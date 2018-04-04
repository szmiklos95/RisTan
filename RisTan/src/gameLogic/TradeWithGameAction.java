package gameLogic;

import config.Config;

public class TradeWithGameAction extends TradeAction{
	private static final long serialVersionUID = 1L;
	
	private Resource give;
	private int give_amount;
	private Resource take;
	
	public TradeWithGameAction(int playerID,Resource give,int give_amount,Resource take) {
		super(playerID,Config.Action.TradeWithGameAction.time);
		this.give=give;
		this.give_amount=give_amount;
		this.take=take;
	}
	
	@Override
	void check(GameState gameState)throws GameLogicException{
		super.check(gameState);
		if(!gameState.getActivePlayer().hasResource(give,give_amount)) {
			throw new InsufficientResourceException();
		}
	}
	
	@Override
	void execute(GameState gameState)throws GameLogicException{
		super.execute(gameState);
		Player player=gameState.getActivePlayer();
		player.takeResource(give,give_amount);
		int take_amount=give_amount*Config.Action.TradeWithGameAction.ratio_N/Config.Action.TradeWithGameAction.ratio_D;
		player.giveResource(take,take_amount);
	}
}
