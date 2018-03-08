package server.core.player;

import server.core.model.room.ServerGameRoom;

public class Player {

	private static final int NUM_HORSES = 4;

	private String name;

	private boolean turn;
	private boolean roll;

	private int dice;
	private int[] horses;
	private ServerGameRoom room;

	public Player(String name) {
		this.name = name;
		this.turn = false;
		this.roll = false;
		this.dice = 0;
		this.room = null;
		this.horses = new int[NUM_HORSES];
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean canRollDice() {
		return roll;
	}

	public void willRollDice() {
		this.roll = true;
	}

	public void wontRollDice() {
		this.roll = false;
	}

	public boolean canPlay() {
		return turn;
	}

	public void willPlay() {
		this.turn = true;
	}

	public void wontPlay() {
		this.turn = false;
	}

	public void setDice(int dice) {
		this.dice = dice;
	}

	public int getDice() {
		return dice;
	}

	public void moveHorse(int index, int value) {
		this.horses[index] = value;
	}

	public int getHorse(int index) {
		return horses[index];
	}

	public int[] getHorses() {
		return horses;
	}

	public ServerGameRoom getRoom() {
		return room;
	}

	public void setRoom(ServerGameRoom room) {
		this.room = room;
	}
}