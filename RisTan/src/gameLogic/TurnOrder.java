package gameLogic;

import java.util.ArrayList;
import java.util.List;

/**
 * Stores the order of the turns.
 * @author Andras
 *
 */
public class TurnOrder {
	/**
	 * The list of the turns.
	 */
	private List<Turn> turns;
	/**
	 * The index of the current turn.
	 */
	private int activeNum;
	
	/**
	 * Constructor.
	 * @param turns the list of turns to be included.
	 */
	TurnOrder(List<Turn> turns){
		this.turns=new ArrayList<Turn>();
		this.turns.addAll(turns);
		activeNum=0;
	}
	
	/**
	 * Custom deserializer constructor.
	 * @param generator the generator string.
	 */
	TurnOrder(String generator){
		turns=new ArrayList<Turn>();
		String[] turnGenerators=generator.split("#");
		for(int i=0;i<turnGenerators.length;++i) {
			turns.add(Turn.fromGeneratorString(turnGenerators[i]));
		}
		activeNum=0;
	}
	
	/**
	 * Custom serializer.
	 * @return the generator string.
	 */
	String getGeneratorString() {
		StringBuilder builder=new StringBuilder();
		if(turns.size()>=1) {
			builder.append(turns.get(0).getGeneratorString());
			if(turns.size()>=2) {
				for(int i=1;i<turns.size();++i) {
					builder.append("#"+turns.get(i).getGeneratorString());
				}
			}
		}
		return builder.toString();
	}
	
	/**
	 * Gets the active turn.
	 * @return the active turn.
	 */
	Turn getActive() {
		return turns.get(activeNum);
	}
	
	/**
	 * Gets whether it is the first turn or not.
	 * @return true in the first turn.
	 */
	boolean isFirstTurn() {
		return activeNum==0;
	}
	
	/**
	 * Switches to the next turn.
	 * @return true if the game has ended.
	 */
	boolean next() {
		activeNum++;
		if(activeNum==turns.size()) {
			activeNum=0;
			return true;
		}
		return false;
	}
	
	/**
	 * @author Miklós
	 * @return the active turn number
	 */
	public int getActiveNum() {
		return activeNum;
	}
	
	/**
	 * Returns how many turns are left from the game.
	 * @return remaining turn
	 */
	public int getRemainingTurn() {
		return turns.size()-activeNum-1;
	}
}
