import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.function.Consumer;

import javafx.application.Platform;
import javafx.scene.control.ListView;


public class Server{
	int count = 1;	
	int numberOfPlayers = 0;
	ArrayList<ClientThread> players = new ArrayList<ClientThread>();
	ArrayList<Match> gameMatches = new ArrayList<Match>();
	ServerThread server;
	private Consumer<Serializable> callback1;
	//private Consumer<Serializable> callback2;
	Integer portNum;
	Gameinfo serverMessage;
	
	Server(Consumer<Serializable> call1,Consumer<Serializable> call2, Integer num){
		callback1 = call1;
		//callback2 = call2;
		server = new ServerThread();
		serverMessage = new Gameinfo(-1);
		serverMessage.onlinePlayers.add(0);
		serverMessage.player = -1;
		portNum = num;
		server.start();
	}
	
	public class Match {
		int player1id, player2id;
		String player1play,player2play;
		final String r = "rock", p = "paper", s = "scissors", l = "lizard", sp = "spock"; 
		int winnerID = -1;
		
		Match(int p1, String p1Play, int p2, String p2Play){
			player1id = p1;
			player2id = p2;
			player1play = p1Play;
			player2play = p2Play;
		}
		
		int getWinnerID() {
			winnerID = gameResult();
			return winnerID;
		}
		
		//returns a -1 if there is a tie and -2 if there is no valid play for player 1
		int gameResult() { //the logic of the game 
			switch(player1play) {
			case s: 
				switch(player2play) {
				case p: return player1id;
				case l: return player1id;
				case s: return -1;
				default: return player2id;
				}
			case p:
				switch(player2play) {
				case r: return player1id;
				case sp: return player1id;
				case p: return -1;
				default: return player2id;
				}
			case r:
				switch(player2play) {
				case l: return player1id;
				case s: return player1id;
				case r: return -1;
				default: return player2id;
				}	
			case l:
				switch(player2play) {
				case sp: return player1id;
				case p: return player1id;
				case l: return -1;
				default: return player2id;
				}	
			case sp:
				switch(player2play) {
				case s: return player1id;
				case r: return player1id;
				case sp: return -1;
				default: return player2id;
				}	
			default: return -2;
			}
		}
		
	} 
	
	public class ServerThread extends Thread{
		//ServerThread oppReqHelper, gameMatchHelper, disconnectHelper, playerJoinHelper, playerListHelper;
		ObjectInputStream in;
		ObjectOutputStream out;
		
		public void run() {
			try(ServerSocket mySocket = new ServerSocket(portNum);){
			    while(true) {
			
					ClientThread c = new ClientThread(mySocket.accept(), count);
					numberOfPlayers = count;
					callback1.accept("player has connected to server: " + "player #" + count);
					players.add(c);
					serverMessage.onlinePlayers.add(1);
					c.gi.onlinePlayers = serverMessage.onlinePlayers;
					c.start();
					count++;
				    }
			}//end of try (connection try)
			catch(Exception e) {
				 callback1.accept("OOOOPPs...Server closing down!");
			}
		}//end of run
	
	} //end of ServerThread class
	
		class ClientThread extends Thread{
			
			Gameinfo gi;
			Socket connection;
			ObjectInputStream in;
			ObjectOutputStream out;
			
			ObjectOutputStream outConn;
			
			
			ClientThread(Socket s, int id){
				this.connection = s;	
				gi = new Gameinfo(id);
				gi.typeOfMessage = 2;
				try { this.out.writeObject(gi);}
				catch(Exception e) {}
			}
			
			public void updateAllPlayers() {
				for(int i = 0; i < players.size(); i++) {
					ClientThread t = players.get(i);
					try {
					 t.out.writeObject(gi);
					 t.out.reset();
					}
					catch(Exception e) {}
				}	
			}
			
			public void updateSpecificClient( Gameinfo gi) {
					ClientThread t = players.get(gi.player);
					try {
					 t.out.writeObject(gi);
					}
					catch(Exception e) {}
			} 
			
