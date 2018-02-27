package client;

import client.core.Connect;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientMain {

    public static void main(String[] args) {
        new Connect("localhost",1235);
    }

}
