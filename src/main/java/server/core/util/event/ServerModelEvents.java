package server.core.util.event;

import java.util.List;

public interface ServerModelEvents {

	/**
	 * the game model notification to all the navigators
	 * that a certain room status has changed
	 *
	 * @param roomsStatus status report lines
	 */
	void notifyRoomStatusChanged(List<String> roomsStatus);

	/**
	 * the game model notification to everyone on the server
	 * that it will shut down
	 */
	void notifyShutdownRequested();

}
