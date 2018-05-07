package gameLogic;

import java.util.ArrayList;
import java.util.List;

import config.Config;

/**
 * A normal game turn.
 * @author Andras
 *
 */
public class NormalTurn extends Turn {
	private final int time;
	
	public NormalTurn(int time) {
		super(true);
		this.time=time;
	}
	
	@Override
	String getGeneratorString() {
		return NormalTurn.class.getCanonicalName()+" "+time;
	}
	
	@Override
	void reset(GameState gameState) {
		setRemainingTime(time);
		List<Action> actions=getAutomaticActions();
		actions.clear();
		int activePlayerID=gameState.getActivePlayer().getID();
		actions.add(new HarvestResourcesAction(activePlayerID));
	}
	
	@Override
	public List<TileAction> getPossibleTileActions(GameState gameState){
		List<TileAction> ret=new ArrayList<TileAction>();
		Player player=gameState.getActivePlayer();
		Board board=gameState.getBoard();
		int time=getRemainingTime();
		List<Point> fnpoints=board.getFreeNeighbourTileCoordinates(player.getID());
		List<Point> enepoints=board.getEnemyNeighbourEmptyTileCoordinates(player.getID());
		List<Point> envpoints=board.getEnemyNeighbourVillageTileCoordinates(player.getID());
		List<Point> entpoints=board.getEnemyNeighbourTownTileCoordinates(player.getID());
		List<Point> pepoints=board.getPlayerEmptyTileCoordinates(player.getID());
		List<Point> pvpoints=board.getPlayerVillageCoordinates(player.getID());
		//OccupyFreeTile
		if((time>=Config.Action.OccupyFreeTile.time)&&(player.hasResource(Config.Action.OccupyFreeTile.cost))) {
			for(int i=0;i<fnpoints.size();++i) {
				Point point=fnpoints.get(i);
				ret.add(new OccupyFreeTile(player.getID(),point));
			}
		}
		//OccupyEnemyTile
		if((time>=Config.Action.OccupyEnemyTile.time)&&(player.hasResource(Config.Action.OccupyEnemyTile.cost))) {
			for(int i=0;i<enepoints.size();++i) {
				Point point=enepoints.get(i);
				ret.add(new OccupyEnemyTile(player.getID(),point));
			}
		}
		//OccupyEnemyTileL2
		if((time>=Config.Action.OccupyEnemyTileL2.time)&&(player.hasResource(Config.Action.OccupyEnemyTileL2.cost))) {
			for(int i=0;i<enepoints.size();++i) {
				Point point=enepoints.get(i);
				ret.add(new OccupyEnemyTileL2(player.getID(),point));
			}
		}
		//OccupyEnemyVillage
		if((time>=Config.Action.OccupyEnemyVillage.time)&&(player.hasResource(Config.Action.OccupyEnemyVillage.cost))) {
			for(int i=0;i<envpoints.size();++i) {
				Point point=envpoints.get(i);
				ret.add(new OccupyEnemyVillage(player.getID(),point));
			}
		}
		//OccupyEnemyVillageL2
		if((time>=Config.Action.OccupyEnemyVillageL2.time)&&(player.hasResource(Config.Action.OccupyEnemyVillageL2.cost))) {
			for(int i=0;i<envpoints.size();++i) {
				Point point=envpoints.get(i);
				ret.add(new OccupyEnemyVillageL2(player.getID(),point));
			}
		}
		//OccupyEnemyTown
		if((time>=Config.Action.OccupyEnemyTown.time)&&(player.hasResource(Config.Action.OccupyEnemyTown.cost))) {
			for(int i=0;i<entpoints.size();++i) {
				Point point=entpoints.get(i);
				ret.add(new OccupyEnemyTown(player.getID(),point));
			}
		}
		//BuildVillage
		if((time>=Config.Action.BuildVillage.time)&&(player.hasResource(Config.Action.BuildVillage.cost))) {
			for(int i=0;i<pepoints.size();++i) {
				Point point=pepoints.get(i);
				ret.add(new BuildVillage(player.getID(),point));
			}
		}
		//BuildTown
		if((time>=Config.Action.BuildTown.time)&&(player.hasResource(Config.Action.BuildTown.cost))) {
			for(int i=0;i<pvpoints.size();++i) {
				Point point=pvpoints.get(i);
				ret.add(new BuildTown(player.getID(),point));
			}
		}
		return ret;
	}
	
	@Override
	boolean isTileActionPossible(GameState gameState,TileAction action) {
		Player player=gameState.getActivePlayer();
		Board board=gameState.getBoard();
		List<Point> fnpoints=board.getFreeNeighbourTileCoordinates(player.getID());
		List<Point> enepoints=board.getEnemyNeighbourEmptyTileCoordinates(player.getID());
		List<Point> envpoints=board.getEnemyNeighbourVillageTileCoordinates(player.getID());
		List<Point> entpoints=board.getEnemyNeighbourTownTileCoordinates(player.getID());
		//OccupyFreeTile check
		if(action instanceof OccupyFreeTile) {
			for(int i=0;i<fnpoints.size();++i) {
				Point point=fnpoints.get(i);
				if(point.equals(action.getPoint())) {
					return true;
				}
			}
		}
		//OccupyEnemyTile, OccupyEnemyTileL2 check
		if(action instanceof OccupyEnemyEmptyTileAction) {
			for(int i=0;i<enepoints.size();++i) {
				Point point=enepoints.get(i);
				if(point.equals(action.getPoint())) {
					return true;
				}
			}
		}
		//OccupyEnemyVillage, OccupyEnemyVillageL2 check
		if(action instanceof OccupyEnemyVillageAction) {
			for(int i=0;i<envpoints.size();++i) {
				Point point=envpoints.get(i);
				if(point.equals(action.getPoint())) {
					return true;
				}
			}
		}
		//OccupyEnemyTown, OccupyEnemyTownL2 check
		if(action instanceof OccupyEnemyTownAction) {
			for(int i=0;i<entpoints.size();++i) {
				Point point=entpoints.get(i);
				if(point.equals(action.getPoint())) {
					return true;
				}
			}
		}
		//BuildVillage, BuildTown
		if((action instanceof BuildVillage)||(action instanceof BuildTown)) {
			return true;
		}
		//checked all possible tile actions - if not exited, the action is not possible
		return false;
	}
	
	@Override
	public String toString() {
		return Config.TurnNames.normal;
	}
}
