package gameLogic;

public class GetResourceAction extends InGameAction {
	private static final long serialVersionUID = 1L;
	
	private Resource resource;
	private int amount;
	
	public GetResourceAction(int playerID,Resource resource,int amount){
		super(playerID);
		this.resource=resource;
		this.amount=amount;
	}
	
	@Override
	void execute(GameState gameState)throws GameLogicException{
		super.execute(gameState);
		gameState.getActivePlayer().giveResource(resource,amount);
	}
}
