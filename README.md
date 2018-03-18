# Parcheesi

It is a board game, called **ludo** or **les 4 petits cheveaux**

## Getting Started

To play the game, you need to make sure that:
- the server side application is launched and the server has started.
- open as much client side application as you want and connect them to the server on the right *IP* adress and *Port*.

### Prerequisites

What things you need to install the software and how to install them

- Install **Intelij IDEA** *IDE* from [here](https://www.jetbrains.com/idea/).
- Install **Java JDK - 1.8** from [here](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html).
- Install **Maven** framework from [here](https://maven.apache.org/).

in linux you can just do
```
sudo apt-get update
sudo apt-get install maven
```

### Installing

A step by step series of examples that tell you have to get a development env running

Say what the step will be

```
Give the example
```

And repeat

```
until finished
```

End with an example of getting some data out of the system or using it for a little demo

## Running the tests

```
mvn install -B
```

## Deployment

Add additional notes about how to deploy this on a live system

##GAME SERVER PROTOCOLE I/O
### Input:
	NAME
	user-name
To choose your name in the server or to modify it.

	ROOMS LIST

To get all the rooms from the server.

	CREATE ROOM
	room-name

To create a room

	ENTER ROOM
	room-name

To enter a  room

	PLAYERS LIST
To ask for the players list.

	SPECTATORS NUMBER
To ask for the spectators number.

	PLAY DICE

To play the dice, this commande is used only when you are in game and its your turn

	MOVE HORSE
	[0-3]

To move the chosen horse, if the horse can't move
	
	EXIT ROOM
	
To exite a room

	DISCONNECT
	
To disconnect

	START GAME
	
To start the game only the admin can start the game 

	PLAYERS LIST

To request the players list in a room

### Output :

	NAME OK
	NAME BAD
Server accepted or refused the name.

	ROOM ALREADY EXISTS
	ROOM ERROR
	ROOM CREATED
Server accepted or not creating your room.
	
	ROOM ENTERED PLAYER
	ROOM ENTERED SPECTATOR
	ROOM DOESNT EXIST
Server Accepted or not you entering in a room.

	ROOM CLOSED
Server notifies players and spectators when a room is closed.

	BAD MOVE
Server sends this when the player does a bad move.

	GOOD BYE
Server sends this when a client is leaving

	GAME STARTED
Server notifies everyone that the game has started.	

server sends accept or refuse messages 

	ROOMS LIST
	room-name
	[0-4]:[0~inf]
	..
	END

server sends rooms list. [players][spectators].

	PLAYERS LIST
	user-name
	..
	END

server sends players list

	SPECTATORS NUMBER
	[0-inf]
	
server sends spectators number

	DICE RESULT
	player-name
	[1-6]

server returns dice result

	GAME UPDATE
	user-name
	[0-63]:[0-63]:[0-63]:[0-63]
	..
	END

server notify everyone when something change in the game.

	PLAYER TURN
	player-name

server notify everyone who's turn is.
	
	WINNER IS
	user-name

server sends which player won.

	SERVER OFF

server sends notification when it closes.	

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details
