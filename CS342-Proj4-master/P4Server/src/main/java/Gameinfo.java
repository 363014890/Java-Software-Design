import java.io.Serializable;
import java.util.ArrayList;

public class Gameinfo implements Serializable {
	
	/**
	 * 
	 */
	public Gameinfo(int p) {
		// TODO Auto-generated constructor stub
		player = p;
		opp_id = -1;
		typeOfMessage = -1;
		player_plays = "no-play";
		opp_plays = new String();
		winner = new String();
		onlinePlayers = new ArrayList<Integer>();
		typeOfMessage = 2;
		challenged = 0;
	}
	
	private static final long serialVersionUID = 1L;
	String player_plays,opp_plays;
	int player,opp_id, challenged; //0-No  1-Yes
	String winner; //Self-> player won, Opp->Opponent won
	ArrayList<Integer> onlinePlayers;//0 offline, 1 available, 2 in game
	int typeOfMessage; 
	//0 Being challenged
	//1 Opponent has played
	//2 Player has chosen an opponent
	//3 Player has played
	//5 Update the ArrayList
	//6 Opponent Disconnected
	//7 No Specific Message
}
