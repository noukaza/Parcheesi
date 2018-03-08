package server.core.handler;

import server.core.handler.io.ServerInput;
import server.core.handler.io.ServerOutput;
import server.core.logger.IServerLogger;
import server.core.model.ServerModel;
import server.core.player.Player;
import server.core.util.event.ServerModelEvents;
import server.core.util.exception.ServerProtocolException;
import server.core.util.protocol.ServerInputProtocol;

import java.io.IOException;
import java.net.Socket;

public class ClientHandler implements Runnable, ServerInputProtocol, ServerModelEvents {

	/**
	 * To be able to handle the client's input and output
	 */
	private final Socket socket;
	private ServerInput clientInput;
	private ServerOutput clientOutput;

	/**
	 * To controle wether we are handling this client or we stop
	 */
	private boolean stop;

	/**
	 * The server logger to writing all whats going on the server
	 */
	private IServerLogger serverLogger;

	/**
	 * the server model to interact with it
	 */
	private ServerModel serverModel;

	/**
	 * client paramaters we need to controle his behaviors
	 */
	private ClientState clientState;
	/**
	 * all the informations about the player
	 *
	 * @see Player
	 */
	private Player player;

	public ClientHandler(Socket socket, IServerLogger serverLogger, ServerModel serverModel) {
		this.serverLogger = serverLogger;
		this.serverModel = serverModel;
		this.socket = socket;
		this.stop = false;
		this.clientState = ClientState.ST_INIT;
		this.player = null;
	}

	public void run() {
		try (Socket s = socket) {
			clientInput = new ServerInput(this, s.getInputStream());
			clientOutput = new ServerOutput(s.getOutputStream());
			clientInput.doRun();
		} catch (IOException e) {
			serverLogger.systemMessage(e.getMessage());
			if (! stop)
				finish();
		} catch (ServerProtocolException e) {
			serverLogger.systemMessage(e.getMessage());
		}
	}

	@Override
	public void commandeName(String name) {
		if (clientState == ClientState.ST_INIT) {
			if (serverModel.registerUser(name, this)) {
				player = new Player(name);
				clientState = ClientState.ST_NAVIGATOR;
			} else {
				// TODO send him NAME BAD
			}
		} else {
			if (serverModel.changeUserName(player.getName(), name, this)) {
				player.setName(name);
			} else {
				// TODO if his name already exists send an output NAME BAD
			}
		}
	}

	@Override
	public void commandeRoomList() {
		if (clientState == ClientState.ST_NAVIGATOR) {
			// TODO check the model and get all the rooms
			// TODO output him the rooms list
		}
	}

	@Override
	public void commandeCreateRoom(String name) {
		if (clientState == ClientState.ST_NAVIGATOR) {
			// TODO check the model if this room doesn't already exist
		}
	}

	@Override
	public void commandeEnterRoom(String name) {
		if (clientState == ClientState.ST_NAVIGATOR) {
			// TODO check the model if this room exist
		}
	}

	@Override
	public void commandePlayDice() {
		if (clientState == ClientState.ST_PLAYER && player.canRollDice()) {

		}
	}

	@Override
	public void commandeMoveTheHorse(int horse) {
		if (clientState == ClientState.ST_PLAYER && player.canPlay()) {

		}
	}

	@Override
	public void commandeExitRoom() {
		if (clientState == ClientState.ST_PLAYER || clientState == ClientState.ST_SPECTATOR) {

		}
	}

	@Override
	public void commandeDisconnect() {
		if (clientState != ClientState.ST_INIT) {

		}
	}

	@Override
	public void commandeStartGame() {
		if (clientState == ClientState.ST_PLAYER) {
			// TODO check if he's an admin and there is at least two players
		}
	}

	private synchronized void finish() {
		if (! stop) {
			stop = true;
			try {
				socket.close();
			} catch (IOException e) {
				serverLogger.systemMessage(e.getMessage());
			}
			if (player != null)
				// TODO remove the player from the model
				serverLogger.clientDisconnected(socket.getLocalSocketAddress().toString(), player.getName());
		}
	}

	public enum ClientState {
		ST_INIT, ST_NAVIGATOR, ST_PLAYER, ST_SPECTATOR
	}
}
