package gameLogic;

import java.util.ArrayList;
import java.util.List;

//stores the state of the game
public class GameState {
	private boolean over;
	private Board board;
	private List<Player> players;
	private PlayerOrder playerOrder;
	private Market market;
	
	public GameState() {
		over=true;
		board=new Board();
		players=new ArrayList<Player>();
		playerOrder=null;
		market=new Market();
	}
}
