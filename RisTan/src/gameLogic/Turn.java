package gameLogic;

import java.util.ArrayList;
import java.util.List;

abstract class Turn {
	private int remainingTime;
	private List<Action> automaticActions;
	private List<Event> obligatoryEvents;
	private boolean tradeEnabled;
	
	Turn(boolean tradeEnabled){
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
	
	void checkObligatoryEvents(Action action) {
		for(int i=0;i<obligatoryEvents.size();++i) {
			Event event=obligatoryEvents.get(i);
			if(event.satisfies(action)) {
				obligatoryEvents.remove(event);
				return;
			}
		}
	}
	
	abstract List<Action> getPossibleTileActions(GameState gameState);
	
	boolean isTradeEnabled() {
		return tradeEnabled;
	}
}
