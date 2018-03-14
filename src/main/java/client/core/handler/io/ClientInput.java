package client.core.handler.io;

import client.core.handler.ServerHandler;
import client.core.util.exeption.ClientProtocolException;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ClientInput {

    private static final String NAME_OK = "NAME OK";
    private static final String NAME_BAD = "NAME BAD";
	private static final String ROOM_ERROR = "ROOM ERROR";
	private static final String ROOM_CREATED = "ROOM CREATED";
	private static final String ROOM_CLOSED = "ROOM CLOSED";
    private static final String ROOM_DOESNT_EXIST = "ROOM DOESNT EXIST";
	private static final String ROOM_ENTERED_PLAYER = "ROOM ENTERED PLAYER";
	private static final String ROOM_ENTERED_SPECTATOR = "ROOM ENTERED SPECTATOR";
	private static final String ROOMS_LIST = "ROOMS LIST";
	private static final String PLAYERS_LIST = "PLAYERS LIST";
	private static final String SPECTATORS_NUMBER = "SPECTATORS NUMBER";
	private static final String GAME_STARTED = "GAME STARTED";
	private static final String PLAYER_TURN = "PLAYER TURN";
    private static final String DICE_RESULT = "DICE RESULT";
	private static final String WINNER_IS = "WINNER IS";
	private static final String BAD_MOVE = "BAD MOVE";
	private static final String GAME_UPDATE = "GAME UPDATE";
	private static final String SERVER_OFF = "SERVER OFF";
    private static final String GOOD_BYE = "GOOD BYE";

    private InputStream in;
    private boolean connected;
	private ServerHandler handler;

	public ClientInput(ServerHandler handler, InputStream in) {
        this.in = in;
        this.connected = false;
		this.handler = handler;
    }

    public void doRun() throws Exception {

        try (BufferedReader buffer = new BufferedReader(new InputStreamReader(in))) {
            while (! connected) {
                System.out.println("test");

                String header = buffer.readLine();
                if (header !=null){
                    switch (header) {
                        case NAME_OK:
	                        handler.nameOk();
                            break;
                        case NAME_BAD:
	                        handler.nameBad();
                            break;
	                    case ROOM_ERROR:
                            //TODO ..
                            break;
                        case ROOM_CREATED:
                            //TODO ..
                            break;
	                    case ROOM_ENTERED_PLAYER:
                            //TODO ..
                            break;
	                    case ROOM_ENTERED_SPECTATOR:
		                    //TODO ..
		                    break;
	                    case ROOM_CLOSED:
                            //TODO ..
                            break;
	                    case ROOM_DOESNT_EXIST:
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
	                    case SPECTATORS_NUMBER:
                            //TODO ..
                            break;
	                    case PLAYER_TURN:
                            //TODO ..
                            break;
                        case DICE_RESULT:
                            //TODO ..
                            break;
                        case BAD_MOVE:
                            //TODO ..
                            break;
	                    case GAME_UPDATE:
                            //TODO ..
                            break;
                        case WINNER_IS:
                            //TODO ..
                            break;
	                    case GOOD_BYE:
		                    //todo
                            break;
	                    case SERVER_OFF:
		                    //todo
		                    break;
                        default:
                            throw new ClientProtocolException("Invalid Commande :" + header);
                    }
                }

            }
        }
    }

}
