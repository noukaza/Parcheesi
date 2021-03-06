package server.core.handler;

import server.core.handler.io.ServerInput;
import server.core.handler.io.ServerOutput;
import server.core.logger.IServerLogger;
import server.core.model.ServerModel;
import server.core.player.Player;
import server.core.util.event.ServerGameRoomEvents;
import server.core.util.event.ServerModelEvents;
import server.core.util.exception.ClientLeftException;
import server.core.util.exception.ServerProtocolException;
import server.core.util.protocol.ServerInputProtocol;

import java.io.IOException;
import java.net.Socket;
import java.util.List;

public class ClientHandler implements Runnable, ServerInputProtocol, ServerModelEvents, ServerGameRoomEvents {

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
		} catch (IOException | ClientLeftException | ServerProtocolException e) {
			serverLogger.systemMessage(e.getMessage());
			finish();
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
				serverLogger.clientGotName(socket.getLocalSocketAddress().toString(), name);
				clientOutput.nameOk();
			} else {
				clientOutput.nameBad();
			}
		}
	}

	@Override
	public void commandeRoomList() {
		if (this.isNavigator()) {
			List<String> list = serverModel.getAllRoomsStatus();
			clientOutput.roomList(list);
		}
	}

	@Override
	public void commandeCreateRoom(String name) {
		if (this.isNavigator()) {
			if (!name.contains(":") && serverModel.createRoom(name, this)) {
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
		if (this.isNavigator()) {
			if (! serverModel.entreRoom(player.getName(), name, this)) {
				clientOutput.roomDoesntExist();
				serverLogger.clientEnteredRoom(socket.getInetAddress().toString(), player.getName(), name);
			}
		}
	}

	@Override
	public void commandePlayDice() {
		if (this.isPlayer() && player.canRollDice()) {
			int value = player.getRoom().rollTheDice(this);
			player.setDice(value);
		}
	}

	@Override
	public void commandeMoveTheHorse(int horse) {
		if (this.isPlayer() && player.canPlay()) {
			if (!(horse >= 0 && horse <= 3 && player.getRoom().playerMovedHorse(this, horse)))
				clientOutput.badMove();
		}
	}

	@Override
	public void commandePassTurn() {
		if (this.isPlayer() && player.canPlay()) {
			player.getRoom().playerPassedTurn();
		}
	}

	@Override
	public void commandeExitRoom() {
		if (this.isPlayer()) {
			player.getRoom().removePlayer(this);
			player.setRoom(null);
		} else if (this.isSpectator()) {
			player.getRoom().removeSpectator(this);
			player.setRoom(null);
		}
		serverLogger.clientExitedRoom(socket.getInetAddress().toString(), player.getName());
	}

	@Override
	public void commandeDisconnect() {
		serverLogger.clientDisconnected(socket.getInetAddress().toString(), getPlayer().getName());
		serverModel.unregisterUser(player.getName(), this);
		player = null;
		clientOutput.goodBye();
		this.finish();
	}

	@Override
	public void commandeStartGame() {
		if (this.isPlayer() && ! player.getRoom().isGameStarted()) {
			if (!player.getRoom().startGame(this)) {
				clientOutput.roomError();
			}
		}
	}

	@Override
	public void commandePlayersList() {
		if (this.isPlayer() || this.isSpectator()) {
			clientOutput.playersList(player.getRoom().getPlayersList());
		}
	}

	@Override
	public void commandeSpectatorsNumber() {
		if (this.isPlayer() || this.isSpectator()) {
			clientOutput.spectatorsNumber(player.getRoom().getSpectatorsNumber());
		}
	}

	private synchronized void finish() {
		String name = "unknown";
		if (player != null) {
			name = player.getName();
			serverModel.unregisterUser(player.getName(), this);
			player = null;
			clientOutput.goodBye();
		}
		clientInput.stop();
		try {
			socket.close();
		} catch (IOException e) {
			serverLogger.systemMessage(e.getMessage());
		}
		serverLogger.clientDisconnected(socket.getLocalSocketAddress().toString(), name);
	}

	@Override
	public void notifyRoomStatusChanged(List<String> roomsStatus) {
		if (this.isNavigator())
			clientOutput.roomList(roomsStatus);
	}

	@Override
	public void notifyShutdownRequested() {
		clientOutput.serverOff();
		clientInput.stop();
		try {
			socket.close();
		} catch (IOException e) {
			serverLogger.systemMessage(e.getMessage());
		}
		String name = "unknown";
		if (player != null)
			name = getPlayer().getName();
		serverLogger.clientDisconnected(socket.getLocalSocketAddress().toString(), name);
	}

	@Override
	public void notifyGameStarted() {
		if (this.isPlayer() || this.isSpectator())
			clientOutput.gameStarted();
	}

	@Override
	public void notifyPlayersListChanged(List<String> players) {
		if (this.isPlayer() || this.isSpectator())
			clientOutput.playersList(players);
	}

	@Override
	public void notifySpectatorsNumberChanged(int spectators) {
		if (this.isPlayer() || this.isSpectator())
			clientOutput.spectatorsNumber(spectators);
	}

	@Override
	public void notifyDiceResult(String player, int dice) {
		if (this.isPlayer() || this.isSpectator())
			clientOutput.diceResult(player, dice);
	}

	@Override
	public void notifyRoomClosed() {
		if (this.isPlayer() || this.isSpectator())
			clientOutput.roomClosed();
		setClientState(ClientState.ST_NAVIGATOR);
		player.setRoom(null);
	}

	@Override
	public void notifyPlayerTurn(String player) {
		if (this.isPlayer() || this.isSpectator())
			clientOutput.playerTurn(player);
	}

	@Override
	public void notifyGameStatusChanged(List<String> lines) {
		if (this.isPlayer() || this.isSpectator())
			clientOutput.gameUpdate(lines);
	}

	@Override
	public void notifyWinnerIs(String player) {
		if (this.isPlayer() || this.isSpectator())
			clientOutput.winnerIs(player);
		if (this.player.getName().equals(player))
			serverLogger.clientWon(socket.getInetAddress().toString(), player);
	}

	public Player getPlayer() {
		return player;
	}

	@Override
	public void notifyRoomEnteredPlayer() {
		clientOutput.roomEnteredPlayer();
	}

	@Override
	public void notifyRoomEnteredSpectator() {
		clientOutput.roomEnteredSpectator();
	}

	public void setClientState(ClientState clientState) {
		this.clientState = clientState;
	}

	public synchronized boolean isSpectator() {
		return clientState == ClientState.ST_SPECTATOR;
	}

	private synchronized boolean isNavigator() {
		return clientState == ClientState.ST_NAVIGATOR;
	}

	public synchronized boolean isPlayer() {
		return clientState == ClientState.ST_PLAYER;
	}

	public enum ClientState {
		ST_INIT, ST_NAVIGATOR, ST_PLAYER, ST_SPECTATOR
	}
}
