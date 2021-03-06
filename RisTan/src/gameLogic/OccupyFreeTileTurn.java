package gameLogic;

import java.util.ArrayList;
import java.util.List;

import config.Config;

/**
 * A turn when the player occupies a determined number of tile for free.
 * @author Andras
 *
 */
public class OccupyFreeTileTurn extends Turn {
	private int amount;
	
	public OccupyFreeTileTurn(int amount){
		super(false);
		this.amount=amount;
	}
	
	@Override
	String getGeneratorString() {
		return OccupyFreeTileTurn.class.getCanonicalName()+" "+amount;
	}
	
	@Override
	void reset(GameState gameState) {
		List<Event> events=getObligatoryEvents();
		events.clear();
		for(int i=0;i<amount;++i) {
			events.add(new OccupyFreeTileEvent());
		}
	}
	
	@Override
	public List<TileAction> getPossibleTileActions(GameState gameState) {
		List<TileAction> ret=new ArrayList<TileAction>();
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
	
	@Override
	public String toString() {
		return Config.TurnNames.occupyFreeTile;
	}
}
