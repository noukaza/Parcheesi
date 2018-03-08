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
	public void roomDoesntExist() {
		out.println("ROOM DOESNT EXIST");
	}

	@Override
	public void roomList(List<String> rooms) {
		out.println("ROOMS LIST");
		rooms.forEach(out:: println);
		out.println("END");
	}
}
