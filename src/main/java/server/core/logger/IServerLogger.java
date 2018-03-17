package server.core.logger;

import server.core.gui.ServerFrame;

public class IServerLogger implements Logger {

	private ServerFrame serverFrame;

	public IServerLogger(ServerFrame serverFrame) {
		this.serverFrame = serverFrame;
	}
	
	@Override
	public void serverStarting(int port) {
		serverFrame.println("Server on port " + port + " is strating ...");
	}

	@Override
	public void serverClosing() {
		serverFrame.println("Server Closing ...");
	}

	@Override
	public void systemMessage(String message) {
		serverFrame.println("System : " + message);
	}

	@Override
	public void clientSentCommand(String ip, String name, String command) {
		serverFrame.println("client: [" + ip + "] " + name + ", sent : {" + command + "}");
	}

	@Override
	public void clientConnected(String ip) {
		serverFrame.println("client: [" + ip + "] connected");
	}

	@Override
	public void clientDisconnected(String ip, String name) {
		serverFrame.println("client: [" + ip + "] disconnected");
	}

	@Override
	public void clientGotName(String ip, String name) {
		serverFrame.println("client: [" + ip + "] got name: " + name);
	}

	@Override
	public void clientCreatedRoom(String ip, String name, String roomName) {
		serverFrame.println("client: [" + ip + "] " + name + ", created: " + roomName);
	}

	@Override
	public void clientEnteredRoom(String ip, String name, String roomName) {
		serverFrame.println("client: [" + ip + "] " + name + ", entred room: " + roomName);
	}

	@Override
	public void clientExitedRoom(String ip, String name) {
		serverFrame.println("client: [" + ip + "] " + name + ", exited room");
	}

	@Override
	public void clientWon(String ip, String name) {
		serverFrame.println("client: [" + ip + "] " + name + " WON!!!");
	}
}
