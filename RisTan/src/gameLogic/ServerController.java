package gameLogic;

import network.Message;
import network.SerialServer;

public class ServerController extends Controller {
	private SerialServer server;

	public ServerController(SerialServer server) {
		this.server=server;
	}
	
	//execute Actions from clients
	@Override
	public void executeAction(int playerID, Action action) {
		try {
			getGameState().executeAction(action);
			//the following line runs if and only if the action executed proprely on the server
			server.SendtoAll(new Message(Message.eMsgType.Action,action));
		}catch(GameLogicException e) {
			server.Send(new Message(e.getMessage()),playerID);
		}
	}
}
