package server.core.util.protocol;

import java.util.List;

public interface ServerOutputProtocol {

	/**
	 * Means that your chosen name is not accepted
	 *
	 * NAME BAD
	 *
	 */
	void nameBad();

	/**
	 * Means that your name is accepted
	 *
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
	 * Means that the room is closed
	 *
	 * ROOM CLOSED
	 */
	void roomClosed();

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
	 *
	 * ROOM ENTERED SPECTATOR
	 *
	 */
	void roomEnteredSpectator();

	/**
	 * A Response with all the existing rooms in the server,
	 * including the number of players and spectators
	 *
	 * ROOMS LIST
	 * room-name
	 * [0-4]:[0~inf]
	 * ..
	 * END
	 *
	 * @param rooms all the rooms in the server
	 */
	void roomList(List<String> rooms);

	/**
	 * A Response with all the existing players in a room
	 *
	 * PLAYERS LIST
	 * user-name
	 * ..
	 * END
	 *
	 * @param players all the players in the room
	 */
	void playersList(List<String> players);

	/**
	 * A Response with all the existing spectators in a room
	 *
	 * SPECTATORS NUMBER
	 * [0-inf]
	 *
	 * @param spectators number of spectators
	 */
	void spectatorsNumber(int spectators);


	/**
	 * A Response with the result of a player dice
	 *
	 * DICE RESULT
	 * player-name
	 * [1-6]
	 *
	 * @param player name
	 * @param value of the played dice
	 */
	void diceResult(String player, int value);

	/**
	 * The game notification to all players and spectators
	 *
	 * GAME UPDATE
	 * user-name
	 * [0-63]:[0-63]:[0-63]:[0-63]
	 * ...
	 * END
	 *
	 * @param lines update lines
	 */
	void gameUpdate(List<String> lines);

	/**
	 * The game notification to the player who needs to play
	 *
	 * YOUR TURN
	 *
	 */
	void playerTurn(String player);

	/**
	 * the game notification when the player does
	 * a bad move, either chosen a wrong horse or
	 * tried to do a bad commande
	 *
	 * BAD MOVE
	 */
	void badMove();
	/**
	 * The game notification that the game has started
	 *
	 * GAME STARTED
	 *
	 */
	void gameStarted();

	/**
	 * The game notification to the player who won
	 *
	 * WINNER IS
	 * user-name
	 *
	 * @param player name
	 */
	void winnerIs(String player);

	/**
	 * Server notification when it closes
	 *
	 * SERVER OFF
	 *
	 */
	void serverOff();

	/**
	 * Server notification to user when he leaves
	 *
	 * GOOD BYE
	 *
	 */
	void goodBye();
}
