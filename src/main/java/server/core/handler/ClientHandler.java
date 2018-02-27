package server.core.handler;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientHandler implements Runnable {

	private final Socket socket;
	private boolean stop;

	public ClientHandler(Socket socket /*TODO add ServerModel*/ /*TODO add the Logger*/) {
		this.socket = socket;
		this.stop = false;
	}

	public void run() {
		try (Socket s = socket) {
			Scanner clientInput = new Scanner(s.getInputStream());
			PrintWriter clientOutput = new PrintWriter(s.getOutputStream(), true);
			while (! stop) {
				// get next line written by user
				String line = clientInput.nextLine();

				System.out.println(line);

				// send him back something positive
				clientOutput.println("You said " + line + ", THANK YOU");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
