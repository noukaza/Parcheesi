package server.core.model.room;

import server.core.handler.ClientHandler;
import server.core.model.ServerModel;
import server.core.player.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Vector;

public class ServerGameRoom {

	private volatile Vector<ClientHandler> spectators;

	private volatile Vector<ClientHandler> players;

	private volatile ClientHandler admine;

	private int turn = 0;

	private Random random;

	private String name;

	private boolean gameStarted;

	private ServerModel serverModel;

	public ServerGameRoom(String name, ServerModel serverModel, ClientHandler admine) {
		this.name = name;
		this.admine = admine;
		this.admine.getPlayer().setAdmine(true);
		this.admine.getPlayer().setRoom(this);
		this.serverModel = serverModel;
		this.spectators = new Vector<>();
		this.players = new Vector<>();
		this.random = new Random();
		this.gameStarted = false;
		players.add(admine);
	}

	public synchronized void addPlayer(ClientHandler clientHandler) {
		int MAX_PLAYERS = 4;
		if (players.size() < MAX_PLAYERS && ! gameStarted) {
			players.add(clientHandler);
			clientHandler.setClientState(ClientHandler.ClientState.ST_PLAYER);
			clientHandler.notifyRoomEnteredPlayer();
			notifyPlayersListChanged();
		} else {
			spectators.add(clientHandler);
			clientHandler.setClientState(ClientHandler.ClientState.ST_SPECTATOR);
			clientHandler.notifyRoomEnteredSpectator();
			clientHandler.notifyPlayersListChanged(getPlayersList());
			if (gameStarted) {
				clientHandler.notifyGameStatusChanged(getGameStatus());
			}
			notifySpectatorsNumberChanged();
		}
		clientHandler.getPlayer().setRoom(this);
	}

	private synchronized void notifySpectatorsNumberChanged() {
		int size = spectators.size();
		for (ClientHandler player : players)
			if (player != null)
				player.notifySpectatorsNumberChanged(size);
		for (ClientHandler spectator : spectators)
			spectator.notifySpectatorsNumberChanged(size);
	}

	public synchronized void notifyPlayersListChanged() {
		List<String> list = getPlayersList();
		for (ClientHandler player : players)
			if (player != null)
				player.notifyPlayersListChanged(list);
		for (ClientHandler spectator : spectators)
			spectator.notifyPlayersListChanged(list);
	}

	private synchronized void notifyGameStatusChanged() {
		List<String> report = getGameStatus();
		for (ClientHandler p : players)
			if (p != null)
				p.notifyGameStatusChanged(report);
		for (ClientHandler s : spectators)
			s.notifyGameStatusChanged(report);
	}

	private synchronized void notifyGameStarted() {
		for (ClientHandler p : players)
			if (p != null)
				p.notifyGameStarted();
		for (ClientHandler s : spectators)
			s.notifyGameStarted();
	}

	public synchronized List<String> getPlayersList() {
		ArrayList<String> names = new ArrayList<>();
		for (ClientHandler player : players)
			if (player != null)
				names.add(player.getPlayer().getName());
		return names;
	}

	private synchronized List<String> getGameStatus() {
		if (gameStarted) {
			ArrayList<String> lines = new ArrayList<>();
			for (ClientHandler p : players) {
				lines.add(p.getPlayer().getName());
				int[] h = p.getPlayer().getHorses();
				lines.add(h[0] + ":" + h[1] + ":" + h[2] + ":" + h[3]);
			}
			return lines;
		}
		return null;
	}

	public synchronized void removePlayer(ClientHandler clientHandler) {
		if (clientHandler.equals(admine)) {

			int index;

			if (gameStarted) {
				index = players.indexOf(clientHandler);
				players.set(index, null);
			} else {
				players.remove(admine);
				if (players.size() == 0) {
					clientHandler.setClientState(ClientHandler.ClientState.ST_NAVIGATOR);
					serverModel.removeRoom(name, this);
					return;
				}
			}

			admine.getPlayer().setAdmine(false);

			ClientHandler chosen;

			index = turn;

			do {
				index = (index + 1) % players.size();
			} while (players.get(index) == null);

			chosen = players.get(index);
			this.admine = chosen;
			chosen.getPlayer().setAdmine(true);

		} else {

			int index;

			if (gameStarted) {
				index = players.indexOf(clientHandler);
				players.set(index, null);
			} else {
				players.remove(clientHandler);
			}
		}

		clientHandler.setClientState(ClientHandler.ClientState.ST_NAVIGATOR);
		notifyPlayersListChanged();

		if (gameStarted && getPLayersNumber() == 1) {
			notifyWeHaveAWinner(getLastPlayer());
		} else if (players.isEmpty()) {
			serverModel.removeRoom(name, this);
		} else {
			serverModel.notifyRoomStatusChanged();
		}
	}

	private synchronized String getLastPlayer() {
		for (ClientHandler player : players) {
			if (player != null)
				return player.getPlayer().getName();
		}
		return "no one";
	}

