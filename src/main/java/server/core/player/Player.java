package server.core.player;

import server.core.model.room.ServerGameRoom;

import java.util.Arrays;

public class Player {

	private static final int NUM_HORSES = 4;

	private String name;

	private boolean admine;
	private boolean turn;
	private boolean roll;

	private int dice;
	private int[] horses;
	private ServerGameRoom room;

	public Player(String name) {
		this.name = name;
		this.room = null;
		this.init();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (obj.getClass() != this.getClass())
			return false;
		Player that = (Player) obj;
		return that.name.equals(this.name)
				&& that.room.equals(this.room)
				&& Arrays.equals(that.horses, this.horses);
	}

	public boolean isAdmine() {
		return admine;
	}

	public void setAdmine(boolean admine) {
		this.admine = admine;
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
		if (horses[index] + value >= 67)
			this.horses[index] = 67;
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

	public void init() {
		this.turn = false;
		this.roll = false;
		this.admine = false;
		this.dice = 0;
		this.horses = new int[NUM_HORSES];
	}

	public boolean won() {
		return (horses[0] == 67 && horses[1] == 67 && horses[2] == 67 && horses[3] == 67);
	}
}