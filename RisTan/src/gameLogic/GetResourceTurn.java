package gameLogic;

import java.util.List;
import java.util.Map;

import config.Config;

/**
 * A turn when the player gets a predetermined set of resources.
 * @author Andras
 *
 */
public class GetResourceTurn extends Turn {
	private Map<Resource,Integer> resources;
	
	public GetResourceTurn(Map<Resource,Integer> resources) {
		super(false);
		this.resources=resources;
	}
	
	@Override
	String getGeneratorString() {
		StringBuilder builder=new StringBuilder();
		builder.append(GetResourceTurn.class.getCanonicalName());
		for(Map.Entry<Resource,Integer> e:resources.entrySet()) {
			builder.append(" ");
			builder.append(e.getKey().ordinal());
			builder.append(" ");
			builder.append(e.getValue().toString());
		}
		return builder.toString();
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
	
	@Override
	public String toString() {
		return Config.TurnNames.getResource;
	}
}
