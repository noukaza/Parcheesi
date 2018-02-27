package client;

import client.core.ClientCore;

public class ClientMain {

    public static void main(String[] args) {
	    String address = args.length > 1 ? args[0] : "localhost";
	    int port = args.length > 2 ? Integer.parseInt(args[1]) : 1234;
	    new Thread(new ClientCore(address, port)).start();
    }

}
