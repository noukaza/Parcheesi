package client.core;

import client.core.gui.GameFrame;
import client.core.handler.ServerHandler;

import javax.swing.*;
import java.io.IOException;
import java.net.Socket;

public class ClientCore implements Runnable {
	private String address;
	private int port;

	public ClientCore(String address, int port) {
		this.address = address;
		this.port = port;
	}

	@Override
	public void run() {
		try {
			Socket socket = new Socket(address, port);
			GameFrame model = new GameFrame();
			ServerHandler serverHandler = new ServerHandler(model, socket);
			model.startModel(serverHandler);
			new Thread(serverHandler).start();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Connection Error", e.getMessage(), JOptionPane.ERROR_MESSAGE);
		}
	}
}
