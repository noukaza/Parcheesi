package server.core;

import server.core.handler.ClientHandler;
import server.core.logger.IServerLogger;
import server.core.util.model.ServerModel;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class ServerCore implements Runnable {

	private int port;
	private ServerSocket serverSocket;

	private boolean stop;

	private ServerModel serverModel;
	private IServerLogger serverLogger;

	public ServerCore(int port) {
		this.port = port;
		this.serverLogger = new IServerLogger();
		this.serverModel = new ServerModel();
		this.stop = false;

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
		this.stop = true;
		try {
			serverModel.serverClosing();
			serverSocket.close();
		} catch (IOException e) {
			serverLogger.systemMessage(e.getMessage());
		}
		serverLogger.serverClosing();
	}
}
