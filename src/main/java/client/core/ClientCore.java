package client.core;

import client.core.handler.ServerHandler;

import javax.swing.*;
import java.io.IOException;
import java.net.Socket;

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
