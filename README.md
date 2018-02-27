##GAME SERVER PROTOCOLE I/O
### Input :
	NAME
	user-name
To choose your name in the server or to modify it.

	ROOMS LIST

To get all the rooms from the server.

	CREATE PUBLIC ROOM
	room-name

To create a public room

	CREATE PRIVATE ROOM
	room-name
	pass-word
	
TO create a private room

	ENTER PUBLIC ROOM
	room-name

To enter a public room

	ENTER PRIVATE ROOM
	room-name
	pass-word

To enter a private room, you need the right password

### Output :


	NAME OK
	NAME BAD
	------------
	ROOM ALREADY EXISTS
	ROOM CREATED
	ROOM ENTERED
	ROOM CLOSED
	ROOM DOESNT EXIST
	PASSWORD WRONG

server sends accept or refuse messages 

	ROOMS LIST
	PUBLIC:room-name
	PRIVATE:room-name
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

	PLAYER TURN
	user-name

server notify everyone who's turn
	
	WINNER IS
	user-name

server sends which player won