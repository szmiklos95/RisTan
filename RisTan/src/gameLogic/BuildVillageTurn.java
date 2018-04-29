package gameLogic;

import java.util.ArrayList;
import java.util.List;

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
	public List<Action> getPossibleTileActions(GameState gameState) {
		List<Action> ret=new ArrayList<Action>();
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
}
