import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.function.Consumer;

public class Client extends Thread{
	Socket socketClient;
	
	ObjectOutputStream out;
	ObjectInputStream in;
	Integer portnum;
	String ipAddress;
	Gameinfo info;
	
	private Consumer<Serializable> callback;//put message for port scene
	private Consumer<Serializable> updateArrayList; //Update the ArrayList For Available Players 
	private Consumer<Serializable> message1,message2; //use for writing message to the client GUI
	private Consumer<Serializable> setGame; //change scene to game and should also be able to display message to the listview
	private Consumer<Serializable> setChallenge; //change scene to pick Opponent
	
	Client(Consumer<Serializable> call,String ip,int port){
		callback = call;
		ipAddress = ip;
		portnum = port;
	}
	
	public void run() {
		try {
			socketClient = new Socket(ipAddress,portnum);
			out = new ObjectOutputStream(socketClient.getOutputStream());
		    in = new ObjectInputStream(socketClient.getInputStream());
		    socketClient.setTcpNoDelay(true);
		}
		catch(Exception e) {
			callback.accept("Unable to Connect to Server");
		}
		while(true) {
			try {
				info = (Gameinfo)in.readObject();
				if(info.typeOfMessage == 5) {
					//the initialization of GameInfo or when player joins or leaves the server.
					System.out.println(info.onlinePlayers);
					updateArrayList.accept("New Player Available");
				}
				else if(info.typeOfMessage == 3) {
					//Client is being challenged(The opp_id is changed)
					setGame.accept("Challenged by Player "+info.opp_id+", Game Start");
					synchronized(this) {//Client is being challenged, wait for player choice
					    try {
					        this.wait(); //Every Button for Choice of Play should notify this thread
					    } catch (Exception e) {
					    }
					}//player_plays will be updated in clientGUI
					message2.accept("You Played "+info.player_plays.toUpperCase());
					info.typeOfMessage = 3;
					out.writeObject(info);
				}
				else if(info.typeOfMessage == 2) {
					if(info.opp_id == -1) {//Challenge is declined
						message1.accept("The Opponent You Picked Is Already In Game");
					}
					else {
						setGame.accept("Player "+info.opp_id+" Accepted Your Challenge, Game Start");
						synchronized(this) {//Client challenged someone else and is successful
						    try {
						        this.wait(); //Every Button for Choice of Play should notify this thread
						    } catch (Exception e) {
						    }
						}
						message2.accept("You Played "+info.player_plays.toUpperCase());
						info.typeOfMessage = 3;
						out.writeObject(info);
					}
				}
				else if(info.typeOfMessage == 4) {
					message1.accept("Your Opponent Played "+info.opp_plays.toUpperCase());
					if(info.winner.equals("Self")) {
						message1.accept("Congratulations! You Won This Round!");
					}
					else if(info.winner.equals("Tie")) {
						message1.accept("Game Tie");
					}
					else {
						message1.accept("Sorry, You Lost This Round.");
					}
					setChallenge.accept("Change Scene to Pick Opponent");
					info = new Gameinfo(info.player);
				}
				else if(info.typeOfMessage == 6) {
					message1.accept("The Opponent is Busy or Has Disconnected From The Server");
					setChallenge.accept("Change Scene to Pick a new Opponent");
					updateArrayList.accept("Modify Online Player List");
				}
			}
			catch(Exception e) {}
		}
		
	}
	public void setGameScene(Consumer<Serializable> s) {
		setGame = s;
	}
	public void setMessage1(Consumer<Serializable> m) {
		message1 = m;
	}
	public void setMessage2(Consumer<Serializable> m) {
		message2 = m;
	}
	public void setList(Consumer<Serializable> a) {
		updateArrayList = a;
	}
	public void setChoice(Consumer<Serializable> c) {
		setChallenge = c;
	}
	public void check() {
		try {
			info.typeOfMessage = 1;
			System.out.println("Hi");
			out.writeObject(info);
		}
		catch(Exception e) {}
		
	}
}
