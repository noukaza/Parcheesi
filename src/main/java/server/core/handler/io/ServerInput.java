package server.core.handler.io;

import server.core.handler.ClientHandler;
import server.core.util.exception.ClientLeftException;
import server.core.util.exception.ServerProtocolException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ServerInput {

	private static final String NAME = "NAME";

	private static final String ROOMS_LIST = "ROOMS LIST";
	private static final String CREATE_ROOM = "CREATE ROOM";
	private static final String ENTER_ROOM = "ENTER ROOM";
	private static final String EXIT_ROOM = "EXIT ROOM";
	private static final String PLAYERS_LIST = "PLAYERS LIST";
	private static final String SPECTATORS_NUMBER = "SPECTATORS NUMBER";
	private static final String START_GAME = "START GAME";
	private static final String PLAY_DICE = "PLAY DICE";
	private static final String MOVE_HORSE = "MOVE HORSE";

	private static final String EXIT_SERVER = "DISCONNECT";


	private InputStream in;
	private ClientHandler handler;
	private boolean stop;

	public ServerInput(ClientHandler handler, InputStream in) {
		this.in = in;
		this.handler = handler;
	}

	public void doRun() throws IOException, ServerProtocolException, ClientLeftException {
		String name;
		int horse;
		stop = false;
		try (BufferedReader buffer = new BufferedReader(new InputStreamReader(in))) {
			while (! stop) {
				String header = buffer.readLine();
				if (header == null) {
					throw new ClientLeftException("client left without saying goodbye");
				}
				switch (header) {
					case NAME:
						name = buffer.readLine();
						handler.commandeName(name);
						break;
					case ROOMS_LIST:
						handler.commandeRoomList();
						break;
					case CREATE_ROOM:
						name = buffer.readLine();
						handler.commandeCreateRoom(name);
						break;
					case ENTER_ROOM:
						name = buffer.readLine();
						handler.commandeEnterRoom(name);
						break;
					case EXIT_ROOM:
						handler.commandeExitRoom();
						break;
					case PLAYERS_LIST:
						handler.commandePlayersList();
						break;
					case SPECTATORS_NUMBER:
						handler.commandeSpectatorsNumber();
						break;
					case START_GAME:
						handler.commandeStartGame();
						break;
					case PLAY_DICE:
						handler.commandePlayDice();
						break;
					case MOVE_HORSE:
						horse = Integer.parseInt(buffer.readLine());
						handler.commandeMoveTheHorse(horse);
						break;
					case EXIT_SERVER:
						handler.commandeDisconnect();
						break;
					default:
						throw new ServerProtocolException("Invalid Commande :" + header);
				}
			}
		}
	}

	public void stop() {
		stop = true;
	}
}
