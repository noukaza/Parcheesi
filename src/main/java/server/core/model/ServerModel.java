package server.core.model;

import server.core.handler.ClientHandler;
import server.core.model.room.ServerGameRoom;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class ServerModel {

	private volatile TreeMap<String, ClientHandler> usersList;
	private volatile TreeMap<String, ServerGameRoom> serverRooms;

	public ServerModel() {
		this.usersList = new TreeMap<>();
		this.serverRooms = new TreeMap<>();
	}

	public synchronized boolean registerUser(String name, ClientHandler clientHandler) {
		if (! existUserName(name)) {
			this.usersList.put(name, clientHandler);
			//notifyRoomStatusChanged();
			return true;
		}
		return false;
	}

	private void notifyRoomStatusChanged() {
		List<String> statusList = getAllRoomsStatus();
		for (ClientHandler user : usersList.values())
			user.notifyRoomStatusChanged(statusList);
	}

	public synchronized void unregisterUser(String name, ClientHandler clientHandler) {
		if (existUserName(name)) {
			ServerGameRoom room = clientHandler.getPlayer().getRoom();
			if (room != null) {
				if (clientHandler.isPlayer()) {
					room.removePlayer(clientHandler);
				} else if (clientHandler.isSpectator()) {
					room.removeSpectator(clientHandler);
				}
				//todo notify users in the room that it has changed
			}
			this.usersList.remove(name);
		}
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
			notifyRoomStatusChanged();
			return true;
		}
		return false;
	}

	public synchronized boolean entreRoom(String userName, String roomName, ClientHandler clientHandler) {
		if (existRoomName(roomName) && existUserName(userName)) {
			ServerGameRoom room = serverRooms.get(roomName);
			if (room.addPlayer(clientHandler)) {
				clientHandler.setClientState(ClientHandler.ClientState.ST_PLAYER);
				//TODO notify all people in room the same room that a new player came
				notifyRoomStatusChanged();
			} else {
				clientHandler.setClientState(ClientHandler.ClientState.ST_SPECTATOR);

			}
			return true;
		}
		return false;
	}

	public synchronized List<String> getAllRoomsStatus() {
		ArrayList<String> rooms = new ArrayList<>();
		for (String roomName : serverRooms.keySet()) {
			ServerGameRoom room = serverRooms.get(roomName);
			String status = room.getPlayers().size() +
					":" +
					room.getSpectators().size();
			rooms.add(roomName);
			rooms.add(status);
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
		for (ClientHandler user : usersList.values())
			user.notifyShutdownRequested();
	}

}
