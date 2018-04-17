package gameLogic;

import java.util.Map;
import java.util.Random;

//a TileAction which has P<1 probability of success
public abstract class ProbabilisticTileAction extends TileAction {
	private static final long serialVersionUID = 1L;
	
	private boolean successful;
	
	public ProbabilisticTileAction(int playerID,int time,Point point,Map<Resource,Integer> cost,double probability){
		super(playerID,time,point,cost);
		Random rand=new Random();
		successful=rand.nextDouble()<probability;
	}
	
	boolean isSuccessful() {
		return successful;
	}
}
