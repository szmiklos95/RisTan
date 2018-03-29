package gameLogic;

//describes an action or happening in the game
public abstract class Action {
	abstract void execute(GameState gameState)throws GameLogicException;
}
