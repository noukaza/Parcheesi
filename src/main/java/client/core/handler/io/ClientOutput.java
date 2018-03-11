package client.core.handler.io;

import java.io.OutputStream;
import java.io.PrintWriter;

public class ClientOutput {

    private PrintWriter out;

    public ClientOutput(OutputStream outputStream) {
        this.out = new PrintWriter(outputStream, true);
    }

    public void sendName(String name) {
        out.println("NAME");
        out.println(name);
    }
    public void createRoom(String roomName){
        out.println("CREATE ROOM");
        out.println(roomName);
    }
    public void enterRoom(String roomName){
        out.println("ENTER ROOM");
        out.println(roomName);
    }
    public void startGame(){
        out.println("START GAME");
    }

    public void playDice(){
        out.println("PLAY DICE");
    }

    public void moveHorse(int horse){
        out.println("MOVE HORSE");
        out.println(horse);
    }

    public void exitRoom(){
        out.println("EXIT ROOM");
    }

    public void disconnect(){
        out.println("DISCONNECT");
    }

}
