package gameLogic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Stores the order of the players in the turns.
 * @author Andras
 *
 */
class PlayerOrder {
	/**
	 * The list of the players.
	 */
	private List<Player> players;
	/**
	 * The index of the active player in the list.
	 */
	private int activeNum;
	/**
	 * The permutation string of the shuffle.
	 */
	private String shuffleOrder;
	
	/**
	 * Constructor. Creates a randomly ordered list from the input player list.
	 * @param players the list of the players in the game.
	 */
	PlayerOrder(List<Player> players){
		this.players=new ArrayList<Player>();
		this.players.addAll(players);
		Collections.shuffle(this.players);
		generateShuffleOrder(players,this.players);
		activeNum=0;
	}
	
	/**
	 * Constructor. Orders the input player list based on the permutation string of the shuffle.
	 * @param players the input player list.
	 * @param shuffleOrder the permutation string of the shuffle.
	 */
	PlayerOrder(List<Player> players, String shuffleOrder){
		this.players=new ArrayList<Player>();
		this.shuffleOrder=shuffleOrder;
		String[] so=shuffleOrder.split(" ");
		for(int i=0;i<players.size();++i) {
			this.players.add(players.get(Integer.parseInt(so[i])));
		}
		activeNum=0;
	}
	
	/**
	 * Creates the permutation string from the input and output player lists.
	 * @param in the input player list.
	 * @param out the shuffled list of the players.
	 */
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
	
	/**
	 * Gets the shuffle order.
	 * @return the shuffle order.
	 */
	String getShuffleOrder() {
		return shuffleOrder;
	}
	
	/**
	 * Gets the active player.
	 * @return the active player.
	 */
	Player getActive() {
		return players.get(activeNum);
	}
	
	/**
	 * Switches to the next player circularly.
	 * @return true when the first player in the shuffled list becomes active - if a new turn begins.
	 */
	boolean next() {
		activeNum++;
		if(activeNum==players.size()) {
			activeNum=0;
			return true;
		}
		return false;
	}
}
