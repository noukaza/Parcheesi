package server.core.model.room;

import server.core.handler.ClientHandler;
import server.core.model.ServerModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Vector;

public class ServerGameRoom {

	private static int MAX_PLAYERS = 4;
	private static int MIN_PLAYERS = 2;

	private volatile Vector<ClientHandler> spectators;

	private volatile Vector<ClientHandler> players;

	private volatile ClientHandler admine;

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
		if (players.size() < MAX_PLAYERS && ! gameStarted) {
			players.add(clientHandler);
			clientHandler.setClientState(ClientHandler.ClientState.ST_PLAYER);
			clientHandler.roomEnteredPlayer();
			notifyPlayersListChanged();
		} else {
			spectators.add(clientHandler);
			clientHandler.setClientState(ClientHandler.ClientState.ST_SPECTATOR);
			clientHandler.roomEnteredSpectator();
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
			player.notifySpectatorsNumberChanged(size);
		for (ClientHandler spectator : spectators)
			spectator.notifySpectatorsNumberChanged(size);
	}

	public synchronized void notifyPlayersListChanged() {
		List<String> list = getPlayersList();
		for (ClientHandler player : players)
			player.notifyPlayersListChanged(list);
		for (ClientHandler spectator : spectators)
			spectator.notifyPlayersListChanged(list);
	}

	private synchronized void notifyGameStatusChanged() {
		List<String> report = getGameStatus();
		for (ClientHandler p : players)
			p.notifyGameStatusChanged(report);
		for (ClientHandler s : spectators)
			s.notifyGameStatusChanged(report);
	}

	public synchronized List<String> getPlayersList() {
		ArrayList<String> names = new ArrayList<>();
		for (ClientHandler player : players)
			names.add(player.getPlayer().getName());
		return names;
	}

	public synchronized int getSpectatorsNumber() {
		return this.spectators.size();
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

			admine.getPlayer().setAdmine(false);

			int index = random.nextInt(players.size());
			ClientHandler chosen = players.get(index);
			this.admine = chosen;
			chosen.getPlayer().setAdmine(true);

			if (gameStarted) {
				index = players.indexOf(clientHandler);
				players.insertElementAt(null, index);
			} else {
				players.remove(admine);
			}

			notifyPlayersListChanged();
		} else {
			if (players.remove(clientHandler)) {
				notifyPlayersListChanged();
			}
		}
		clientHandler.setClientState(ClientHandler.ClientState.ST_NAVIGATOR);
		if (players.isEmpty())
			serverModel.removeRoom(name, this);
		else
			serverModel.notifyRoomStatusChanged();
	}

	public synchronized void removeSpectator(ClientHandler clientHandler) {
		if (spectators.remove(clientHandler)) {
			notifySpectatorsNumberChanged();
		}
	}

	public synchronized int rollTheDice(ClientHandler player) {
		String name = player.getPlayer().getName();
		int value = (random.nextInt(6) + 1);
		for (ClientHandler p : players)
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
}
