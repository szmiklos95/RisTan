package gameLogic;

import java.util.ArrayList;
import java.util.List;

public class TurnOrder {
	private List<Turn> turns;
	private int activeNum;
	
	TurnOrder(List<Turn> turns){
		this.turns=new ArrayList<Turn>();
		this.turns.addAll(turns);
		activeNum=0;
	}
	
	Turn getActive() {
		return turns.get(activeNum);
	}
	
	boolean next() {
		activeNum++;
		if(activeNum==turns.size()) {
			activeNum=0;
			return true;
		}
		return false;
	}
}
