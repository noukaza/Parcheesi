package server;

import server.core.ServerCore;

/**
 * {@code ServerMain} represents the class main of the server
 *
 * @author Safiy ZAGHBANE
 * @see server.core.ServerCore
 */
public class ServerMain {

	public static void main(String[] args) {
		int port = args.length == 1 ? Integer.parseInt(args[0]) : 1234;
		new Thread(new ServerCore(port)).start();
	}

}
