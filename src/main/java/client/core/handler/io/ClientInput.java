package client.core.handler.io;

import client.core.handler.ServerHandler;
import client.core.util.exeption.ClientProtocolException;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

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
	    int number;
	    String line, name;
	    ArrayList<String> list;
        try (BufferedReader buffer = new BufferedReader(new InputStreamReader(in))) {
            while (! connected) {
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
		                    handler.roomError();
                            break;
                        case ROOM_CREATED:
	                        handler.roomCreated();
                            break;
	                    case ROOM_ENTERED_PLAYER:
		                    handler.roomEnteredPlayer();
                            break;
	                    case ROOM_ENTERED_SPECTATOR:
		                    handler.roomEnteredSpectator();
		                    break;
	                    case ROOM_CLOSED:
		                    handler.roomClosed();
                            break;
	                    case ROOM_DOESNT_EXIST:
		                    handler.roomDoesntExist();
                            break;
                        case GAME_STARTED:
	                        handler.gameStarted();
                            break;
                        case ROOMS_LIST:
	                        line = buffer.readLine();
	                        list = new ArrayList<>();
	                        while (! line.equals("END")) {
		                        list.add(line);
		                        line = buffer.readLine();
	                        }
	                        handler.roomList(list);
                            break;
                        case PLAYERS_LIST:
	                        line = buffer.readLine();
	                        list = new ArrayList<>();
	                        while (! line.equals("END")) {
		                        list.add(line);
		                        line = buffer.readLine();
	                        }
	                        handler.playersList(list);
                            break;
	                    case SPECTATORS_NUMBER:
		                    number = Integer.parseInt(buffer.readLine());
		                    handler.spectatorsNumber(number);
                            break;
	                    case PLAYER_TURN:
		                    name = buffer.readLine();
		                    handler.playerTurn(name);
                            break;
                        case DICE_RESULT:
	                        name = buffer.readLine();
	                        number = Integer.parseInt(buffer.readLine());
	                        handler.diceResult(name, number);
                            break;
                        case BAD_MOVE:
	                        handler.badMove();
                            break;
	                    case GAME_UPDATE:
		                    line = buffer.readLine();
		                    list = new ArrayList<>();
		                    while (! line.equals("END")) {
			                    list.add(line);
			                    line = buffer.readLine();
		                    }
		                    handler.gameUpdate(list);
		                    break;
                        case WINNER_IS:
	                        name = buffer.readLine();
	                        handler.winnerIs(name);
                            break;
	                    case GOOD_BYE:
		                    handler.goodBye();
                            break;
	                    case SERVER_OFF:
		                    handler.serverOff();
		                    break;
                        default:
                            throw new ClientProtocolException("Invalid Commande :" + header);
                    }
                }

            }
        }
    }

	public void disconnect() {
		this.connected = false;
	}

}
