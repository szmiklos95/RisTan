package gameLogic;

//to add a player to the game, instantiate a Player, get this action with that player, and execute the action on the server and the clients - before the initGameAction
public abstract class AddPlayerAction extends ManagementAction {
	private static final long serialVersionUID = 1L;
	
	private int ID;
	private String name;
	
	public AddPlayerAction(Player player) {
		ID=player.getID();
		name=player.getName();
	}
	
	void execute(GameState gameState)throws GameLogicException{
		gameState.getPlayers().add(new Player(name,ID));
	}
}
