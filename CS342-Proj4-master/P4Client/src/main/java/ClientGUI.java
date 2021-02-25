import java.util.HashMap;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class ClientGUI extends Application {
	private TextField portnum,ipadd,opponent;
	private Text point1,point2,decide;
	private Button connect,challenge;
	private Button ro,pa,sc,li,sp;
	private ListView<String> direction,message1,message2,available;
	private HashMap<String,Scene> sceneMap;
	private Client clientConnection;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

	//feel free to remove the starter code from this method
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		primaryStage.setTitle("Client");
		sceneMap = new HashMap<String,Scene>();
		sceneMap.put("start",createStart());
		sceneMap.put("select", createSelect());
		sceneMap.put("game", createGame());
		connect.setOnAction(e->{
			if(portnum.getText().isEmpty() || ipadd.getText().isEmpty()) {
				direction.getItems().add("Please Enter Both Port Number and IP Address");
			}
			else {
				Integer val = Integer.parseInt(portnum.getText());
				//set callback
				clientConnection = new Client(data->{Platform.runLater(()->{
					direction.getItems().add(data.toString());
					primaryStage.setScene(sceneMap.get("start"));
				});},ipadd.getText(), val);
				//set start
				clientConnection.setGameScene(start->{Platform.runLater(()->{
					message2.getItems().add(start.toString());
					enableButtons();
					primaryStage.setScene(sceneMap.get("game"));
				});});
				//set message
				clientConnection.setMessage1(m->{Platform.runLater(()->{
					message1.getItems().add(m.toString());
				});});
				clientConnection.setMessage2(m->{Platform.runLater(()->{
					message2.getItems().add(m.toString());
				});});
				//set setChallenge
				clientConnection.setChoice(c->{Platform.runLater(()->{
					primaryStage.setScene(sceneMap.get("select"));
					challenge.setDisable(false);
				});});
				//set updateArrayList
				clientConnection.setList(update->{Platform.runLater(()->{
					synchronized (clientConnection) {
						message1.getItems().add(update.toString());
						available.getItems().clear();
						for(int i = 0; i<clientConnection.info.onlinePlayers.size();i++) {
							if(clientConnection.info.onlinePlayers.get(i) == 1) {
								available.getItems().add("Player "+(i+1));
							}
						}
					}
				});
					
				});
				portnum.clear();
				ipadd.clear();
				clientConnection.start();
				primaryStage.setScene(sceneMap.get("select"));
			}
		});
		challenge.setOnAction(e->{
			if(opponent.getText().isEmpty()) {
				message1.getItems().add("Please Enter An Valid ID");
			}
			else {
				int opp_id = Integer.parseInt(opponent.getText());
				if(opp_id<=0||opp_id>clientConnection.info.onlinePlayers.size()) {
					message1.getItems().add("Please Enter An Valid ID");
				}
				else if(opp_id == clientConnection.info.player) {
					message1.getItems().add("You Cannot Challenge Yourself");
				}
				else {
					synchronized (clientConnection) {
						clientConnection.info.opp_id = opp_id;
						challenge.setDisable(true);
						clientConnection.check();
					}
					/*synchronized(clientConnection) {
						clientConnection.notify();
					}*/
				}
			}
		});
		ro.setOnAction(e->{
			clientConnection.info.player_plays = "rock";
			synchronized(clientConnection) {
				clientConnection.notify();
			}
			disableButtons();
		});
		pa.setOnAction(e->{
			clientConnection.info.player_plays = "paper";
			synchronized(clientConnection) {
				clientConnection.notify();
			}
			disableButtons();
		});
		sc.setOnAction(e->{
			clientConnection.info.player_plays = "scissors";
			synchronized(clientConnection) {
				clientConnection.notify();
			}
			disableButtons();
		});
		li.setOnAction(e->{
			clientConnection.info.player_plays = "lizard";
			synchronized(clientConnection) {
				clientConnection.notify();
			}
			disableButtons();
		});
		sp.setOnAction(e->{
			clientConnection.info.player_plays = "spock";
			synchronized(clientConnection) {
				clientConnection.notify();
			}
			disableButtons();
		});
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                Platform.exit();
                System.exit(0);
            }
        });
		primaryStage.setScene(sceneMap.get("start"));
		primaryStage.show();
	}
	private void disableButtons() {
		ro.setDisable(true);
		pa.setDisable(true);
		sc.setDisable(true);
		li.setDisable(true);
		sp.setDisable(true);
	}
	private void enableButtons() {
		ro.setDisable(false);
		pa.setDisable(false);
		sc.setDisable(false);
		li.setDisable(false);
		sp.setDisable(false);
	}
	private Scene createStart() {
		direction = new ListView<String>();
		Text port = new Text("Port:");
		portnum = new TextField("5555");
		ipadd = new TextField("127.0.0.1");
		Text ip = new Text("IP address:");
		connect = new Button("Connect");
		VBox connection = new VBox(10,port,portnum,ip,ipadd,connect);
		VBox pane = new VBox(10,connection,direction);
		direction.getItems().addAll("Enter Port number and IP address","Press Connect");
		Scene scene = new Scene(pane,1000,600);
		return scene;
	}
	private Scene createSelect() {
		message1 = new ListView<String>();
		opponent = new TextField();
		available = new ListView<String>();
		challenge = new Button("Challenge!!!");
		Text opp_id = new Text("Enter Opponent ID:");
		message1.getItems().add("Select A Player To Challenge");
		BorderPane pane = new BorderPane();
		VBox selection = new VBox(10,opp_id,opponent,challenge);
		pane.setLeft(message1);
		pane.setRight(available);
		pane.setCenter(selection);
		pane.setStyle("-fx-background-color: coral");
		Scene scene = new Scene(pane,1000,600);
		return scene;
	}
	private Scene createGame() {
		message2 = new ListView<String>();
		Image pic1 = new Image("rock.png");
		Image pic2 = new Image("paper.png");
		Image pic3 = new Image("scissors.png");
		Image pic4 = new Image("lizard.png");
		Image pic5 = new Image("spock.png");
		ro = new Button("Rock",new ImageView(pic1));
		pa = new Button("Paper",new ImageView(pic2));
		sc = new Button("Scissors",new ImageView(pic3));
		li = new Button("Lizard",new ImageView(pic4));
		sp = new Button("Spock",new ImageView(pic5));
		HBox choice = new HBox(50,ro,pa,sc,li,sp);
		message2.getItems().add("Select one from above");
		BorderPane pane = new BorderPane();
		pane.setCenter(choice);
		pane.setBottom(message2);
		pane.setStyle("-fx-background-color: coral");
		Scene scene = new Scene(pane,1000,600);
		return scene;
	}
	/*private Scene createDecide() {
		decide = new Text("");
		yes = new Button("Yes");
		no = new Button("No");
		HBox choice = new HBox(100,yes,no);
		choice.setAlignment(Pos.CENTER);
		VBox pane = new VBox(200,decide,choice);
		pane.setAlignment(Pos.CENTER);
		pane.setStyle("-fx-background-color: coral");
		Scene scene = new Scene(pane,1000,600);
		return scene;
	}*/

}
