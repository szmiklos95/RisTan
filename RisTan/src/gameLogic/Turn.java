package gameLogic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import config.Config;

/**
 * A turn in the game.
 * @author Andras
 *
 */
public abstract class Turn {
	/**
	 * Custom deserializer. Creates a turn from the generator string.
	 * @param generator the generator string.
	 * @return the turn.
	 */
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
	
	/**
	 * The remaining time in a player's turn.
	 */
	private int remainingTime;
	/**
	 * The list of the actions which are to be executed at the start of the player's turn.
	 */
	private List<Action> automaticActions;
	/**
	 * The list of events which needs to happen in the player's turn.
	 */
	private List<Event> obligatoryEvents;
	/**
	 * Whether the trade is enabled or not.
	 */
	private boolean tradeEnabled;
	
	/**
	 * Constructor.
	 * @param tradeEnabled whether the trade is enabled in this turn or not.
	 */
	public Turn(boolean tradeEnabled){
		remainingTime=0;
		automaticActions=new ArrayList<Action>();
		obligatoryEvents=new ArrayList<Event>();
		this.tradeEnabled=tradeEnabled;
	}
	
	/**
	 * Custom deserializer.
	 * @return the generator string.
	 */
	abstract String getGeneratorString();
	
	/**
	 * Resets the turn. Needs to be called at the start of the active player's turn. Resets the remaining time, the automatic actions and the obligatory events.
	 * @param gameState the current game state.
	 */
	abstract void reset(GameState gameState);
	
	/**
	 * Gets the remaining time.
	 * @return the remaining time.
	 */
	public int getRemainingTime() {
		return remainingTime;
	}
	
	/**
	 * Sets the remaining time.
	 * @param time the new remaining time.
	 */
	void setRemainingTime(int time) {
		remainingTime=time;
	}
	
	/**
	 * Tries to take an amount of time from the active player. If there is not enough time, throws a NotEnoughTimeException.
	 * @param time the time to take.
	 * @throws GameLogicException if there is not enough time, throws a NotEnoughTimeException.
	 */
	void takeTime(int time)throws GameLogicException{
		if(remainingTime<time) {
			throw new NotEnoughTimeException();
		}
		remainingTime-=time;
	}
	
	/**
	 * Gets the list of automatic actions.
	 * @return the list of automatic actions.
	 */
	List<Action> getAutomaticActions(){
		return automaticActions;
	}
	
	/**
	 * Gets the list of obligatory actions.
	 * @return the list of obligatory actions.
	 */
	List<Event> getObligatoryEvents(){
		return obligatoryEvents;
	}
	
	/**
	 * Checks an action for matching any of the obligatory events. If a match is found, the event is removed. An action can remove maximum one event.
	 * @param action the action to be checked.
	 */
	void checkObligatoryEvents(Action action) {
		for(int i=0;i<obligatoryEvents.size();++i) {
			Event event=obligatoryEvents.get(i);
			if(event.satisfies(action)) {
				obligatoryEvents.remove(event);
				return;
			}
		}
	}
	
	/**
	 * Gets the list of the possible tile actions is a game state.
	 * @param gameState the current game state.
	 * @return the list of possible tile actions.
	 */
	List<TileAction> getPossibleTileActions(GameState gameState){
		return new ArrayList<TileAction>();
	}
	
	/**
	 * Gets whether the trade is enabled or not.
	 * @return true if and only if the trade is enabled.
	 */
	boolean isTradeEnabled() {
		return tradeEnabled;
	}
	
	/**
	 * Checks whether the active player can execute any action in a given game state.
	 * @param gameState the given game state.
	 * @return true if and only if the active player can execute an action.
	 */
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
	
	/**
	 * TileAction checks which require more tile knowledge than the target tile (e.g. is the target tile neighbour of an own). Also checks the type of the tile actions - allowed types vary depending on the turn type.
	 * @param gameState the current game state.
	 * @param action the tile action to check.
	 * @return true if and if only the tile action can be executed..
	 */
	boolean isTileActionPossible(GameState gameState,TileAction action) {
		return false;
	}
	
	@Override
	public String toString() {
		return Config.TurnNames.defaultName;
	}
}
