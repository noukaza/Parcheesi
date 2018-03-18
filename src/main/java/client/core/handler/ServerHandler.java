package client.core.handler;

import client.core.gui.GameFrame;
import client.core.handler.io.ServerInput;
import client.core.handler.io.ServerOutput;
import client.core.util.event.ClientModelEvent;
import server.core.util.protocol.ServerInputProtocol;
import server.core.util.protocol.ServerOutputProtocol;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ServerHandler implements Runnable, ServerOutputProtocol, ServerInputProtocol, ClientModelEvent {

	private Socket socket;

	private ServerInput serverInput;
	private ServerOutput serverOutput;

	private GameFrame model;

	private String playerName, tmp_playerName;

	private int[] horses;

	private boolean spectator = false;

	public ServerHandler(GameFrame model, Socket socket) {
		this.socket = socket;
		this.model = model;
		horses = new int[4];
	}

	public void run() {
		try (Socket s = socket) {
			serverInput = new ServerInput(this, s.getInputStream());
			serverOutput = new ServerOutput(s.getOutputStream());
			serverInput.doRun();
		} catch (Exception e) {
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
		model.serverAcceptedName(playerName);
	}

	@Override
	public void roomError() {
		model.serverRefusedRoomName();
	}

	@Override
	public void roomCreated() {
	}

	@Override
	public void roomClosed() {
		spectator = false;
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
		ArrayList<String> players = new ArrayList<>();
		ArrayList<int[]> horses = new ArrayList<>();

		for (int i = 0; i < lines.size(); i += 2) {
			String name = lines.get(i);
			players.add(name);
			String[] h = lines.get(i + 1).split(":");
			int[] hr = new int[4];
			for (int j = 0; j < h.length; j++)
				hr[j] = Integer.parseInt(h[j]);
			if (name.equals(playerName))
				this.horses = hr;
			horses.add(hr);
		}
		model.serverSentGameUpdate(players, horses);
	}

	@Override
	public void playerTurn(String player) {

		if (player.equals(playerName))
			model.serverSentPlayerTurn("Your");
		else
			model.serverSentPlayerTurn(player);
	}

	@Override
	public void badMove() {
		model.serverSentBadMove();
	}

	@Override
	public void gameStarted() {
		horses = new int[4];
		model.serverSentGameStarted();
	}

	@Override
	public void winnerIs(String player) {
		model.serverSentWinnerIs(player);
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
		serverOutput.commandeName(name);
	}

	@Override
	public void commandeRoomList() {
		serverOutput.commandeRoomList();
	}

	@Override
	public void commandeCreateRoom(String name) {
		serverOutput.commandeCreateRoom(name);
	}

	@Override
	public void commandeEnterRoom(String name) {
		serverOutput.commandeEnterRoom(name);
	}

	@Override
	public void commandePlayDice() {
		serverOutput.commandePlayDice();
	}

	@Override
	public void commandeMoveTheHorse(int horse) {
		serverOutput.commandeMoveTheHorse(horse);
	}

	@Override
	public void commandePassTurn() {
		serverOutput.commandePassTurn();
	}

	@Override
	public void commandeExitRoom() {
		serverOutput.commandeExitRoom();
		model.playerNowIsNavigator();
	}

	@Override
	public void commandeDisconnect() {
		serverOutput.commandeDisconnect();
	}

	@Override
	public void commandeStartGame() {
		serverOutput.commandeStartGame();
	}

	@Override
	public void commandePlayersList() {
		serverOutput.commandePlayersList();
	}

	@Override
	public void commandeSpectatorsNumber() {
		serverOutput.commandeSpectatorsNumber();
	}

	public boolean isSpectator() {
		return spectator;
	}

	public int[] getHorses() {
		return horses;
	}

	public String getPlayerName() {
		return playerName;
	}

	private void finish() {
		serverInput.disconnect();
		try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
