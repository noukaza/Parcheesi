package server.core.util.event;

import java.util.List;

public interface ServerModelEvents {

	void roomStatusChanged(List<String> roomsStatus);

	void shutdownRequested();
}
