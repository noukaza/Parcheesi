package server.core.util.event;

public interface ServerModelEvents {

	/**
	 * To set a user name, or change its value
	 * in the server
	 * <p>
	 * NAME
	 * user-name
	 *
	 * @param name the player name
	 */
	void commandeName(String name);

	/**
	 * To request the list of all the rooms
	 * in the server
	 * <p>
	 * ROOMS LIST
	 */
	void commandeRoomList();

	/**
	 * To create new room in the server
	 * <p>
	 * CREATE ROOM
	 * room-name
	 *
	 * @param name the room name
	 */
	void commandeCreateRoom(String name);

	/**
	 * To entre an existing room in the server
	 * <p>
	 * ENTER ROOM
	 * room-name
	 *
	 * @param name the room name
	 */
	void commandeEnterRoom(String name);

	/**
	 * To roll the dice in a running game
	 * <p>
	 * PLAY DICE
	 */
	void commandePlayDice();

	/**
	 * To move a chosen horse in a running game
	 * <p>
	 * MOVE HORSE
	 * [0-3]
	 *
	 * @param horse the chosen horse
	 */
	void commandeMoveTheHorse(int horse);

	/**
	 * To exit a room that you are already in
	 * <p>
	 * EXIT ROOM
	 */
	void commandeExitRoom();

	/**
	 * To disconnect from the server
	 * <p>
	 * DISCONNECT
	 */
	void commandeDisconnect();

	/**
	 * To start the game, but only the creator of the room, or the admine can do this
	 * <p>
	 * START GAME
	 */
	void commandeStartGame();
}
