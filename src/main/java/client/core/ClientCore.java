package client.core;

import client.core.gui.GameFrame;
import client.core.handler.ServerHandler;

import javax.swing.*;
import java.io.IOException;
import java.net.Socket;

public class ClientCore implements Runnable {
	private String address;
	private int port;
	private Socket socket ;

	private GameFrame model;

	public ClientCore(String address, int port) {
		this.address = address;
		this.port = port;
	}

	@Override
	public void run() {
		try {
			this.socket = new Socket(address, port);
			ServerHandler serverHandler = new ServerHandler(model, this.socket);
			this.model = new GameFrame(serverHandler);
			new Thread(serverHandler).start();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Connection Error", e.getMessage(), JOptionPane.ERROR_MESSAGE);
		}
	}

	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}
}