			public void run() {
				try {
					in = new ObjectInputStream(connection.getInputStream());
					out = new ObjectOutputStream(connection.getOutputStream());
					connection.setTcpNoDelay(true);
				}
				catch(Exception e) {
					System.out.println("Streams not open");
				}
				gi.typeOfMessage = 5;
				updateAllPlayers();
				gi.typeOfMessage = 7; //no specific message
				
				 while(true) {
					    try {
					    	Gameinfo message = (Gameinfo) in.readObject();
					    	
					    	switch(message.typeOfMessage) { 
					    	
					    	case 0: //0 Being challenged
					    		gi.challenged = 1;
					    		gi.onlinePlayers.set(gi.player, 2);
					    		gi.onlinePlayers.set(message.player, 2);
					    		gi.opp_id = message.player;
					    		updateAllPlayers();
					    		gi.typeOfMessage = 3;   
					    		this.out.writeObject(gi);   //we let the Client GUI know we need a play
					    		break;
					    		
					    	case 1:  //1 Opponent has played
					    		gi.challenged = 0;
					    		//ask for our own play from Client GUI
					    		gi.typeOfMessage = 3;
					    		this.out.writeObject(gi);  //we let the Client GUI know we need a play
					    		gi.typeOfMessage = 7;
					    		break;
					    	case 2:	//2 Player has chosen an opponent
					    		if (gi.opp_id > 0 && gi.opp_id <= gi.onlinePlayers.size()-1 && gi.opp_id != gi.player && gi.onlinePlayers.get(gi.opp_id) == 1) {
					    			gi.typeOfMessage = 0;
					    			players.get(gi.opp_id).out.writeObject(gi);
					    		}
					    		else {
					    			gi.opp_id = -1;
				    				gi.typeOfMessage = 2; 
				    				this.out.writeObject(gi);
					    		}
					    		break;
					    	case 3:	//3 Player has played
					    		if (gi.challenged == 0) { // I challenged someone else 
					    			Match thisMatch = new Match(gi.player, gi.player_plays, message.player, message.player_plays);
						    		int winner = thisMatch.getWinnerID();
						    		if (winner == gi.player) {
						    			gi.winner = "Self";
						    			players.get(message.player).gi.winner = "Opp";
						    		}
						    		else {
						    			gi.winner = "Opp";
						    			players.get(message.player).gi.winner = "Self";
						    		}
						    		gameMatches.add(thisMatch);
						    		gi.onlinePlayers.set(gi.player, 1);
						    		gi.onlinePlayers.set(message.player, 1);
						    		gi.typeOfMessage = 5;
						    		updateAllPlayers();
						    		
						    		gi.typeOfMessage = 2;
						    		players.get(message.player).gi.typeOfMessage = 2;
						    		gi.opp_id = -1;
						    		players.get(message.player).gi.opp_id = -1;
						    		gi.player_plays = "no-play";
						    		players.get(message.player).gi.player_plays = "no-play";
						    		this.out.writeObject(gi);   //we ask the Client GUI for a new opponent
						    		players.get(message.player).out.writeObject(players.get(message.player).gi); //we ask the Client GUI for a new opponent
					    		}
					    		else {  // I was challenged
					    			gi.typeOfMessage = 1;
						    		players.get(message.player).out.writeObject(gi);
						    		gi.typeOfMessage = 7; //no specific message
						    		gi.player_plays = "no-play";
					    		}
					    		break;
					    	case 5:	//5 Update the ArrayList
					    		gi.onlinePlayers = message.onlinePlayers;
					    		break;
					    	case 6:	//6 Opponent Disconnected
					    		gi.onlinePlayers.set(gi.player, 1);
					    		gi.challenged = 0;
					    		gi.typeOfMessage = 2;
					    		this.out.writeObject(gi);
					    		break;
					    	case 7: //7 No Specific Message
					    		break;
					    	default: 
					    	}//end of switch
					    }//end of try
					    catch(Exception e) {
					    	numberOfPlayers--;
					    	callback1.accept("OOOOPPs...Something wrong with the socket from player: " + gi.player + "....closing down!");
					    	gi.onlinePlayers.set(gi.player, 0);
					    	if(gi.onlinePlayers.get(gi.player) == 2) {
					    		gi.typeOfMessage = 6;
					    		try {players.get(gi.opp_id).out.writeObject(gi);}
					    		catch(Exception e1) {}
					    	}
					    	gi.typeOfMessage = 5;
					    	updateAllPlayers();
					    	players.remove(this);
					    	break;
					    }
					}//end of while
			}//end of run
		}//end of clientThread
} //end of Server
