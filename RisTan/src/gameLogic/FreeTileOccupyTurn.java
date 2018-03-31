package gameLogic;

import java.util.ArrayList;
import java.util.List;

public class FreeTileOccupyTurn extends Turn {
	private int amount;
	
	public FreeTileOccupyTurn(int amount){
		super(false);
		this.amount=amount;
	}
	
	@Override
	void reset() {
		List<Event> events=getObligatoryEvents();
		events.clear();
		for(int i=0;i<amount;++i) {
			events.add(new OccupyFreeTileEvent());
		}
	}
	
	@Override
	List<Action> getPossibleTileActions(GameState gameState) {
		List<Action> ret=new ArrayList<Action>();
		if(getObligatoryEvents().size()>0) {
			int activePlayerID=gameState.getActivePlayer().getID();
			List<Point> points=gameState.getBoard().getFreeNeighbourTileCoordinates(activePlayerID);
			for(int i=0;i<points.size();++i) {
				Point point=points.get(i);
				ret.add(new OccupyFreeTileFree(activePlayerID,point));
			}
		}
		return ret;
	}
	
	@Override
	boolean isTileActionPossible(GameState gameState,TileAction action) {
		if(getObligatoryEvents().size()<=0) {
			return false;
		}
		if(!(action instanceof OccupyFreeTileFree)) {
			return false;
		}
		int activePlayerID=gameState.getActivePlayer().getID();
		List<Point> points=gameState.getBoard().getFreeNeighbourTileCoordinates(activePlayerID);
		for(int i=0;i<points.size();++i) {
			Point point=points.get(i);
			if(point.equals(action.getPoint())) {
				return true;
			}
		}
		return false;
	}
}
