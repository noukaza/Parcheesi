package server.core.handler.io;

import server.core.util.protocol.ServerOutputProtocol;

import java.io.OutputStream;
import java.io.PrintWriter;

public class ServerOutput implements ServerOutputProtocol {

	private PrintWriter out;

	public ServerOutput(OutputStream outputStream) {
		this.out = new PrintWriter(outputStream, true);
	}
}
