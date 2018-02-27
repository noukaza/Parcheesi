package client.core.handler;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ServerHandler implements Runnable {
	private Socket socket;
	private boolean stop;

	public ServerHandler(Socket socket) {
		this.socket = socket;
		System.out.println(socket);
		this.stop = false;
	}

	public void run() {
		try {
			Scanner userInput = new Scanner(System.in);
			Scanner serverInput = new Scanner(socket.getInputStream());
			PrintWriter serverOutput = new PrintWriter(socket.getOutputStream(), true);
			while (!stop) {
				String line = userInput.nextLine();

				serverOutput.println(line);

				String serverResponse = serverInput.nextLine();

				System.out.println(serverResponse);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
