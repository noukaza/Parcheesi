package client.core.handler.io;

import server.core.util.protocol.ServerInputProtocol;

import java.io.OutputStream;
import java.io.PrintWriter;

public class ServerOutput implements ServerInputProtocol {

    private PrintWriter out;

	public ServerOutput(OutputStream outputStream) {
        this.out = new PrintWriter(outputStream, true);
    }

	@Override
	public void commandeName(String name) {
        out.println("NAME");
        out.println(name);
    }

    @Override
    public void commandeRoomList() {
	    out.println("ROOMS LIST");
    }

    @Override
    public void commandeCreateRoom(String name) {
	    out.println("CREATE ROOM");
	    out.println(name);
    }

    @Override
    public void commandeEnterRoom(String name) {
	    out.println("ENTER ROOM");
	    out.println(name);
    }

    @Override
    public void commandePlayDice() {
	    out.println("PLAY DICE");
    }

    @Override
    public void commandeMoveTheHorse(int horse) {
	    out.println("MOVE HORSE");
	    out.println(horse);
    }

	@Override
	public void commandePassTurn() {
		out.println("PASS TURN");
	}

	@Override
    public void commandeExitRoom() {
	    out.println("EXIT ROOM");
    }

    @Override
    public void commandeDisconnect() {
	    out.println("DISCONNECT");
    }

	@Override
	public void commandeStartGame() {
		out.println("START GAME");
	}

	@Override
	public void commandePlayersList() {
		out.println("PLAYERS LIST");
	}

	@Override
	public void commandeSpectatorsNumber() {
		out.println("SPECTATORS NUMBER");
	}
}
