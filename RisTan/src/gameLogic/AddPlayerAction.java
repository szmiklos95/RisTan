package gameLogic;

//to add a player to the game, instantiate a Player, get this action with that player, and execute the action on the server and the clients - before the initGameAction
public class AddPlayerAction extends ManagementAction {
	private static final long serialVersionUID = 1L;
	
	private int ID;
	private String name;
	
	public AddPlayerAction(Player player) {
		ID=player.getID();
		name=player.getName();
	}
	
	@Override
	void execute(GameState gameState)throws GameLogicException{
		super.execute(gameState);
		gameState.getPlayers().add(new Player(name,ID));
	}
}