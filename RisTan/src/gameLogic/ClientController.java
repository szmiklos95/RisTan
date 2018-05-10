package gameLogic;

import java.util.List;

import network.Message;
import network.SerialClient;
import network.Message.eMsgType;

/**
 * A controller which handles the client side game state.
 * @author Andras
 *
 */
public class ClientController extends Controller {
	/**
	 * The network client for communication.
	 */
	private SerialClient client;
	/**
	 * The ID of the local player.
	 */
	private int localPlayerID;
	
	/**
	 * Constructor.
	 * @param client the network client.
	 */
	public ClientController(SerialClient client) {
		this.client=client;
		localPlayerID=-1;
	}
	
	/**
	 * Gets the local player ID.
	 * @return the local plyaer ID.
	 */
	public int getLocalPlayerID() {
		return localPlayerID;
	}
	
	/**
	 * Sets the local player ID.
	 * @param localPlayerID the new local player ID.
	 */
	public void setLocalPlayerID(int localPlayerID) {
		this.localPlayerID=localPlayerID;
	}
	
	public String getLocalPlayerName() {
		List<Player> players=getGameState().getPlayers();
		for(Player p:players) {
			if(p.getID()==localPlayerID) {
				return p.getName();
			}
		}
		return "";
	}
	
	/**
	 * Executes an action broadcasted by the server. This is the end of the action execution chain.
	 */
	@Override
	public void executeAction(int playerID,Action action) {
		try {
			getGameState().executeAction(action);
		} catch (GameLogicException e) {
			//this code should never execute
			e.printStackTrace();
		}
	}
	
	/**
	 * Sends an action to the server for checking and execution. This is the start of the action execution chain.
	 * @param action the action to execute.
	 */
	public void sendAction(Action action) {
		client.Send(new Message(eMsgType.Action,action));
	}
	
	/**
	 * Gets whether the local player is the active or not.
	 * @return true if and only if the local player is the active.
	 */
	public boolean isActivePlayer() {
		Player active=getGameState().getActivePlayer();
		if(active==null) {
			return false;
		}
		return active.getID()==localPlayerID;
	}
}
