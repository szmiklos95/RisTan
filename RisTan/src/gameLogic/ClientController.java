package gameLogic;

import java.util.List;

import network.Message;
import network.SerialClient;
import network.Message.eMsgType;

public class ClientController extends Controller {
	private SerialClient client;
	private int localPlayerID;
	
	public ClientController(SerialClient client) {
		this.client=client;
		localPlayerID=-1;
	}
	
	public int getLocalPlayerID() {
		return localPlayerID;
	}
	
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
	
	//execute broadcasted actions from server
	@Override
	public void executeAction(int playerID,Action action) {
		try {
			getGameState().executeAction(action);
			//TODO GUI újrarajzolás
		} catch (GameLogicException e) {
			//this code should never execute
			e.printStackTrace();
		}
	}
	
	//send an action to the server for check and execution (call this function to start the execution chain)
	public void sendAction(Action action) {
		client.Send(new Message(eMsgType.Action,action));
	}
	
	//TODO: GUI eseményekbõl Action-ök elõállítása (nem feltétlenül itt)
}
