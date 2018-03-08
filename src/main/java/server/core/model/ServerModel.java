package server.core.model;

import server.core.handler.ClientHandler;
import server.core.model.room.ServerGameRoom;
import server.core.util.event.ServerGameRoomEvents;

import java.util.TreeMap;

public class ServerModel implements ServerGameRoomEvents {

	private volatile TreeMap<String, ClientHandler> usersList;
	private volatile TreeMap<String, ServerGameRoom> serverRooms;

	public ServerModel() {
		this.usersList = new TreeMap<>();
		this.serverRooms = new TreeMap<>();
	}

	public synchronized boolean registerUser(String name, ClientHandler clientHandler) {
		if (! existUserName(name)) {
			this.usersList.put(name, clientHandler);
			// TODO maybe notify all the users that a new user is here
			return true;
		}
		return false;
	}

	public synchronized boolean changeUserName(String oldName, String newName, ClientHandler clientHandler) {
		if (existUserName(oldName) && ! existUserName(newName)) {
			usersList.remove(oldName);
			usersList.put(newName, clientHandler);
			// TODO check if there will be problems
			return true;
		}
		return false;
	}

	private synchronized boolean existUserName(String name) {
		return this.usersList.containsKey(name);
	}

	public synchronized void serverClosing() {
		// TODO tell all the users that we are closing
	}

}
