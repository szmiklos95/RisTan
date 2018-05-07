package gameLogic;

import java.util.ArrayList;
import java.util.List;

import config.Config;

/**
 * A turn when the player builds a village for free.
 * @author Andras
 *
 */
public class BuildVillageTurn extends Turn {
	public BuildVillageTurn(){
		super(false);
	}
	
	@Override
	String getGeneratorString() {
		return BuildVillageTurn.class.getCanonicalName();
	}
	
	@Override
	void reset(GameState gameState) {
		List<Event> events=getObligatoryEvents();
		events.clear();
		events.add(new BuildVillageEvent());
	}
	
	@Override
	public List<TileAction> getPossibleTileActions(GameState gameState) {
		List<TileAction> ret=new ArrayList<TileAction>();
		if(getObligatoryEvents().size()>0) {
			int activePlayerID=gameState.getActivePlayer().getID();
			List<Point> points=gameState.getBoard().getPlayerEmptyTileCoordinates(activePlayerID);
			for(int i=0;i<points.size();++i) {
				Point point=points.get(i);
				ret.add(new BuildVillageFree(activePlayerID,point));
			}
		}
		return ret;
	}
	
	@Override
	boolean isTileActionPossible(GameState gameState,TileAction action) {
		return (getObligatoryEvents().size()>0)&&
				(action instanceof BuildVillageFree);
	}
	
	@Override
	public String toString() {
		return Config.TurnNames.buildVillage;
	}
}
