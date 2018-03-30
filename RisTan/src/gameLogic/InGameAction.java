package gameLogic;

//describes an action in the game
public abstract class InGameAction extends Action{
	private static final long serialVersionUID = 1L;
	
	private int playerID;
	private int time;
	
	
	InGameAction(int playerID){
		init(playerID,0);
	}
	
	InGameAction(int playerID,int time){
		init(playerID,time);
	}
	
	private void init(int playerID,int time) {
		this.playerID=playerID;
		this.time=time;
	}
	
	@Override
	void check(GameState gameState)throws GameLogicException{
		if(gameState.isOver()) {
			throw new GameOverException();
		}
		if(gameState.getActivePlayer().getID()!=playerID) {
			throw new PlayerOutOfTurnException();
		}
		if(gameState.getTurn().getRemainingTime()<time) {
			throw new NotEnoughTimeException();
		}
	}
	
	@Override
	void execute(GameState gameState)throws GameLogicException{
		gameState.getTurn().takeTime(time);
	}
}
