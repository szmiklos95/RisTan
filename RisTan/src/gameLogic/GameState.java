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
	private boolean automaticActionsExecuted;
	
	public GameState() {
		over=true;
		board=null;
		players=new ArrayList<Player>();
		playerOrder=null;
		turnOrder=null;
		market=new Market();
		automaticActionsExecuted=false;
	}
	
	public void executeAction(Action action)throws GameLogicException{
		action.execute(this);
		if(!over) {
			getTurn().checkObligatoryEvents(action);
			if(checkForSwitchToNextPlayer()) {
				activePlayerEnd();
			}
		}
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
		board=new Board();
		board.generate(Config.Board.res_prob);
		playerOrder=new PlayerOrder(players);
		turnOrder=new TurnOrder(Config.TurnOrder.turns);
		over=false;
	}
	
	StartGameAction getStartGameAction() {
		String boardGenerator=board.getGeneratorString();
		String playerShuffleOrder=playerOrder.getShuffleOrder();
		String turnOrderGenerator=turnOrder.getGeneratorString();
		return new StartGameAction(boardGenerator,playerShuffleOrder,turnOrderGenerator);
	}
	
	void startGame(String boardGenerator,String playerShuffleOrder,String turnOrderGenerator) throws GameLogicException{
		if(over) {//true on clients, needs to generate game state according to server
			board=new Board();
			board.generate(boardGenerator);
			playerOrder=new PlayerOrder(players,playerShuffleOrder);
			turnOrder=new TurnOrder(turnOrderGenerator);
			over=false;
		}
		//effectively starting the game
		activePlayerStart();
	}
	
	void activePlayerStart()throws GameLogicException{
		getTurn().reset(this);
		automaticActionsExecuted=false;
		List<Action> automaticActions=getTurn().getAutomaticActions();
		for(int i=0;i<automaticActions.size();++i) {
			Action action=automaticActions.get(i);
			action.execute(this);
		}
		automaticActionsExecuted=true;
	}
	
	private boolean checkForSwitchToNextPlayer() {
		if(!automaticActionsExecuted) {
			return false;
		}
		Turn turn=getTurn();
		if(turn.getObligatoryEvents().size()>0) {
			return false;
		}
		return !turn.canDoAnything(this);
	}
	
	void activePlayerEnd() throws GameLogicException {
		boolean toNextTurn=playerOrder.next();
		if(toNextTurn) {
			over=turnOrder.next();
		}
		if(!over) {
			activePlayerStart();
		}
	}
}
