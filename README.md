##GAME SERVER PROTOCOLE I/O
### Input :
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

	
 
### Output :


	NAME OK
	NAME BAD
	------------
	ROOM ALREADY EXISTS
	ROOM ERROR
	ROOM CREATED
	ROOM ENTERED PLAYER
	ROOM ENTERED SPECTATOR
	ROOM CLOSED
	ROOM DOESNT EXIST
	BAD MOVE
	-------------
	ROOM EXITED
	GOOD BYE
	-------------
	GAME STARTED
	

server sends accept or refuse messages 

	ROOMS LIST
	room-name:[0-4]:[0~inf]
	..
	END

server sends rooms list

	PLAYERS LIST
	user-name
	..
	END

server sends players list

	SPECTATORS LIST
	user-name
	..
	END
	
server sends spectators list

	DICE RESULT
	[1-6]

server returns dice result

	PLAYERS UPDATE
	user-name
	0:1:2:3
	[0-63]:[0-63]:[0-63]:[0-63]
	END

server notify everyone that player moved

	YOUR TURN

server notify everyone who's turn
	
	WINNER IS
	user-name

server sends which player won

	