package gameLogic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import config.Config;

public abstract class Turn {
	static Turn fromGeneratorString(String generator) {
		String[] parts=generator.split(" ");
		String type=parts[0];
		if(type.equals(BuildVillageTurn.class.getCanonicalName())) {
			return new BuildVillageTurn();
		}else if(type.equals(GetResourceTurn.class.getCanonicalName())) {
			Map<Resource,Integer> resources=new HashMap<Resource,Integer>();
			for(int i=1;i<parts.length;i+=2) {
				Resource resource=Resource.values()[Integer.parseInt(parts[i])];
				Integer amount=Integer.parseInt(parts[i+1]);
				resources.put(resource,amount);
			}
			return new GetResourceTurn(resources);
		}else if(type.equals(NormalTurn.class.getCanonicalName())) {
			return new NormalTurn(Integer.parseInt(parts[1]));
		}else if(type.equals(OccupyFreeTileTurn.class.getCanonicalName())) {
			return new OccupyFreeTileTurn(Integer.parseInt(parts[1]));
		}else if(type.equals(StartTileChoiceTurn.class.getCanonicalName())) {
			return new StartTileChoiceTurn();
		}
		return null;
	}
	
	private int remainingTime;
	private List<Action> automaticActions;
	private List<Event> obligatoryEvents;
	private boolean tradeEnabled;
	
	public Turn(boolean tradeEnabled){
		remainingTime=0;
		automaticActions=new ArrayList<Action>();
		obligatoryEvents=new ArrayList<Event>();
		this.tradeEnabled=tradeEnabled;
	}
	
	abstract String getGeneratorString();
	
	abstract void reset(GameState gameState);
	
	public int getRemainingTime() {
		return remainingTime;
	}
	
	void setRemainingTime(int time) {
		remainingTime=time;
	}
	
	void takeTime(int time)throws GameLogicException{
		if(remainingTime<time) {
			throw new NotEnoughTimeException();
		}
		remainingTime-=time;
	}
	
	List<Action> getAutomaticActions(){
		return automaticActions;
	}
	
	List<Event> getObligatoryEvents(){
		return obligatoryEvents;
	}
	
	void checkObligatoryEvents(Action action) {
		for(int i=0;i<obligatoryEvents.size();++i) {
			Event event=obligatoryEvents.get(i);
			if(event.satisfies(action)) {
				obligatoryEvents.remove(event);
				return;
			}
		}
	}
	
	List<TileAction> getPossibleTileActions(GameState gameState){
		return new ArrayList<TileAction>();
	}
	
	boolean isTradeEnabled() {
		return tradeEnabled;
	}
	
	boolean canDoAnything(GameState gameState) {
		if(tradeEnabled) {
			if(remainingTime>=Config.Action.TradeWithGameAction.time) {
				return true;
			}
			if(remainingTime>=Config.Action.OfferTradeAction.time) {
				return true;
			}
			if(remainingTime>=Config.Action.AcceptTradeAction.time) {
				return true;
			}
		}
		if(getPossibleTileActions(gameState).size()>0) {
			return true;
		}
		return false;
	}
	
	//checks occur here of action type or which require more tiles than the affected in the tile action (e.g. is the affected tile neighbour of an own)
	boolean isTileActionPossible(GameState gameState,TileAction action) {
		return false;
	}
}
