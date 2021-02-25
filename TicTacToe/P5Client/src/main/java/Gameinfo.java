import java.io.Serializable;
import java.util.ArrayList;

public class Gameinfo implements Serializable{
	private static final long serialVersionUID = 1L;
	ArrayList<String> topScore = new ArrayList<String>();
	String[] state;
	int Id;
	ArrayList<Node> stateList;
	int move;
	int wins;
	String word;
	String difficulty;
	String instruction;
}