	private synchronized int getPLayersNumber() {
		int i = 0;
		for (ClientHandler player : players)
			if (player != null)
				i++;
		return i;
	}

	public synchronized void removeSpectator(ClientHandler clientHandler) {
		if (spectators.remove(clientHandler)) {
			clientHandler.setClientState(ClientHandler.ClientState.ST_NAVIGATOR);
			notifySpectatorsNumberChanged();
		}
	}

	public synchronized int rollTheDice(ClientHandler player) {
		String name = player.getPlayer().getName();
		int value = (random.nextInt(6) + 1);
		player.getPlayer().wontRollDice();
		player.getPlayer().willPlay();
		for (ClientHandler p : players)
			if (p != null)
				p.notifyDiceResult(name, value);
		for (ClientHandler s : spectators)
			s.notifyDiceResult(name, value);
		return value;
	}

	public synchronized Vector<ClientHandler> getPlayers() {
		return players;
	}

	public synchronized Vector<ClientHandler> getSpectators() {
		return spectators;
	}

	public synchronized boolean startGame(ClientHandler player) {
		int MIN_PLAYERS = 2;
		if (player.equals(admine) && players.size() >= MIN_PLAYERS) {
			for (ClientHandler p : players)
				p.getPlayer().init();
			gameStarted = true;
			notifyGameStarted();
			turn = 0;
			players.get(turn).getPlayer().willRollDice();
			notifyPlayerTurn(players.get(turn).getPlayer().getName());
			return true;
		}
		return false;
	}

	private synchronized void notifyPlayerTurn(String name) {
		for (ClientHandler p : players)
			if (p != null)
				p.notifyPlayerTurn(name);
		for (ClientHandler s : spectators)
			s.notifyPlayerTurn(name);
	}

	public synchronized boolean playerMovedHorse(ClientHandler client, int index) {
		boolean rightMove = true;
		int dice = client.getPlayer().getDice();
		int horse = client.getPlayer().getHorse(index);
		if (dice == 6) {
			if (horse == 0)
				client.getPlayer().moveHorse(index, 1);
			else if (horse >= 67)
				rightMove = false;
			else
				client.getPlayer().moveHorse(index, dice);
		} else {
			if (horse == 0 || horse >= 67)
				rightMove = false;
			else
				client.getPlayer().moveHorse(index, dice);
		}
		if (rightMove) {
			int winner = doWeHaveAWinner();
			if (winner >= 0) {
				notifyWeHaveAWinner(players.get(index).getPlayer().getName());
				return true;
			}
			checkCrossedHorses(players.indexOf(client), client.getPlayer().getHorse(index));
			if (dice != 6) {
				client.getPlayer().wontPlay();
				do {
					turn = (turn + 1) % players.size();
				} while (players.get(turn) == null);
				players.get(turn).getPlayer().willRollDice();
				notifyGameStatusChanged();
				notifyPlayerTurn(players.get(turn).getPlayer().getName());
			} else {
				notifyGameStatusChanged();
				notifyPlayerTurn(players.get(turn).getPlayer().getName());
				client.getPlayer().willRollDice();
				client.getPlayer().wontPlay();
			}
			return true;
		} else
			return false;
	}

	private synchronized void checkCrossedHorses(int player, int horse) {
		int xcase = ((15 * player + horse) % 60);
		for (int i = 0; i < players.size(); i++) {
			if (i != player) {
				int[] horses = players.get(i).getPlayer().getHorses();
				for (int j = 0; j < horses.length; j++) {
					int ycase = ((15 * i + horses[j]) % 60);
					if (ycase == xcase)
						players.get(i).getPlayer().setHorse(j, 0);
				}
			}
		}
	}

	private synchronized void notifyWeHaveAWinner(String name) {
		for (ClientHandler p : players) {
			if (p != null) {
				p.notifyWinnerIs(name);
				p.notifyRoomClosed();
			}
		}
		for (ClientHandler s : spectators) {
			s.notifyWinnerIs(name);
			s.notifyRoomClosed();
		}
		serverModel.removeRoom(this.name, this);
	}

	private synchronized int doWeHaveAWinner() {
		for (int i = 0; i < players.size(); i++) {
			ClientHandler client = players.get(i);
			if (client != null) {
				Player player = client.getPlayer();
				if (player.won())
					return i;
			}
		}
		return - 1;
	}

	public synchronized int getSpectatorsNumber() {
		return this.spectators.size();
	}

	public boolean isGameStarted() {
		return gameStarted;
	}

	public void playerPassedTurn() {
		players.get(turn).getPlayer().wontPlay();
		players.get(turn).getPlayer().wontRollDice();
		do {
			turn = (turn + 1) % players.size();
		} while (players.get(turn) == null);
		players.get(turn).getPlayer().willRollDice();
		notifyPlayerTurn(players.get(turn).getPlayer().getName());
	}
}
