package gameLogic;

import config.Config;

/**
 * Describes an action of upgrading a village to a town. This action requires time and resources.
 * @author Andras
 *
 */
public class BuildTown extends TileAction {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructor.
	 * @param playerID the ID of the action executing player.
	 * @param point the coordinates of the target tile (in board coordinate system).
	 */
	public BuildTown(int playerID,Point point){
		super(playerID,
				Config.Action.BuildTown.time,
				point,
				Config.Action.BuildTown.cost);
	}
	
	/**
	 * Checks whether the action can be executed at a game state.
	 * It does not change the game state.
	 * If the action cannot be executed, throws an according GameLogicException.<br>
	 * If the game is over, throws a GameOverException.<br>
	 * If the executing player is not the active, throws a PlayerOutOfTurnException.<br>
	 * If the executing player does not have enough time, throws a NotEnoughTimeException. <br>
	 * If no tile exists at the target coordinates, throws a NoTileAtPointException. <br>
	 * If the executing player does not have enough resources, throws an InsufficientResourceException. <br>
	 * If this tile action is invalid in the actual turn, throws an InvaligTileActionException. <br>
	 * If the target tile is not owned by the action executing player, throws a TileIsNotOwnException. <br>
	 * If the target tile is not a village, throws a TileIsNotVillageException.
	 */
	@Override
	void check(GameState gameState)throws GameLogicException{
		super.check(gameState);
		Tile tile=gameState.getBoard().getTileAt(getPoint());
		if(tile.getOwner()!=gameState.getActivePlayer()) {
			throw new TileIsNotOwnException();
		}
		if(tile.getBuildingLevel()!=BuildingLevel.Village) {
			throw new TileIsNotVillageException();
		}
	}
	
	/**
	 * Tries to execute the action on a game state.
	 * If the execution is invalid, the game state remains unchanged and throws a GameLogicException of the cause.<br>
	 * If the game is over, throws a GameOverException.<br>
	 * If the executing player is not the active, throws a PlayerOutOfTurnException.<br>
	 * If the executing player does not have enough time, throws a NotEnoughTimeException. <br>
	 * If no tile exists at the target coordinates, throws a NoTileAtPointException. <br>
	 * If the executing player does not have enough resources, throws an InsufficientResourceException. <br>
	 * If this tile action is invalid in the actual turn, throws an InvaligTileActionException. <br>
	 * If the target tile is not owned by the action executing player, throws a TileIsNotOwnException. <br>
	 * If the target tile is not a village, throws a TileIsNotVillageException.
	 */
	@Override
	void execute(GameState gameState)throws GameLogicException{
		super.execute(gameState);
		gameState.getBoard().getTileAt(getPoint()).setBuildingLevel(BuildingLevel.Town);
	}
}
