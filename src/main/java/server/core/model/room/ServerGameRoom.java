package server.core.model.room;

import server.core.handler.ClientHandler;
import server.core.model.ServerModel;

import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;

public class ServerGameRoom {

	private static int MAX_PLAYERS = 4;
	private static int MIN_PLAYERS = 2;

	private volatile Vector<ClientHandler> spectators;

	private volatile Vector<ClientHandler> players;

	private volatile ClientHandler admine;

	private Random random;

	private boolean gameStarted;

	private ServerModel serverModel;

	public ServerGameRoom(ServerModel serverModel, ClientHandler admine) {
		this.admine = admine;
		this.serverModel = serverModel;
		this.spectators = new Vector<>();
		this.players = new Vector<>();
		this.random = new Random();
		this.gameStarted = false;
		players.add(admine);
	}

	public synchronized boolean addPlayer(ClientHandler clientHandler) {
		if (players.size() < MAX_PLAYERS && ! gameStarted) {
			players.add(clientHandler);
			notifyPlayersListChanged();
			return true;
		}
		spectators.add(clientHandler);
		// todo give this new spectator a notification of the game
		notifySpectatorsNumberChanged();
		return false;
	}

	private synchronized void notifySpectatorsNumberChanged() {
		int size = spectators.size();
		for (ClientHandler player : players)
			player.notifySpectatorsNumberChanged(size);
		for (ClientHandler spectator : spectators)
			spectator.notifySpectatorsNumberChanged(size);
	}

	private synchronized void notifyPlayersListChanged() {
		ArrayList<String> names = new ArrayList<>();
		for (ClientHandler player : players)
			names.add(player.getPlayer().getName());
		for (ClientHandler player : players)
			player.notifyPlayersListChanged(names);
		for (ClientHandler spectator : spectators)
			spectator.notifyPlayersListChanged(names);
	}

	public synchronized void removePlayer(ClientHandler clientHandler) {
		if (clientHandler.equals(admine)) {
			players.remove(admine);
			if (gameStarted) {
				int index = players.indexOf(clientHandler);
				// todo set a new this.admine =
				// todo admine left so someone else should become the admine
				players.insertElementAt(null, players.indexOf(clientHandler));
			} else {
				// todo in case the game didn't start
			}
		} else {
			if (players.remove(clientHandler)) {
				// todo notify that he left

			}
		}
	}

	public synchronized void removeSpectator(ClientHandler clientHandler) {
		if (spectators.remove(clientHandler)) {
			// todo notify that he left
			notifySpectatorsNumberChanged();
		}
	}

	public int rollTheDice() {
		return (random.nextInt(6) + 1);
	}

	public synchronized Vector<ClientHandler> getPlayers() {
		return players;
	}

	public synchronized Vector<ClientHandler> getSpectators() {
		return spectators;
	}
}
