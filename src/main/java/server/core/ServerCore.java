package server.core;

import server.core.gui.ServerFrame;
import server.core.handler.ClientHandler;
import server.core.logger.IServerLogger;
import server.core.model.ServerModel;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class ServerCore implements Runnable {

	private int port;
	private ServerSocket serverSocket;
	private ServerFrame serverFrame;

	private boolean stop;

	private ServerModel serverModel;
	private IServerLogger serverLogger;

	public ServerCore(ServerFrame serverFrame, int port) {
		this.port = port;
		this.serverLogger = new IServerLogger(serverFrame);
		this.serverModel = new ServerModel();
		this.stop = false;
		serverFrame.registerServer(this);
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
					new Thread(new ClientHandler(socket, serverLogger, serverModel)).start();
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
