package client.core.handler.io;

import server.core.util.protocol.ServerInputProtocol;

import java.io.OutputStream;
import java.io.PrintWriter;

public class ClientOutput implements ServerInputProtocol {

    private PrintWriter out;

    public ClientOutput(OutputStream outputStream) {
        this.out = new PrintWriter(outputStream, true);
    }

	@Override
	public void commandeName(String name) {
        out.println("NAME");
        out.println(name);
    }

    @Override
    public void commandeRoomList() {

    }

    @Override
    public void commandeCreateRoom(String name) {

    }

    @Override
    public void commandeEnterRoom(String name) {

    }

    @Override
    public void commandePlayDice() {

    }

    @Override
    public void commandeMoveTheHorse(int horse) {

    }

    @Override
    public void commandeExitRoom() {

    }

    @Override
    public void commandeDisconnect() {

    }

	@Override
	public void commandeStartGame() {

	}
}
