package gameLogic;

public abstract class TradeAction extends InGameAction {
	private static final long serialVersionUID = 1L;
	
	public TradeAction(int playerID,int time){
		super(playerID,time);
	}
	
	@Override
	void check(GameState gameState)throws GameLogicException{
		super.check(gameState);
		if(!gameState.getTurn().isTradeEnabled()) {
			throw new TradeIsNotAllowedException();
		}
	}
}
