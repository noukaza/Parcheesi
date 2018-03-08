package server.core.model.room;

import server.core.handler.ClientHandler;
import server.core.model.ServerModel;

import java.util.Random;
import java.util.Vector;

public class ServerGameRoom {

	private static int MAX_PLAYERS = 4;
	private static int MIN_PLAYERS = 2;

	private volatile Vector<ClientHandler> spectators;

	private volatile Vector<ClientHandler> players;

	private volatile ClientHandler admine;

	private Random random;

	private ServerModel serverModel;

	public ServerGameRoom(ServerModel serverModel, ClientHandler admine) {
		this.admine = admine;
		this.serverModel = serverModel;
		this.spectators = new Vector<>();
		this.players = new Vector<>();
		this.random = new Random();

		players.add(admine);
	}

	public int rollTheDice() {
		return (random.nextInt(6) + 1);
	}

	public Vector<ClientHandler> getPlayers() {
		return players;
	}

	public Vector<ClientHandler> getSpectators() {
		return spectators;
	}
}
