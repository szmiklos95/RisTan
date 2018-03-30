package gameLogic;

import java.util.ArrayList;
import java.util.List;

//stores the state of the game
public class GameState {
	private boolean over;
	private Board board;
	private List<Player> players;
	private PlayerOrder playerOrder;
	private TurnOrder turnOrder;
	private Market market;
	
	public GameState() {
		over=true;
		board=new Board();
		players=new ArrayList<Player>();
		playerOrder=null;
		turnOrder=null;
		market=new Market();
	}
	
	public void ExecuteAction(Action action)throws GameLogicException{
		action.check(this);
		action.execute(this);
	}
	
	public boolean isOver() {
		return over;
	}
	
	public Board getBoard() {
		return board;
	}
	
	public Player getActivePlayer() {
		return playerOrder.getActive();
	}
	
	Player getPlayerByID(int ID) {
		for(int i=0;i<players.size();++i) {
			Player player=players.get(i);
			if(player.getID()==ID) {
				return player;
			}
		}
		return null;
	}
	
	Turn getTurn() {
		return turnOrder.getActive();
	}
	
	public Market getMarket() {
		return market;
	}
}
