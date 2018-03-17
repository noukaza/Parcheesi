package server.core.util.event;

import java.util.List;

public interface ServerGameRoomEvents {

	/**
	 * the game room notifies all the players and spectators
	 * that the game has started
	 */
	void notifyGameStarted();

	/**
	 * the game room notifies all the players and spectators
	 * when a new player enters or leaves
	 *
	 * @param players names
	 */
	void notifyPlayersListChanged(List<String> players);

	/**
	 * the game room notifies all the players and spectators
	 * when a new spectators enters or leaves, which means
	 * the number of spectators has changed
	 *
	 * @param spectators number
	 */
	void notifySpectatorsNumberChanged(int spectators);

	/**
	 * the game room notifies all the players and spectators
	 * about the dice result
	 *
	 * @param player name
	 * @param dice   result
	 */
	void notifyDiceResult(String player, int dice);

	/**
	 * the game room notifies all the players and spectators
	 * before it closes.
	 */
	void notifyRoomClosed();

	/**
	 * the game room notifies the player who needs to play now
	 * this notification is only sent to all players
	 * spectators are also notified
	 * but only the one who is considered will be able to play
	 *
	 * @param player name
	 */
	void notifyPlayerTurn(String player);

	/**
	 * the game room notifies when game updates
	 * it notifies all the players and the spectators
	 *
	 * @param lines room status report lines
	 */
	void notifyGameStatusChanged(List<String> lines);

	/**
	 * the game room notifies when we have a winner
	 * it notifies all the players and spectators
	 *
	 * @param player name
	 */
	void notifyWinnerIs(String player);

	/**
	 * the game room notifies when you enter a room as
	 * a player
	 */
	void notifyRoomEnteredPlayer();

	/**
	 * the game room notifies when you enter a room as
	 * a spectator
	 */
	void notifyRoomEnteredSpectator();
}
