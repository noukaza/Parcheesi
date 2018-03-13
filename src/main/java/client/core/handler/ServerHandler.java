package client.core.handler;

import client.core.handler.io.ClientInput;
import client.core.handler.io.ClientOutput;
import client.core.util.exeption.ClientProtocolException;

import javax.swing.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ServerHandler implements Runnable {
	private Socket socket;
	private boolean stop;
	private ClientInput clientInput;
	private ClientOutput clientOutput;

	public ServerHandler(Socket socket) throws IOException {
		this.socket = socket;
		System.out.println(socket);
		this.clientOutput = new ClientOutput(this.socket.getOutputStream());
		this.clientInput = new ClientInput(this.socket.getInputStream());
		this.stop = false;
	}

	public void run() {
		try {
			Scanner userInput = new Scanner(System.in);
			Scanner serverInput = new Scanner(socket.getInputStream());
			PrintWriter serverOutput = new PrintWriter(socket.getOutputStream(), true);
			while (!stop) {
				String line = userInput.nextLine();

				serverOutput.println(line);

				String serverResponse = serverInput.nextLine();

				System.out.println(serverResponse);

				clientInput.doRun();

			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void sendName(String name) {
		clientOutput.sendName(name);
	}
	public void creatRoom(String roomName){
		clientOutput.createRoom(roomName);
	}
	public void disconnect(){
		clientOutput.disconnect();
	}
	public void badNAme() {
		String name = JOptionPane.showInputDialog("Enter you'r name ! ");
		sendName(name);


	}
}
