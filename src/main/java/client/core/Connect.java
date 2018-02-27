package client.core;

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
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Connected !!");
	}

}
