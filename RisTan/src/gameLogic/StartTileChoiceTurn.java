package gameLogic;

import java.util.ArrayList;
import java.util.List;

public class StartTileChoiceTurn extends Turn {
	public StartTileChoiceTurn(){
		super(false);
	}
	
	@Override
	String getGeneratorString() {
		return StartTileChoiceTurn.class.getCanonicalName();
	}
	
	@Override
	void reset(GameState gameState) {
		List<Event> events=getObligatoryEvents();
		events.clear();
		events.add(new OccupyFreeTileEvent());
	}

	@Override
	public List<TileAction> getPossibleTileActions(GameState gameState) {
		List<TileAction> ret=new ArrayList<TileAction>();
		if(getObligatoryEvents().size()>0) {
			List<Point> points=gameState.getBoard().getFreeTileCoordinates();
			int activePlayerID=gameState.getActivePlayer().getID();
			for(int i=0;i<points.size();++i) {
				Point point=points.get(i);
				ret.add(new OccupyFreeTileFree(activePlayerID,point));
			}
		}
		return ret;
	}
	
	@Override
	boolean isTileActionPossible(GameState gameState,TileAction action) {
		return (getObligatoryEvents().size()>0)&&
				(action instanceof OccupyFreeTileFree);
	}
}
