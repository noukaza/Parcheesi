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
		//todo code here
	}

	@Override
	public void roomClosed() {
		//todo code here
	}

	@Override
	public void roomDoesntExist() {
		//todo code here
	}

	@Override
	public void roomEnteredPlayer() {
		//todo code here
	}

	@Override
	public void roomEnteredSpectator() {
		//todo code here
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
		//todo code here
	}

	@Override
	public void spectatorsNumber(int spectators) {
		//todo code here
	}

	@Override
	public void diceResult(String player, int value) {
		//todo code here
	}

	@Override
	public void gameUpdate(List<String> lines) {
		//todo code here
	}

	@Override
	public void playerTurn(String player) {
		//todo code here
	}

	@Override
	public void badMove() {
		//todo code here
	}

	@Override
	public void gameStarted() {
		//todo code here
	}

	@Override
	public void winnerIs(String player) {
		//todo code here
	}

	@Override
	public void serverOff() {
		//todo code here
	}

	@Override
	public void goodBye() {
		//todo code here
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
