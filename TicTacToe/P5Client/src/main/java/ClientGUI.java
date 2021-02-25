import javafx.application.Application;
import java.util.HashMap;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
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
public class ClientGUI extends Application{
	public static void main(String[] args) {
		launch(args);
	}
	private ListView<String> direction,gameinfo,traffic;
	private HashMap<String,Scene> sceneMap;
	private Button connect,easy, expert, medium,b1,b2,b3,b4,b5,b6,b7,b8,b9,again,exit;
	private Text first,second,third,p_score,score;
	private TextField portnum,ipadd;
	private Client clientConnection;
	private VBox difficultyBox;
	public String[] stateList;
	private Image tile, o, x;
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		primaryStage.setTitle("Client");
		o = new Image("o.png",50,50,false,false);
		tile = new Image("tile.png",50,50,false,false);
		x = new Image("x.png",50,50,false,false);
		sceneMap = new HashMap<String,Scene>();
		sceneMap.put("connection",createStart());
		sceneMap.put("game", gameScene());
		sceneMap.put("difficulty",difficultyScene());
		connect.setOnAction(e->{
			if(portnum.getText().isEmpty() || ipadd.getText().isEmpty()) {
				direction.getItems().add("Please Enter Both Port Number and IP Address");
			}
			else {
				Integer val = Integer.parseInt(portnum.getText());
				clientConnection = new Client(call->{Platform.runLater(()->{
						traffic.getItems().add(call.toString());
					});	
				},ipadd.getText(),val);

				clientConnection.Connected(c->{Platform.runLater(()->{
					primaryStage.setScene(sceneMap.get("difficulty"));
				});});
				clientConnection.setDirections(d->{Platform.runLater(()->{
					direction.getItems().add(d.toString());
				});});
				clientConnection.setScore(sc->{Platform.runLater(()->{
					score.setText(sc.toString());
				});});
				clientConnection.setState(s->{Platform.runLater(()->{
					gameinfo.getItems().add(s.toString());
					stateList = clientConnection.myinfo.state;
					synchronized(clientConnection) {
						for(int i =0;i < stateList.length;i++) {
							if(stateList[i].equals("X")) {
								setButton(i,"X");
							}
							else if(stateList[i].equals ("O")) {
								setButton(i,"O");
							}
						}
					}
				});});
				clientConnection.endgame(end->{
					Platform.runLater(()->{
						again.setDisable(false);
						first.setText(clientConnection.myinfo.topScore.get(0));
						second.setText(clientConnection.myinfo.topScore.get(1));
						third.setText(clientConnection.myinfo.topScore.get(2));
						disableboard();
					});
				});
				clientConnection.start();
			}
		});
		expert.setOnAction(e->{
			primaryStage.setScene(sceneMap.get("game"));
			again.setDisable(true);
			clientConnection.myinfo.difficulty = "expert"; 
			clientConnection.myinfo.instruction = "difficulty";
			clientConnection.myinfo.word = "client# "+clientConnection.myinfo.Id+" chose " + clientConnection.myinfo.difficulty;
			clientConnection.send(clientConnection.myinfo);
		});
		medium.setOnAction(e->{
			primaryStage.setScene(sceneMap.get("game"));
			again.setDisable(true);
			clientConnection.myinfo.difficulty = "medium";
			clientConnection.myinfo.instruction = "difficulty";
			clientConnection.myinfo.word = "client# "+clientConnection.myinfo.Id+" chose " + clientConnection.myinfo.difficulty;
			clientConnection.send(clientConnection.myinfo);
		});
		easy.setOnAction(e->{
			primaryStage.setScene(sceneMap.get("game"));
			again.setDisable(true);
			clientConnection.myinfo.difficulty = "easy";
			clientConnection.myinfo.instruction = "difficulty";
			clientConnection.myinfo.word = "client# "+clientConnection.myinfo.Id+" chose " + clientConnection.myinfo.difficulty;
			clientConnection.send(clientConnection.myinfo);
		});
		b1.setOnAction(e->{
			clientConnection.myinfo.state[0] = "O";
			stateList = clientConnection.myinfo.state;
			for(int i =0; i < stateList.length;i++) {
				if(stateList[i].equals ("X")) {
					setButton(i,"X");
				}
				else if(stateList[i] == "O") {
					setButton(i,"O");
				}
			}
			clientConnection.myinfo.instruction = "find";
			clientConnection.myinfo.word = "client# "+clientConnection.myinfo.Id+" chose 1";
			clientConnection.send(clientConnection.myinfo);
		});
		b2.setOnAction(e->{
			clientConnection.myinfo.state[1] = "O";
			stateList = clientConnection.myinfo.state;
			for(int i =0; i < stateList.length;i++) {
				if(stateList[i].equals("X")) {
					setButton(i,"X");
				}
				else if(stateList[i].equals("O")) {
					setButton(i,"O");
				}
			}
			clientConnection.myinfo.instruction = "find";
			clientConnection.myinfo.word = "client# "+clientConnection.myinfo.Id+" chose 2";
			clientConnection.send(clientConnection.myinfo);
		});
		b3.setOnAction(e->{
			clientConnection.myinfo.state[2] = "O";
			stateList = clientConnection.myinfo.state;
			for(int i =0; i < stateList.length;i++) {
				if(stateList[i].equals("X")) {
					setButton(i,"X");
				}
				else if(stateList[i].equals("O")) {
					setButton(i,"O");
				}
			}
			clientConnection.myinfo.instruction = "find";
			clientConnection.myinfo.word = "client# "+clientConnection.myinfo.Id+" chose 3";
			clientConnection.send(clientConnection.myinfo);
		});
		b4.setOnAction(e->{
			clientConnection.myinfo.state[3] = "O";
			stateList = clientConnection.myinfo.state;
			for(int i =0; i < stateList.length;i++) {
				if(stateList[i].equals("X")) {
					setButton(i,"X");
				}
				else if(stateList[i].equals("O")) {
					setButton(i,"O");
				}
			}
			clientConnection.myinfo.instruction = "find";
			clientConnection.myinfo.word = "client# "+clientConnection.myinfo.Id+" chose 4";
			clientConnection.send(clientConnection.myinfo);
		});
		b5.setOnAction(e->{
			clientConnection.myinfo.state[4] = "O";
			stateList = clientConnection.myinfo.state;
			for(int i =0; i < stateList.length;i++) {
				if(stateList[i].equals("X")) {
					setButton(i,"X");
				}
				else if(stateList[i].equals("O")) {
					setButton(i,"O");
				}
			}
			clientConnection.myinfo.instruction = "find";
			clientConnection.myinfo.word = "client# "+clientConnection.myinfo.Id+" chose 5";
			clientConnection.send(clientConnection.myinfo);
		});
		b6.setOnAction(e->{
			clientConnection.myinfo.state[5] = "O";
			stateList = clientConnection.myinfo.state;
			for(int i =0; i < stateList.length;i++) {
				if(stateList[i].equals("X")) {
					setButton(i,"X");
				}
				else if(stateList[i].equals("O")) {
					setButton(i,"O");
				}
			}
			clientConnection.myinfo.instruction = "find";
			clientConnection.myinfo.word = "client# "+clientConnection.myinfo.Id+" chose 6";
			clientConnection.send(clientConnection.myinfo);
		});
		b7.setOnAction(e->{
			clientConnection.myinfo.state[6] = "O";
			stateList = clientConnection.myinfo.state;
			for(int i =0; i < stateList.length;i++) {
				if(stateList[i].equals("X")) {
					setButton(i,"X");
				}
				else if(stateList[i].equals("O")) {
					setButton(i,"O");
				}
			}
			clientConnection.myinfo.instruction = "find";
			clientConnection.myinfo.word = "client# "+clientConnection.myinfo.Id+" chose 7";
			clientConnection.send(clientConnection.myinfo);
		});
		b8.setOnAction(e->{
			clientConnection.myinfo.state[7] = "O";
			stateList = clientConnection.myinfo.state;
			for(int i =0; i < stateList.length;i++) {
				if(stateList[i].equals("X")) {
					setButton(i,"X");
				}
				else if(stateList[i].equals("O")) {
					setButton(i,"O");
				}
			}
//				gameinfo.getItems().addAll(stateList);
			
			clientConnection.myinfo.instruction = "find";
			clientConnection.myinfo.word = "client# "+clientConnection.myinfo.Id+" chose 8";
			clientConnection.send(clientConnection.myinfo);
		});
		b9.setOnAction(e->{
			clientConnection.myinfo.state[8] = "O";
			stateList = clientConnection.myinfo.state;
			for(int i =0; i < stateList.length;i++) {
				if(stateList[i].equals("X")) {
					setButton(i,"X");
				}
				else if(stateList[i].equals("O")) {
					setButton(i,"O");
				}
			}
			clientConnection.myinfo.instruction = "find";
			clientConnection.myinfo.word = "client# "+clientConnection.myinfo.Id+" chose 9";
			clientConnection.send(clientConnection.myinfo);
		});
		exit.setOnAction(e->{
			Platform.exit();
            System.exit(0);
		});
		again.setOnAction(e->{
			primaryStage.setScene(sceneMap.get("difficulty"));
			renewboard();
			enableboard();
		});
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                Platform.exit();
                System.exit(0);
            }
        });
		primaryStage.setScene(sceneMap.get("connection"));
		primaryStage.show();
		
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
	private Scene difficultyScene() {
		expert = new Button("expert");
		medium = new Button("medium");
		easy = new Button("easy");
		difficultyBox = new VBox(50,expert,medium,easy);
		Scene scene = new Scene(difficultyBox,300,300);
		return scene;
	}
	private Scene gameScene() {
		gameinfo = new ListView<String>();
		traffic = new ListView<String>();
		HBox row1,row2,row3;
		VBox board;
		BorderPane scores = new BorderPane();
		first = new Text("Top 1:");
		second = new Text("Top 2: ");
		third = new Text("Top 3: ");
		VBox topScore = new VBox(20,first,second,third);
		scores.setLeft(topScore);
		p_score = new Text("Your Score: ");
		score = new Text("0");
		VBox playerScore = new VBox(20,p_score,score);
		scores.setRight(playerScore);
		b1 = new Button();
		b2= new Button();
		b3= new Button();
		b4= new Button();
		b5= new Button();
		b6= new Button();
		b7= new Button();
		b8= new Button();
		b9= new Button();
		renewboard();
		again = new Button("again");
		exit = new Button("exit");
		again.setDisable(true);
		HBox againexit = new HBox(50,again,exit);
		row1 = new HBox(b1,b2,b3);
		row2 = new HBox(b4,b5,b6);
		row3 = new HBox(b7,b8,b9);
		row1.setMaxSize(150, 50);
		row2.setMaxSize(150, 50);
		row3.setMaxSize(150, 50);
		gameinfo.setMaxWidth(200);
		traffic.setMaxWidth(200);
		BorderPane gameBorder = new BorderPane();
		gameBorder.setLeft(gameinfo);
		board = new VBox(0,row1,row2,row3);
		board.setMaxSize(200, 200);
		gameBorder.setTop(scores);
		gameBorder.setCenter(board);
		gameBorder.setRight(traffic);
		gameBorder.setBottom(againexit);
		gameBorder.setPadding(new Insets(20));
		Scene scene = new Scene(gameBorder,700,700);
		return scene;
	}
	private void setButton(int i,String ox) {
		if(i == 0) {
			if(ox == "O"){
				b1.setGraphic(new ImageView(o));
			}
			else {
				b1.setGraphic(new ImageView(x));
			}
			b1.setDisable(true);
		}
		else if(i == 1) {
			if(ox == "O"){
				b2.setGraphic(new ImageView(o));
			}
			else {
				b2.setGraphic(new ImageView(x));
			}
			b2.setDisable(true);
		}
		else if(i == 2) {
			if(ox == "O"){
				b3.setGraphic(new ImageView(o));
			}
			else {
				b3.setGraphic(new ImageView(x));
			}
			b3.setDisable(true);
		}
		else if(i == 3) {
			if(ox == "O"){
				b4.setGraphic(new ImageView(o));
			}
			else {
				b4.setGraphic(new ImageView(x));
			}
			b4.setDisable(true);
		}
		else if(i == 4) {
			if(ox == "O"){
				b5.setGraphic(new ImageView(o));
			}
			else {
				b5.setGraphic(new ImageView(x));
			}
			b5.setDisable(true);
		}
		else if(i == 5) {
			if(ox == "O"){
				b6.setGraphic(new ImageView(o));
			}
			else {
				b6.setGraphic(new ImageView(x));
			}
			b6.setDisable(true);
		}
		else if(i == 6) {
			if(ox == "O"){
				b7.setGraphic(new ImageView(o));
			}
			else {
				b7.setGraphic(new ImageView(x));
			}
			b7.setDisable(true);
		}
		else if(i == 7) {
			if(ox == "O"){
				b8.setGraphic(new ImageView(o));
			}
			else {
				b8.setGraphic(new ImageView(x));
			}
			b8.setDisable(true);
		}
		else if(i == 8) {
			if(ox == "O"){
				b9.setGraphic(new ImageView(o));
			}
			else {
				b9.setGraphic(new ImageView(x));
			}
			b9.setDisable(true);
		}
		
	}
	private void renewboard() {
		b1.setGraphic(new ImageView(tile));
		b2.setGraphic(new ImageView(tile));
		b3.setGraphic(new ImageView(tile));
		b4.setGraphic(new ImageView(tile));
		b5.setGraphic(new ImageView(tile));
		b6.setGraphic(new ImageView(tile));
		b7.setGraphic(new ImageView(tile));
		b8.setGraphic(new ImageView(tile));
		b9.setGraphic(new ImageView(tile));
	}
	private void enableboard() {
		b1.setDisable(false);
		b2.setDisable(false);
		b3.setDisable(false);
		b4.setDisable(false);
		b5.setDisable(false);
		b6.setDisable(false);
		b7.setDisable(false);
		b8.setDisable(false);
		b9.setDisable(false);
	}
	private void disableboard() {
		b1.setDisable(true);
		b2.setDisable(true);
		b3.setDisable(true);
		b4.setDisable(true);
		b5.setDisable(true);
		b6.setDisable(true);
		b7.setDisable(true);
		b8.setDisable(true);
		b9.setDisable(true);
	}

}
