package server.core.handler.io;

import server.core.util.protocol.ServerOutputProtocol;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.List;

public class ServerOutput implements ServerOutputProtocol {

	private PrintWriter out;

	public ServerOutput(OutputStream outputStream) {
		this.out = new PrintWriter(outputStream, true);
	}

	@Override
	public void nameBad() {
		out.println("NAME BAD");
	}

	@Override
	public void nameOk() {
		out.println("NAME OK");
	}

	@Override
	public void roomError() {
		out.println("ROOM ERROR");
	}

	@Override
	public void roomCreated() {
		out.println("ROOM CREATED");
	}

	@Override
	public void roomClosed() {
		out.println("ROOM CLOSED");
	}

	@Override
	public void roomDoesntExist() {
		out.println("ROOM DOESNT EXIST");
	}

	@Override
	public void roomEnteredPlayer() {
		out.println("ROOM ENTERED PLAYER");
	}

	@Override
	public void roomEnteredSpectator() {
		out.println("ROOM ENTERED SPECTATOR");
	}

	@Override
	public void roomList(List<String> rooms) {
		out.println("ROOMS LIST");
		rooms.forEach(out:: println);
		out.println("END");
	}

	@Override
	public void playersList(List<String> players) {
		out.println("PLAYERS LIST");
		players.forEach(out:: println);
		out.println("END");
	}

	@Override
	public void spectatorsNumber(int spectators) {
		out.println("SPECTATORS NUMBER");
		out.println(spectators);
	}

	@Override
	public void diceResult(String player, int value) {
		out.println("DICE RESULT");
		out.println(player);
		out.println(value);
	}

	@Override
	public void gameUpdate(List<String> players) {
		out.println("GAME UPDATE");
		players.forEach(out:: println);
		out.println("END");
	}

	@Override
	public void playerTurn(String player) {
		out.println("PLAYER TURN");
		out.println(player);
	}

	@Override
	public void badMove() {
		out.println("BAD MOVE");
	}

	@Override
	public void gameStarted() {
		out.println("GAME STARTED");
	}

	@Override
	public void winnerIs(String player) {
		out.println("WINNER IS");
		out.println(player);
	}

	@Override
	public void serverOff() {
		out.println("SERVER OFF");
	}

	@Override
	public void goodBye() {
		out.println("GOOD BYE");
	}
}
