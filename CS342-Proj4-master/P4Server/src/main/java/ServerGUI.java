import java.util.HashMap;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class ServerGUI extends Application {
	private ListView<String> gameinfo,direction;
	private HashMap<String,Scene> sceneMap;
	private Button b1;
	private Text pnum,cnum;
	private TextField portnum;
	private Server serverConnection;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

	//feel free to remove the starter code from this method
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		primaryStage.setTitle("Server");
		sceneMap = new HashMap<String,Scene>();
		sceneMap.put("start",createStart());
		sceneMap.put("close", createClose());
		b1.setOnAction(e->{
			if(portnum.getText().isEmpty()) {
				direction.getItems().add("Please Enter a Port Number");
			}
			else {
				primaryStage.setScene(sceneMap.get("close"));
				gameinfo.getItems().clear();
				gameinfo.getItems().addAll("GameInfo:","Waiting on Client to Connect");
				Integer val = Integer.parseInt(portnum.getText());
				pnum.setText(portnum.getText());
				serverConnection = new Server(data->{Platform.runLater(()->{
					gameinfo.getItems().add(data.toString());
				});},edit->{Platform.runLater(()->{
					cnum.setText(edit.toString());
				});}, val);
			}
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
	
	private Scene createStart() {
		HBox pane = new HBox(20);
		Text ptext = new Text("Port Number:");
		portnum = new TextField("5555");
		VBox port1 = new VBox(10,ptext,portnum);
		direction = new ListView<String>();
		b1 = new Button("Start");
		direction.getItems().addAll("Enter the Port Number","Press Start to Start the Server");
		gameinfo = new ListView<String>();
		VBox choiceBox = new VBox(20,port1,b1);
		pane.getChildren().addAll(direction,choiceBox);
		Scene scene = new Scene(pane,600,600);
		return scene;
	}
	private Scene createClose() {
		BorderPane pane = new BorderPane();
		Text ptext = new Text("Port Number:");
		Text client = new Text("Client connected:");
		pnum = new Text();
		cnum = new Text("0");
		VBox port2 = new VBox(10,ptext,pnum,client,cnum);
		gameinfo = new ListView<String>();
		HBox choiceBox = new HBox(20,port2);
		pane.setCenter(gameinfo);
		pane.setTop(choiceBox);
		Scene scene = new Scene(pane,600,600);
		return scene;
	}

}
