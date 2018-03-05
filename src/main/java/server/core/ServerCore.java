package server.core;

import server.core.handler.ClientHandler;
import server.core.logger.IServerLogger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class ServerCore implements Runnable {

	private int port;
	private ServerSocket serverSocket;
	private boolean stop;

	// TODO add server gui
	private IServerLogger serverLogger;

	public ServerCore(int port) {
		this.port = port;
		serverLogger = new IServerLogger();
		// TODO initiate the server gui

		serverLogger.serverStarting(port);
	}

	public void run() {
		try {
			ServerSocket serverSocket = new ServerSocket(port);
			serverSocket.setSoTimeout(10000);
			while (! stop) {
				try {
					Socket socket = serverSocket.accept();
					serverLogger.clientConnected(socket.getInetAddress().toString());
					new Thread(new ClientHandler(socket, serverLogger)).start();
				} catch (SocketTimeoutException ignored) {
				}
			}
		} catch (IOException e) {
			serverLogger.systemMessage(e.getMessage());
		}
	}

	public synchronized void finish() {
		// TODO empty the gui and remove all players
		this.stop = true;
		serverLogger.serverClosing();
	}
}
