package server.core.handler;

import server.core.logger.IServerLogger;
import server.core.util.event.ServerModelEvents;
import server.core.util.protocol.ServerInputProtocol;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientHandler implements Runnable, ServerInputProtocol, ServerModelEvents {

	private final Socket socket;
	private boolean stop;

	private IServerLogger serverLogger;

	public ClientHandler(Socket socket, IServerLogger serverLogger /*TODO add ServerModel*/) {
		this.serverLogger = serverLogger;
		this.socket = socket;
		this.stop = false;
	}

	public void run() {
		try (Socket s = socket) {
			// TODO add SERVER INPUT AND OUTPUT
			Scanner clientInput = new Scanner(s.getInputStream());
			PrintWriter clientOutput = new PrintWriter(s.getOutputStream(), true);
			while (! stop) {
				String line = clientInput.nextLine();

				System.out.println(line);

				clientOutput.println("You said " + line + ", THANK YOU");
			}
		} catch (IOException e) {
			serverLogger.systemMessage(e.getMessage());
		}
	}
}
