package server.core.util.protocol;

import java.util.List;

public interface ServerOutputProtocol {

	/**
	 * Means that your chosen name is not accepted
	 * <p>
	 * NAME BAD
	 *
	 */
	void nameBad();

	/**
	 * Means that your name is accepted
	 * <p>
	 * NAME OK
	 *
	 */
	void nameOk();

	/**
	 * Means that you couldn't create the room,
	 * either the name you have chosen is bad or the name already exists
	 *
	 * ROOM ERROR
	 *
	 */
	void roomError();

	/**
	 * Means that you could create the room
	 *
	 * ROOM CREATED
	 *
	 */
	void roomCreated();

	/**
	 * Means that the room doesn't exist if you're trying to entre it
	 *
	 * ROOM DOESNT EXIST
	 *
	 */
	void roomDoesntExist();

	/**
	 * Means that you have succesfully entered the room
	 *
	 * ROOM ENTERED PLAYER
	 *
	 */
	void roomEnteredPlayer();

	/**
	 * Means that you have succesfully entered a the room as a spectator
	 * <p>
	 * ROOM ENTERED SPECTATOR
	 */
	void roomEnteredSpectator();

	/**
	 * A Response with all the existing rooms in the server,
	 * including the number of players and spectators
	 * <p>
	 * ROOMS LIST
	 * room-name:[0-4]:[0~inf]
	 * ..
	 * END
	 *
	 * @param rooms all the rooms in the server
	 */
	void roomList(List<String> rooms);

}
