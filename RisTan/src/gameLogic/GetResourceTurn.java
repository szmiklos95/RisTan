package gameLogic;

import java.util.List;
import java.util.Map;

public class GetResourceTurn extends Turn {
	private Map<Resource,Integer> resources;
	
	public GetResourceTurn(Map<Resource,Integer> resources) {
		super(false);
		this.resources=resources;
	}
	
	@Override
	void reset(GameState gameState) {
		List<Action> actions=getAutomaticActions();
		actions.clear();
		int activePlayerID=gameState.getActivePlayer().getID();
		for(Map.Entry<Resource,Integer> e:resources.entrySet()) {
			actions.add(new GetResourceAction(activePlayerID,e.getKey(),e.getValue()));
		}
	}
}
