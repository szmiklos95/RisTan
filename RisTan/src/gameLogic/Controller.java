package gameLogic;

import java.util.List;

//the game controller
abstract class Controller {
	private GameState gameState;
	
	Controller(){
		gameState=new GameState();
	}
	
	public GameState getGameState() {
		return gameState;
	}
	
	public List<Player> getPlayers(){
		return gameState.getPlayers();
	}
	
	public abstract void executeAction(int playerID,Action action);
}
