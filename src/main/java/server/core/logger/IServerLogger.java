package server.core.logger;

public class IServerLogger implements Logger {

	@Override
	public void serverStarting(int port) {
		System.out.println("Server on port " + port + " is strating ...");
	}

	@Override
	public void serverClosing() {
		System.out.println("Server Closing ...");
	}

	@Override
	public void systemMessage(String message) {
		System.out.println("System : " + message);
	}

	@Override
	public void clientSentCommand(String ip, String name, String command) {
		System.out.println("client: [" + ip + "] " + name + ", sent : {" + command + "}");
	}

	@Override
	public void clientConnected(String ip) {
		System.out.println("client: [" + ip + "] connected");
	}

	@Override
	public void clientDisconnected(String ip, String name) {
		System.out.println("client: [" + ip + "] disconnected");
	}

	@Override
	public void clientGotName(String ip, String name) {
		System.out.println("client: [" + ip + "] got name: " + name);
	}

	@Override
	public void clientCreatedRoom(String ip, String name, String roomName) {
		System.out.println("client: [" + ip + "] " + name + ", created: " + roomName);
	}

	@Override
	public void clientEnteredRoom(String ip, String name, String roomName) {
		System.out.println("client: [" + ip + "] " + name + ", entred room: " + roomName);
	}

	@Override
	public void clientExitedRoom(String ip, String name) {
		System.out.println("client: [" + ip + "] " + name + ", exited room");
	}

	@Override
	public void clientWon(String ip, String name) {
		System.out.println("client: [" + ip + "] " + name + " WON!!!");
	}
}
