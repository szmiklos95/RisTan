package gameLogic;

import java.util.Map;

/**
 * Describes an action of occupying an enemy town. This action is probabilistic. This action has some variants in time and resource cost depending on the building level of the target tile and the probability of the success.
 * @author Andras
 *
 */

public abstract class OccupyEnemyTownAction extends OccupyEnemyTileAction {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructor.
	 * @param playerID the ID of the action executing player.
	 * @param time the time cost of the action.
	 * @param point the coordinates of the target tile (in board coordinate system).
	 * @param cost the resource cost of the action.
	 * @param probability the probability of the success.
	 */
	public OccupyEnemyTownAction(int playerID,int time,Point point,Map<Resource,Integer> cost,double probability){
		super(playerID,time,point,cost,probability);
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
	 * If the target tile is not owned by an enemy player, throws a TileIsNotEnemyException. <br>
	 * If the target tile is not a town, throws a TileIsNotTownException.
	 */
	@Override
	void check(GameState gameState)throws GameLogicException{
		super.check(gameState);
		Tile tile=gameState.getBoard().getTileAt(getPoint());
		if(tile.getBuildingLevel()!=BuildingLevel.Town) {
			throw new TileIsNotTownException();
		}
	}
}
