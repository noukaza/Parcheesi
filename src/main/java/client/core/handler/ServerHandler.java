package client.core.handler;

import client.core.gui.GameFrame;
import client.core.handler.io.ClientInput;
import client.core.handler.io.ClientOutput;
import server.core.util.protocol.ServerInputProtocol;
import server.core.util.protocol.ServerOutputProtocol;

import java.net.Socket;
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
			clientInput = new ClientInput(this, this.socket.getInputStream());
			clientOutput = new ClientOutput(this.socket.getOutputStream());
			clientInput.doRun();
		} catch (Exception e) {
			e.printStackTrace();
			//todo add finish();
		}
	}


	@Override
	public void nameBad() {

	}

	@Override
	public void nameOk() {

	}

	@Override
	public void roomError() {

	}

	@Override
	public void roomCreated() {

	}

	@Override
	public void roomClosed() {

	}

	@Override
	public void roomDoesntExist() {

	}

	@Override
	public void roomEnteredPlayer() {

	}

	@Override
	public void roomEnteredSpectator() {

	}

	@Override
	public void roomList(List<String> rooms) {

	}

	@Override
	public void playersList(List<String> players) {

	}

	@Override
	public void spectatorsNumber(int spectators) {

	}

	@Override
	public void diceResult(String player, int value) {

	}

	@Override
	public void gameUpdate(List<String> lines) {

	}

	@Override
	public void playerTurn(String player) {

	}

	@Override
	public void badMove() {

	}

	@Override
	public void gameStarted() {

	}

	@Override
	public void winnerIs(String player) {

	}

	@Override
	public void serverOff() {

	}

	@Override
	public void goodBye() {

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
}
