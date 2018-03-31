package gameLogic;

import java.util.ArrayList;
import java.util.List;

public class StartTileChoiceTurn extends Turn {
	public StartTileChoiceTurn(){
		super(false);
	}
	
	@Override
	void reset() {
		List<Event> events=getObligatoryEvents();
		events.clear();
		events.add(new OccupyFreeTileEvent());
	}

	@Override
	List<Action> getPossibleTileActions(GameState gameState) {
		List<Action> ret=new ArrayList<Action>();
		if(getObligatoryEvents().size()>0) {
			List<Point> freeTileCoordinates=gameState.getBoard().getFreeTileCoordinates();
			int activePlayerID=gameState.getActivePlayer().getID();
			for(int i=0;i<freeTileCoordinates.size();++i) {
				Point point=freeTileCoordinates.get(i);
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
