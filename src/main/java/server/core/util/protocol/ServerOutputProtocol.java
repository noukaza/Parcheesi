package server.core.util.protocol;

import java.util.List;

public interface ServerOutputProtocol {

	/**
	 * Means that your chosen name is not accepted
	 * <p>
	 * NAME BAD
	 */
	void nameBad();

	/**
	 * Means that your name is accepted
	 * <p>
	 * NAME OK
	 */
	void nameOk();

	/**
	 *
	 */
	void roomError();

	/**
	 *
	 */
	void roomCreated();

	/**
	 *
	 */
	void roomDoesntExist();

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
