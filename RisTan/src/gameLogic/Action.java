package gameLogic;

import java.io.Serializable;

//describes an action in or before the game
public abstract class Action implements Serializable{
	private static final long serialVersionUID = 1L;
	
	abstract void check(GameState gameState)throws GameLogicException;
	
	abstract void execute(GameState gameState)throws GameLogicException;
}
