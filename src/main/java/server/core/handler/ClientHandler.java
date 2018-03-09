package server.core.handler;

import server.core.handler.io.ServerInput;
import server.core.handler.io.ServerOutput;
import server.core.logger.IServerLogger;
import server.core.model.ServerModel;
import server.core.player.Player;
import server.core.util.event.ServerModelEvents;
import server.core.util.exception.ClientLeftException;
import server.core.util.exception.ServerProtocolException;
import server.core.util.protocol.ServerInputProtocol;

import java.io.IOException;
import java.net.Socket;
import java.util.List;

public class ClientHandler implements Runnable, ServerInputProtocol, ServerModelEvents {

	/**
	 * To be able to handle the client's input and output
	 */
	private final Socket socket;
	private ServerInput clientInput;
	private ServerOutput clientOutput;

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
		this.clientState = ClientState.ST_INIT;
		this.player = null;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (obj.getClass() != this.getClass())
			return false;
		ClientHandler that = (ClientHandler) obj;
		return that.player.equals(this.player)
				&& that.socket.equals(this.socket)
				&& that.clientState.equals(this.clientState);
	}

	public void run() {
		try (Socket s = socket) {
			clientInput = new ServerInput(this, s.getInputStream());
			clientOutput = new ServerOutput(s.getOutputStream());
			clientInput.doRun();
		} catch (IOException | ClientLeftException e) {
			serverLogger.systemMessage(e.getMessage());
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
				serverLogger.clientGotName(socket.getLocalSocketAddress().toString(), name);
				clientOutput.nameOk();
			} else {
				clientOutput.nameBad();
			}
		} else {
			if (serverModel.changeUserName(player.getName(), name, this)) {
				player.setName(name);
				serverLogger.clientGotName(socket.getLocalSocketAddress().toString(), name);
				clientOutput.nameOk();
			} else {
				clientOutput.nameBad();
			}
		}
	}

	@Override
	public void commandeRoomList() {
		if (clientState == ClientState.ST_NAVIGATOR) {
			List<String> list = serverModel.getAllRooms();
			clientOutput.roomList(list);
		}
	}

	@Override
	public void commandeCreateRoom(String name) {
		if (clientState == ClientState.ST_NAVIGATOR) {
			if (serverModel.createRoom(name, this)) {
				clientState = ClientState.ST_PLAYER;
				clientOutput.roomCreated();
				clientOutput.roomEnteredPlayer();
				serverLogger.clientCreatedRoom(socket.getInetAddress().toString(), player.getName(), name);
				serverLogger.clientEnteredRoom(socket.getInetAddress().toString(), player.getName(), name);
			} else {
				clientOutput.roomError();
			}
		}
	}

	@Override
	public void commandeEnterRoom(String name) {
		if (clientState == ClientState.ST_NAVIGATOR) {
			if (serverModel.entreRoom(player.getName(), name, this)) {
				if (clientState == ClientState.ST_PLAYER) {
					clientOutput.roomEnteredPlayer();
				} else {
					clientOutput.roomEnteredSpectator();
				}
			} else {
				clientOutput.roomDoesntExist();
			}
		}
	}

	@Override
	public void commandePlayDice() {
		if (clientState == ClientState.ST_PLAYER && player.canRollDice()) {
			// todo play the dice
		}
	}

	@Override
	public void commandeMoveTheHorse(int horse) {
		if (clientState == ClientState.ST_PLAYER && player.canPlay()) {
			// todo move the horse chosen
		}
	}

	@Override
	public void commandeExitRoom() {
		if (clientState == ClientState.ST_PLAYER || clientState == ClientState.ST_SPECTATOR) {
			// todo exit the room
		}
	}

	@Override
	public void commandeDisconnect() {
		if (clientState != ClientState.ST_INIT) {
			// todo disconnect
		}
	}

	@Override
	public void commandeStartGame() {
		if (clientState == ClientState.ST_PLAYER) {
			// TODO check if he's an admin and there is at least two players
		}
	}

	private synchronized void finish() {
		clientInput.stop();
		try {
			socket.close();
		} catch (IOException e) {
			serverLogger.systemMessage(e.getMessage());
		}
		if (player != null) {
			//TODO serverModel.
		}
		serverLogger.clientDisconnected(socket.getLocalSocketAddress().toString(), player.getName());
	}

	public Player getPlayer() {
		return player;
	}

	public void setClientState(ClientState clientState) {
		this.clientState = clientState;
	}

	public synchronized boolean isSpectator() {
		return clientState == ClientState.ST_SPECTATOR;
	}

	public synchronized boolean isNavigator() {
		return clientState == ClientState.ST_NAVIGATOR;
	}

	public synchronized boolean isPlayer() {
		return clientState == ClientState.ST_PLAYER;
	}

	public enum ClientState {
		ST_INIT, ST_NAVIGATOR, ST_PLAYER, ST_SPECTATOR
	}
}
