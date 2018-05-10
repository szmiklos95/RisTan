package gameLogic;

import java.util.List;

/**
 * The game controller. Handles the game state.
 * @author Andras
 *
 */
abstract class Controller {
	/**
	 * The handled game state.
	 */
	private GameState gameState;
	
	/**
	 * Constructor.
	 */
	Controller(){
		gameState=new GameState();
	}
	
	/**
	 * Gets the game state.
	 * @return the game state.
	 */
	public GameState getGameState() {
		return gameState;
	}
	
	/**
	 * Gets the players in the game.
	 * @return a list of the players.
	 */
	public List<Player> getPlayers(){
		return gameState.getPlayers();
	}
	
	/**
	 * Executes an action on the game state.
	 * @param playerID the ID of the executing player.
	 * @param action the action to execute.
	 */
	public abstract void executeAction(int playerID,Action action);
}
