package client.core.handler.io;

import client.core.util.exeption.ClientProtocolException;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ClientInput {
    private static final String NAME_OK = "NAME OK";
    private static final String NAME_BAD = "NAME BAD";

    private static final String ROOM_ALREADY_EXISTS = "ROOM ALREADY EXISTS";
    private static final String ROOM_CREATED = "ROOM CREATED";
    private static final String ROOM_ENTERED = "ROOM ENTERED";
    private static final String ROOM_CLOSED = "ROOM CLOSED";
    private static final String ROOM_DOESNT_EXIST = "ROOM DOESNT EXIST";
    private static final String WELCOM_TO_ROOM = "WELCOM TO ROOM";
    private static final String GAME_STARTED = "GAME STARTED";
    private static final String ROOMS_LIST = "ROOMS LIST";
    private static final String PLAYERS_LIST = "PLAYERS LIST";
    private static final String SPECTATORS_LIST = "SPECTATORS LIST";

    private static final String START_GAME = "START GAME";
    private static final String YOUR_TURN = "YOUR TURN";
    private static final String DICE_RESULT = "DICE RESULT";
    private static final String BAD_MOVE = "BAD MOVE";
    private static final String PLAYERS_UPDATE = "PLAYERS UPDATE";
    private static final String WINNER_IS = "WINNER IS";

    private static final String PLAY_DICE = "PLAY DICE";
    private static final String MOVE_HORSE = "MOVE HORSE";

    private static final String GOOD_BYE = "GOOD BYE";

    private InputStream in;
    private boolean connected;


    public void doRun() throws Exception, ClientProtocolException {

        connected = false;
       try (BufferedReader buffer = new BufferedReader(new InputStreamReader(in))) {
            while (! connected) {
                String header = buffer.readLine();
                switch (header) {
                    case NAME_OK:
                        //TODO ..
                        break;
                    case NAME_BAD:
                        //TODO ..
                        break;
                    case ROOM_ALREADY_EXISTS:
                        //TODO ..
                        break;
                    case ROOM_CREATED:
                        //TODO ..
                        break;
                    case ROOM_ENTERED:
                        //TODO ..
                        break;
                    case ROOM_CLOSED:
                        //TODO ..
                        break;
                    case ROOM_DOESNT_EXIST:
                        //TODO ..
                        break;
                    case WELCOM_TO_ROOM:
                        //TODO ..
                        break;
                    case GAME_STARTED:
                        //TODO ..
                        break;
                    case ROOMS_LIST:
                        //TODO ..
                        break;
                    case PLAYERS_LIST:
                        //TODO ..
                        break;
                    case SPECTATORS_LIST:
                        //TODO ..
                        break;
                    case START_GAME:
                        //TODO ..
                        break;
                    case YOUR_TURN:
                        //TODO ..
                        break;
                    case DICE_RESULT:
                        //TODO ..
                        break;
                    case BAD_MOVE:
                        //TODO ..
                        break;
                    case PLAYERS_UPDATE:
                        //TODO ..
                        break;
                    case WINNER_IS:
                        //TODO ..
                        break;
                    case PLAY_DICE:
                        //TODO ..
                        break;
                    case MOVE_HORSE:
                        //TODO ..
                        break;
                    case GOOD_BYE:
                        //TODO ..
                        break;
                    default:
                        throw new ClientProtocolException("Invalid Commande :" + header);
                }
            }
        }
    }





}
