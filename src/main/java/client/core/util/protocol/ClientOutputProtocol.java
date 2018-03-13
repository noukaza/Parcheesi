package client.core.util.protocol;

public interface ClientOutputProtocol {

    void sendName(String name);

    void roomListe();

    void playDice();

    void moveHorse(int horse);

    void createRoom(String roomName);

    void enterRoom(String roomName);

    void exitRoom();

    void disconnect ();

    void startGame();


}
