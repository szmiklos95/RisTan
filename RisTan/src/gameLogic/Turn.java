package gameLogic;

import java.util.ArrayList;
import java.util.List;

public abstract class Turn {
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
	
	abstract void reset(GameState gameState);
	
	int getRemainingTime() {
		return remainingTime;
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
	
	List<Action> getPossibleTileActions(GameState gameState){
		return new ArrayList<Action>();
	}
	
	boolean isTradeEnabled() {
		return tradeEnabled;
	}
	
	//checks occur here of action type or which require more tiles than the affected in the tile action (e.g. is the affected tile neighbour of an own)
	boolean isTileActionPossible(GameState gameState,TileAction action) {
		return false;
	}
}
