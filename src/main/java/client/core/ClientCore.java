package client.core;

import client.core.gui.GameLayout;
import client.core.handler.ServerHandler;

import javax.swing.*;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class ClientCore implements Runnable {
	private String address;
	private int port;
	private Socket socket ;

	public ClientCore(String address, int port) {
		this.address = address;
		this.port = port;
	}

	@Override
	public void run() {
		ServerHandler serverHandler;
		try {
			this.socket = new Socket(address, port);
			System.out.println("Connected !!");
			serverHandler = new ServerHandler(this.socket);
			new Thread(serverHandler).start();
			GameLayout game = new GameLayout();

			// pour le test
			ArrayList<String> s = new ArrayList<>();
			s.add("1er");
			s.add("2eme");
			s.add("3eme");
			s.add("4eme");
			game.setListeRoom(s);

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
