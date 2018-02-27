package client.core;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Connect {
	private String HOST ;
	private int PORT;
	private Socket socket ;

	public Connect(String HOST, int PORT) {
		this.HOST = HOST;
		this.PORT = PORT;
		try {
			Socket socket = new Socket(HOST, PORT);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Connected !!");
	}

}
