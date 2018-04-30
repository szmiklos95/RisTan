package gameLogic;

import java.io.Serializable;

/**
 * Describes an action in or before the game. Everything which can modify the game state is an action.
 * @author Andras
 * 
 */
public abstract class Action implements Serializable{
	private static final long serialVersionUID = 1L;
	
	/**
	 * Checks whether the action can be executed at a game state.
	 * It does not change the game state.
	 * If the action cannot be executed, throws an according GameLogicException.
	 * @param gameState the game state for checking the action.
	 * @throws GameLogicException if the action cannot be executed, an according GameLogicException is thrown.
	 */
	abstract void check(final GameState gameState)throws GameLogicException;
	
	/**
	 * Tries to execute the action on a game state.
	 * If the execution is invalid, the game state remains unchanged and throws a GameLogicException of the cause.
	 * @param gameState the game state for executing the action.
	 * @throws GameLogicException if the action cannot be executed, an according GameLogicException is thrown.
	 */
	void execute(final GameState gameState)throws GameLogicException{
		check(gameState);
	}
}
