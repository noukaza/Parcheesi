package client.core;

import client.core.handler.ServerHandler;

import javax.swing.*;
import java.io.IOException;
import java.net.Socket;

public class ClientCore implements Runnable {
	private String address;
	private int port;
	private Socket socket ;

	//todo add the client model

	public ClientCore(String address, int port) {
		this.address = address;
		this.port = port;
		//todo init client model
	}

	@Override
	public void run() {
		try {
			this.socket = new Socket(address, port);
			new Thread(new ServerHandler(/*todo add the client model*/this.socket)).start();
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
