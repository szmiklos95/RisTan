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
	
	TurnOrder(String generator){
		turns=new ArrayList<Turn>();
		String[] turnGenerators=generator.split("#");
		for(int i=0;i<turnGenerators.length;++i) {
			turns.add(Turn.fromGeneratorString(turnGenerators[i]));
		}
		activeNum=0;
	}
	
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
