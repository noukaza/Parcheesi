package client.core.handler.io;

import client.core.util.protocol.ClientOutputProtocol;

import java.io.OutputStream;
import java.io.PrintWriter;

public class ClientOutput implements ClientOutputProtocol{

    private PrintWriter out;

    public ClientOutput(OutputStream outputStream) {
        this.out = new PrintWriter(outputStream, true);
    }

    public void sendName(String name) {
        out.println("NAME");
        out.println(name);
    }

    @Override
    public void roomListe() {

    }

    @Override
    public void createRoom(String roomName) {
        out.println("CREATE ROOM");
        out.println(roomName);
    }

    @Override
    public void enterRoom(String roomName){
        out.println("ENTER ROOM");
        out.println(roomName);
    }
    public void startGame(){
        out.println("START GAME");
    }

    @Override
    public void playDice(){
        out.println("PLAY DICE");
    }

    @Override
    public void moveHorse(int horse) {
        out.println("MOVE HORSE");
        out.println(horse);

    }


    @Override
    public void exitRoom(){
        out.println("EXIT ROOM");
    }

    @Override
    public void disconnect(){
        out.println("DISCONNECT");
    }

}
