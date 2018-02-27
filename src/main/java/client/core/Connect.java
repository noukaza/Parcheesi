package client.core;

import client.core.handler.ServerHandler;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Connect {
	private String address ;
	private int port;
	private Socket socket ;

	public Connect(String address, int port) {
		this.address = address;
		this.port = port;
		try {
			this.socket = new Socket(address, port);
			System.out.println("Connected !!");
			new Thread(new ServerHandler(this.socket)).start();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
