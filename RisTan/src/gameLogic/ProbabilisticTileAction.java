package gameLogic;

import java.util.Map;
import java.util.Random;

/**
 * Describes a probabilistic TileAction. These actions usually require some time and some resources to perform. <br>
 * These actions have P<1 probability of success. The successfulness is interpretable only if the action is valid. If the action is unsuccessful, the resource and time costs are lost at the execution.
 * @author Andras
 *
 */
public abstract class ProbabilisticTileAction extends TileAction {
	private static final long serialVersionUID = 1L;
	/**
	 * Shows whether the action is successful. This is computed 
	 */
	private final boolean successful;
	
	/**
	 * Constructor.
	 * @param playerID the ID of the action executing player.
	 * @param time the time cost of the action.
	 * @param point the coordinates of the target tile (in board coordinate system).
	 * @param cost the resource cost of the action.
	 * @param probability the probability of the success.
	 */
	public ProbabilisticTileAction(int playerID,int time,Point point,Map<Resource,Integer> cost,double probability){
		super(playerID,time,point,cost);
		Random rand=new Random();
		successful=rand.nextDouble()<probability;
	}
	
	/**
	 * Gets whether the action is successful.
	 * @return the successfulness of the action.
	 */
	boolean isSuccessful() {
		return successful;
	}
}
