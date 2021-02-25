import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.function.Consumer;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.HashMap;
import java.util.function.Consumer;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;


public class Client extends Thread{
	Socket socketClient;
	ObjectOutputStream out;
	ObjectInputStream in;
	private int portnum;
	private String ipadd;
	private Consumer<Serializable> callback;
	private Consumer<Serializable> setState;
	private Consumer<Serializable> directions;
	private Consumer<Serializable> setConnected;
	private Consumer<Serializable> changeScore;
	private Consumer<Serializable> endgame;
	Gameinfo myinfo = new Gameinfo();
	Client(Consumer<Serializable> call,String ip, Integer port){
		portnum = port;
		ipadd = ip;
		callback = call;

	}
	
	public void run() {
		
		try {
			socketClient= new Socket(ipadd,portnum);
		    out = new ObjectOutputStream(socketClient.getOutputStream());
		    in = new ObjectInputStream(socketClient.getInputStream());
		    socketClient.setTcpNoDelay(true);
		    setConnected.accept("Successfully Connected to the Server");
		}
		catch(Exception e) {
			directions.accept("Unable to Connect to the Server, Please Try Again");
		}
		
		while(true) {
			 
			try {
				Gameinfo temp = (Gameinfo) in.readObject();
				if(temp.instruction.equals("initialize")) {
					myinfo = temp;
					callback.accept("initialized");
				}
				else if(temp.instruction.equals("change")) {
					myinfo.state = temp.state;
					setState.accept(temp.word);
				}
				else if(temp.instruction.equals("end")) {
					myinfo.topScore = temp.topScore;
					myinfo.state = temp.state;
					myinfo.wins = temp.wins;
					endgame.accept(temp.word);
					changeScore.accept(myinfo.wins);
				}
				callback.accept(temp.word);
			}
			catch(Exception e) {}
		}
	
    }
	public void send(Gameinfo info){
		
		try {
			out.writeObject(info);
			out.reset();
				
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	//Bunch of callbacks
	public void setState(Consumer<Serializable> s) {
		setState = s;
	}
	public void Connected(Consumer<Serializable> c) {
		setConnected = c;
	}
	public void setDirections(Consumer<Serializable> d) {
		directions = d;
	}
	public void setScore(Consumer<Serializable> sc) {
		changeScore = sc;
	}
	public void endgame(Consumer<Serializable> end) {
		endgame = end;
	}
}
