package server.core.model;

import server.core.handler.ClientHandler;
import server.core.model.room.ServerGameRoom;
import server.core.util.event.ServerGameRoomEvents;

import java.util.ArrayList;
import java.util.List;
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
			// TODO notify all the users in his room that his name has changed
			return true;
		}
		return false;
	}

	public synchronized boolean createRoom(String name, ClientHandler admine) {
		if (! existRoomName(name)) {
			ServerGameRoom room = this.serverRooms.put(name, new ServerGameRoom(this, admine));
			admine.getPlayer().setRoom(room);
			// TODO notify new room
			return true;
		}
		return false;
	}

	public List<String> getAllRooms() {
		ArrayList<String> rooms = new ArrayList<>();
		for (String roomName : serverRooms.keySet()) {
			ServerGameRoom room = serverRooms.get(roomName);
			StringBuilder line = new StringBuilder();
			line.append(roomName)
					.append(":")
					.append(room.getPlayers().size())
					.append(":")
					.append(room.getSpectators().size());
			rooms.add(line.toString());
		}
		return rooms;
	}

	private synchronized boolean existUserName(String name) {
		return this.usersList.containsKey(name);
	}

	private synchronized boolean existRoomName(String name) {
		return this.serverRooms.containsKey(name);
	}

	public synchronized void serverClosing() {
		// TODO tell all the users that we are closing
	}

}
