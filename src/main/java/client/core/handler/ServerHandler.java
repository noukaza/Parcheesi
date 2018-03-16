package client.core.handler;

import client.core.gui.GameFrame;
import client.core.handler.io.ClientInput;
import client.core.handler.io.ClientOutput;
import server.core.util.protocol.ServerInputProtocol;
import server.core.util.protocol.ServerOutputProtocol;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ServerHandler implements Runnable, ServerOutputProtocol, ServerInputProtocol {
	private Socket socket;

	private ClientInput clientInput;
	private ClientOutput clientOutput;

	private GameFrame model;

	public ServerHandler(GameFrame model, Socket socket) {
		this.socket = socket;
		this.model = model;
	}

	public void run() {
		try (Socket s = socket) {
			clientInput = new ClientInput(this, s.getInputStream());
			clientOutput = new ClientOutput(s.getOutputStream());
			clientInput.doRun();
		} catch (Exception e) {
			e.printStackTrace();
			finish();
		}
	}

	@Override
	public void nameBad() {
		model.serverRefusedName();
	}

	@Override
	public void nameOk() {
		model.serverAcceptedName();
	}

	@Override
	public void roomError() {
		model.serverRefusedRoomName();
	}

	@Override
	public void roomCreated() {
        model.serverAcceptedRoom();
	}

	@Override
	public void roomClosed() {
		model.serverSentRoomClosed();
	}

	@Override
	public void roomDoesntExist() {
        model.serverSentRoomDoesntExist();
	}

	@Override
	public void roomEnteredPlayer() {
		model.serverSentStatusPlayer();
	}

	@Override
	public void roomEnteredSpectator() {
		model.serverSentStatusSpectator();
	}

	@Override
	public void roomList(List<String> rooms) {
		ArrayList<String> names = new ArrayList<>();
		ArrayList<Integer> players = new ArrayList<>();
		ArrayList<Integer> spectators = new ArrayList<>();
		for (int i = 0; i < rooms.size(); i += 2) {
			names.add(rooms.get(i));
			String[] sides = rooms.get(i + 1).split(":");
			players.add(Integer.parseInt(sides[0]));
			spectators.add(Integer.parseInt(sides[1]));
		}
		model.updateRoomsList(names, players, spectators);
	}

	@Override
	public void playersList(List<String> players) {
		model.serverSentPlayerList(players);
	}

	@Override
	public void spectatorsNumber(int spectators) {
        model.serversentSpectatorsNumber(spectators);
	}

	@Override
	public void diceResult(String player, int value) {
        model.serverSentDiceResult(player, value);
	}

	@Override
	public void gameUpdate(List<String> lines) {
		model.serverUpdateGame(lines);
	}

	@Override
	public void playerTurn(String player) {
        model.serverSentPlayerTurn(player);
	}

	@Override
	public void badMove() {
        model.serverRefusedTheMove();
	}

	@Override
	public void gameStarted() {
		model.serverSentgGameStart();
	}

	@Override
	public void winnerIs(String player) {
        model.serverSendTheWinner(player);
	}

	@Override
	public void serverOff() {
        model.serverIsOff();
	}

	@Override
	public void goodBye() {
        //todo code here
        clientOutput.commandeDisconnect();
	}

	@Override
	public void commandeName(String name) {
		// todo add name
		clientOutput.commandeName(name);
	}

	@Override
	public void commandeRoomList() {
		clientOutput.commandeRoomList();
	}

	@Override
	public void commandeCreateRoom(String name) {
		// todo add roomname to the handler
		clientOutput.commandeCreateRoom(name);
	}

	@Override
	public void commandeEnterRoom(String name) {
		clientOutput.commandeEnterRoom(name);
	}

	@Override
	public void commandePlayDice() {
		clientOutput.commandePlayDice();
	}

	@Override
	public void commandeMoveTheHorse(int horse) {
		clientOutput.commandeMoveTheHorse(horse);
	}

	@Override
	public void commandeExitRoom() {
		clientOutput.commandeExitRoom();
	}

	@Override
	public void commandeDisconnect() {
		clientOutput.commandeDisconnect();
	}

	@Override
	public void commandeStartGame() {
		clientOutput.commandeStartGame();
	}

	private void finish() {
		clientInput.disconnect();
		try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
