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

	private String playerName, tmp_playerName;

	private boolean spectator = false;
	private boolean admine = false;

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
		playerName = tmp_playerName;
		model.serverAcceptedName();
	}

	@Override
	public void roomError() {
		model.serverRefusedRoomName();
	}

	@Override
	public void roomCreated() {
		admine = true;
		//todo code here
	}

	@Override
	public void roomClosed() {
		spectator = false;
		admine = false;
		model.serverClosedRoom();
	}

	@Override
	public void roomDoesntExist() {
		model.serverRoomDoesntExist();
	}

	@Override
	public void roomEnteredPlayer() {
		spectator = false;
		model.serverAllowedEnteringRoom();
	}

	@Override
	public void roomEnteredSpectator() {
		spectator = true;
		model.serverAllowedEnteringRoom();
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
		model.severSentPlayersList(players);
	}

	@Override
	public void spectatorsNumber(int spectators) {
		model.severSentSpectatorsNumber(spectators);
	}

	@Override
	public void diceResult(String player, int value) {
		model.serverSentDiceResult(player, value);
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
		model.serverShutDown();
		finish();
	}

	@Override
	public void goodBye() {
		model.serverSaidGoodBye();
		finish();
	}

	@Override
	public void commandeName(String name) {
		tmp_playerName = name;
		clientOutput.commandeName(name);
	}

	@Override
	public void commandeRoomList() {
		clientOutput.commandeRoomList();
	}

	@Override
	public void commandeCreateRoom(String name) {
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

	@Override
	public void commandePlayersList() {
		clientOutput.commandePlayersList();
	}

	@Override
	public void commandeSpectatorsNumber() {
		clientOutput.commandeSpectatorsNumber();
	}

	public boolean isAdmine() {
		return admine;
	}

	public boolean isSpectator() {
		return spectator;
	}

	public String getPlayerName() {
		return playerName;
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
