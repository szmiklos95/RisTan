package gameLogic;

import java.util.ArrayList;
import java.util.List;

import config.Config;

/**
 * Stores the current state of the game.
 * @author Andras
 *
 */
public class GameState {
	/**
	 * True when the game is running, false before and after the game.
	 */
	private boolean over;
	/**
	 * True after the game, false otherwise.
	 */
	private boolean finished;
	/**
	 * The game board.
	 */
	private Board board;
	/**
	 * The players of the game.
	 */
	private List<Player> players;
	/**
	 * Handles the in game order of the players.
	 */
	private PlayerOrder playerOrder;
	/**
	 * Handles the different turns in the game.
	 */
	private TurnOrder turnOrder;
	/**
	 * Handles the trade offers in the game.
	 */
	private Market market;
	/**
	 * True if the automatic actions are executed.
	 */
	private boolean automaticActionsExecuted;
	
	/**
	 * Constructor.
	 */
	public GameState() {
		over=true;
		board=null;
		players=new ArrayList<Player>();
		playerOrder=null;
		turnOrder=null;
		market=null;
		automaticActionsExecuted=false;
		finished=false;
	}
	
	/**
	 * Tries to execute an action. If unsuccessful, an according GameLogicException is thrown and the game state remains unchanged. If successful, also checks for the obligatory events, and if the active player cannot do more in this thur, switches to the next player.
	 * @param action the action to execute.
	 * @throws GameLogicException if the execution is unsuccessful, an according exception is thrown.
	 */
	public void executeAction(Action action)throws GameLogicException{
		action.execute(this);
		if(!over) {
			getTurn().checkObligatoryEvents(action);
			if(checkForSwitchToNextPlayer()) {
				activePlayerEnd();
			}
		}
	}
	
	/**
	 * Gets whether the game is over.
	 * @return false if and only if the game is running.
	 */
	public boolean isOver() {
		return over;
	}
	
	/**
	 * Gets whether the game is finished.
	 * @return true if and only if the game is finished.
	 */
	public boolean isFinished() {
		return finished;
	}
	
	/**
	 * Gets the board.
	 * @return the board.
	 */
	public Board getBoard() {
		return board;
	}
	
	/**
	 * Gets the players.
	 * @return the list of players.
	 */
	public List<Player> getPlayers(){
		return players;
	}
	
	/**
	 * Gets the active player.
	 * @return the active player or null if the game has not started.
	 */
	public Player getActivePlayer() {
		if(playerOrder==null) {
			return null;
		}
		return playerOrder.getActive();
	}
	
	/**
	 * Gets a player by ID.
	 * @param ID the ID of the searched player.
	 * @return the searched player or null if there isn't any player with this ID.
	 */
	Player getPlayerByID(int ID) {
		for(int i=0;i<players.size();++i) {
			Player player=players.get(i);
			if(player.getID()==ID) {
				return player;
			}
		}
		return null;
	}
	
	/**
	 * Gets the current turn.
	 * @return the current turn.
	 */
	public Turn getTurn() {
		if(turnOrder==null) {
			return null;
		}
		return turnOrder.getActive();
	}
	
	/**
	 * Gets the market.
	 * @return the market.
	 */
	public Market getMarket() {
		return market;
	}
	
	/**
	 * Gets the list of possible tile actions.
	 * @return the list of possible tile actions.
	 */
	public List<TileAction> getPossibleTileActions(){
		return getTurn().getPossibleTileActions(this);
	}
	
	/**
	 * Inits the game. Generates a new board, a new player order and loads the turn order. To be called on the server.
	 * @throws GameLogicException if an automatic action of the first player is invalid.
	 */
	void initGame() throws GameLogicException {
		board=new Board();
		board.generate(Config.Board.res_prob);
		playerOrder=new PlayerOrder(players);
		turnOrder=new TurnOrder(Config.TurnOrder.turns);
		market=new Market();
		over=false;
		finished=false;
		//effectively starting the game
		activePlayerStart();
	}
	
	/**
	 * Gets a start game action from which the clients can recreate the starting state of the game. To be called on the server, after the initGame().
	 * @return the start game action which needs to be executed on the clients.
	 */
	public StartGameAction getStartGameAction() {
		String boardGenerator=board.getGeneratorString();
		String playerShuffleOrder=playerOrder.getShuffleOrder();
		String turnOrderGenerator=turnOrder.getGeneratorString();
		return new StartGameAction(boardGenerator,playerShuffleOrder,turnOrderGenerator);
	}
	
	/**
	 * Starts the game with the data of a StartGameAction.
	 * @param boardGenerator the board generator string.
	 * @param playerShuffleOrder the player order generator string.
	 * @param turnOrderGenerator the turn order generator string.
	 * @throws GameLogicException if an automatic action of the first player is invalid.
	 */
	void startGame(String boardGenerator,String playerShuffleOrder,String turnOrderGenerator) throws GameLogicException{
		if(over) {//true on clients, needs to generate game state according to server
			board=new Board();
			board.generate(boardGenerator);
			playerOrder=new PlayerOrder(players,playerShuffleOrder);
			turnOrder=new TurnOrder(turnOrderGenerator);
			market=new Market();
			over=false;
			finished=false;
		}
		//effectively starting the game
		activePlayerStart();
	}
	
	/**
	 * Starts the turn of the active player. It also executes the automatic actions. It the player can't do anything else, switches to the next.
	 * @throws GameLogicException if an automatic action is invalid.
	 */
	void activePlayerStart()throws GameLogicException{
		System.out.println("Player turn begins: "+getActivePlayer().getID());
		getTurn().reset(this);
		automaticActionsExecuted=false;
		List<Action> automaticActions=getTurn().getAutomaticActions();
		for(int i=0;i<automaticActions.size();++i) {
			Action action=automaticActions.get(i);
			action.execute(this);
		}
		automaticActionsExecuted=true;
		if(checkForSwitchToNextPlayer()) {
			activePlayerEnd();
		}
	}
	
	/**
	 * Checks whether it is needed to switch to the next player.
	 * @return true when the active player can't do anything and has executed all the automatic actions and all the obligatory events happened.
	 */
	private boolean checkForSwitchToNextPlayer() {
		boolean can=canSwitchToNextPlayer();
		Turn turn=getTurn();
		return can&&(!turn.canDoAnything(this));
	}
	
	/**
	 * Checks whether it is possible to switch to the next player.
	 * @return true when the active player has executed all the automatic actions and all the obligatory events happened.
	 */
	boolean canSwitchToNextPlayer() {
		if(!automaticActionsExecuted) {
			return false;
		}
		Turn turn=getTurn();
		if(turn.getObligatoryEvents().size()>0) {
			return false;
		}
		return true;
	}
	
	/**
	 * End of the turn of the active player. It also switches to the next player and starts his/her turn. It also switches turn when necessary. Furthermore, detects the end of the game.
	 * @throws GameLogicException if an automatic action of the next player is invalid.
	 */
	void activePlayerEnd() throws GameLogicException {
		System.out.println("Player turn ends: "+getActivePlayer().getID());
		//checking whether there is only one player in the game
		if(!turnOrder.isFirstTurn()) {
			int num_players_in=0;
			for(Player p:players) {
				if(p.getScore()>0) {
					num_players_in++;
				}
			}
			if(num_players_in<=1) {
				finished=true;
			}
		}
		//player switching
		boolean toNextTurn=playerOrder.next();
		if(toNextTurn) {
			over=turnOrder.next();
		}
		if(over) {
			finished=true;
		}else{
			activePlayerStart();
		}
	}
}
