package gameLogic;

import java.util.ArrayList;
import java.util.List;

import config.Config;

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
		getTurn().checkObligatoryEvents(action);
		action.execute(this);
	}
	
	public boolean isOver() {
		return over;
	}
	
	public Board getBoard() {
		return board;
	}
	
	List<Player> getPlayers(){
		return players;
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
	
	public Turn getTurn() {
		return turnOrder.getActive();
	}
	
	public Market getMarket() {
		return market;
	}
	
	void initGame() {
		board.generate(Config.Board.res_prob);
		playerOrder=new PlayerOrder(players);
		turnOrder=new TurnOrder(Config.TurnOrder.turns);
	}
	
	void startGame() {
		
	}
}
