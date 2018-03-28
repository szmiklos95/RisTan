package gameLogic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

//stores the order of the players in the turns
class PlayerOrder {
	private List<Player> players;
	private int activeNum;
	
	PlayerOrder(List<Player> players){
		this.players=new ArrayList<Player>();
		this.players.addAll(players);
		Collections.shuffle(this.players);
		activeNum=0;
	}
	
	//gets the active player
	Player getActive() {
		return players.get(activeNum);
	}
	
	//switches to the next player, returns true if a new turn begins
	boolean next() {
		activeNum++;
		if(activeNum==players.size()) {
			activeNum=0;
			return true;
		}
		return false;
	}
}
