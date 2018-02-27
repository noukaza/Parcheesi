package client.core;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Connect {
	private String address ;
	private int prot;
	private Socket socket ;

	public Connect(String address, int prot) {
		this.address = address;
		this.prot = prot;
		try {
			this.socket = new Socket(address, prot);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Connected !!");
	}

}
