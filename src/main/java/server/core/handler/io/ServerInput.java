package server.core.handler.io;

import server.core.handler.ClientHandler;
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

	public void doRun() throws IOException, ServerProtocolException {
		stop = false;
		try (BufferedReader buffer = new BufferedReader(new InputStreamReader(in))) {
			while (! stop) {
				String header = buffer.readLine();
				switch (header) {
					case NAME:
						// TODO here
						break;
					case ROOMS_LIST:
						// TODO here
						break;
					case CREATE_ROOM:
						// TODO here
						break;
					case ENTER_ROOM:
						// TODO here
						break;
					case EXIT_ROOM:
						// TODO here
						break;
					case START_GAME:
						// TODO here
						break;
					case PLAY_DICE:
						// TODO here
						break;
					case MOVE_HORSE:
						// TODO here
						break;
					case EXIT_SERVER:
						// TODO here
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
