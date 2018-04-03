package gameLogic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

//stores the order of the players in the turns
class PlayerOrder {
	private List<Player> players;
	private int activeNum;
	private String shuffleOrder;
	
	PlayerOrder(List<Player> players){
		this.players=new ArrayList<Player>();
		this.players.addAll(players);
		Collections.shuffle(this.players);
		generateShuffleOrder(players,this.players);
		activeNum=0;
	}
	
	PlayerOrder(List<Player> players, String shuffleOrder){
		this.players=new ArrayList<Player>();
		this.shuffleOrder=shuffleOrder;
		String[] so=shuffleOrder.split(" ");
		for(int i=0;i<players.size();++i) {
			this.players.add(players.get(Integer.parseInt(so[i])));
		}
		activeNum=0;
	}
	
	private void generateShuffleOrder(List<Player> in,List<Player> out) {
		StringBuilder builder=new StringBuilder();
		for(int i=0;i<out.size();++i) {
			Player op=out.get(i);
			for(int j=0;j<in.size();++j) {
				Player ip=in.get(j);
				if(op==ip) {
					builder.append(new Integer(j).toString()+" ");
					break;
				}
			}
		}
		shuffleOrder=builder.toString();
	}
	
	String getShuffleOrder() {
		return shuffleOrder;
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
