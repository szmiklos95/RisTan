package gameLogic;

import network.Message;
import network.SerialServer;

/**
 * A controller which handles the server side game state.
 * @author Andras
 *
 */
public class ServerController extends Controller {
	/**
	 * The network server for communication.
	 */
	private SerialServer server;

	/**
	 * Constructor.
	 * @param server the network server.
	 */
	public ServerController(SerialServer server) {
		this.server=server;
	}
	
	/**
	 * Tries to execute an action from a client. If the action is invalid, sends back an error message to the executing player. If the action is valid, after the execution sends it to all the clients for execution.
	 */
	@Override
	public void executeAction(int playerID, Action action) {
		try {
			getGameState().executeAction(action);
			//the following line runs if and only if the action executed properly on the server
			server.SendtoAll(new Message(Message.eMsgType.Action,action));
		}catch(GameLogicException e) {
			server.Send(new Message(e.getMessage()),playerID);
		}
	}
	
	/**
	 * Inits the game state. This is the start of the game.
	 * @return a StartGameAction to execute on the clients.
	 */
	public Action initGame() {
		try {
			getGameState().initGame();
		}catch(GameLogicException e) {
			e.printStackTrace();
		}
		return getGameState().getStartGameAction();
	}
}
