package client;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientMain {

    public static void main(String[] args) {
        try {
            Socket socket = new Socket(args[0], Integer.parseInt(args[1]));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
