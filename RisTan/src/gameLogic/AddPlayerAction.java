package gameLogic;

/**
 * Describes an action of adding a player to the game - before initialization.
 * @author Andras
 * 
 */
public class AddPlayerAction extends ManagementAction {
	private static final long serialVersionUID = 1L;
	
	/**
	 * The ID of the added player.
	 */
	private int ID;
	/**
	 * The name of the added player.
	 */
	private String name;
	
	/**
	 * Constructor.
	 * @param player the player from the data is got.
	 */
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
