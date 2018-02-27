package server.core;

import server.core.handler.ClientHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class ServerCore implements Runnable {

	private int port;
	private ServerSocket serverSocket;
	private boolean stop;

	// TODO add server model
	// TODO add server logger

	public ServerCore(int port) {
		this.port = port;
		// TODO initiate the logger
		// TODO initiate the server model
		// TODO that server is connecting on a port in the logger and its starting
	}

	public void run() {
		try {
			ServerSocket serverSocket = new ServerSocket(port);
			serverSocket.setSoTimeout(10000);
			while (! stop) {
				try {
					Socket socket = serverSocket.accept();
					System.out.println("User : " + socket);
					// TODO write on logger that client has connected and write his socket details
					new Thread(new ClientHandler(socket)).start();
				} catch (SocketTimeoutException ignored) {
				}
			}
		} catch (IOException e) {
			// TODO write on logger that we couldn't open the server on the chosen port
		}
	}

	public synchronized void finish() {
		// TODO empty the model and remove all players
		this.stop = true;
	}
}
