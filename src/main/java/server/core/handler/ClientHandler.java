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
			Scanner scanner = new Scanner(s.getInputStream());
			PrintWriter printWriter = new PrintWriter(s.getOutputStream());
			while (! stop) {
				// get next line written by user
				String line = scanner.nextLine();
				// print it here
				System.out.println(line);
				// send him back something positive
				printWriter.println("THANK YOU");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
