package server.core.logger;

public interface Logger {

	void serverStarting(int port);

	void serverClosing();

	void systemMessage(String message);

	void clientSentCommand(String ip, String name, String command);

	void clientConnected(String ip);

	void clientDisconnected(String ip, String name);

	void clientGotName(String ip, String name);

	void clientCreatedPublicRoom(String ip, String name, String roomName);

	void clientEnteredRoom(String ip, String name, String roomName);

	void clientExitedRoom(String ip, String name, String roomName);

	void clientWon(String ip, String name, String roomName);

	void clientLost(String ip, String name, String roomName);

	void clientClosedRoom(String ip, String name, String roomName);

}
