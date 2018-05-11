package network;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import gameLogic.Action;
import gameLogic.AddPlayerAction;
import gameLogic.Player;
import gameLogic.ServerController;
import network.Message.eMsgType;

/**
 * TCP/IP Server for the communication.
 * It can handle more clients.
 * Contains controller class what is responsible for the action process.
 * @author Péter
 *
 */
public class SerialServer {
	/**
	 * This socket is use for define port and listen for client connect request
	 */
	private ServerSocket serverSocket = null;
	/**
	 * The number of client threads
	 */
	private int num_threads = 3;
	/**
	 * The number of already connected clients
	 */
	private int connectedClients = 0;
	/**
	 * The array what contains the threads
	 */
	private ArrayList<ReceiverThread> clientArray;
	/**
	 * This class try to execute the received actions
	 */
	private ServerController controller;
	
	/**
	 * Constructor
	 */
	public SerialServer() {
		controller=new ServerController(this);
	}
	
	/** 
	 * It is handling connecting clients and receiving messages
	 * */
	private class ReceiverThread implements Runnable {
		/**
		 * Socket for communicating with the corresponding client
		 */
		private Socket clientSocket = null;
		/**
		 * Input stream for receiving messages
		 */
		private ObjectInputStream in;
		/**
		 * Output stream for sending messages
		 */
		private ObjectOutputStream out;
		/**
		 * The Id of the player
		 * It is incremented when player connected 
		 */
		private int playerId = -1;
		/**
		 * The player name
		 */
		private String name;
		/**
		 * Run while the thread is exits.
		 * After a client is connected, waiting for receiving messages
		 */
		public void run() {
			try {
				clientSocket = serverSocket.accept(); // blocking the running
				playerId = connectedClients;
				connectedClients++;
			} catch (IOException e) {
				System.err.println("System: Accept failed.");
				disconnect();
				return;
			}
			
			try {
				out = new ObjectOutputStream(clientSocket.getOutputStream());
				in = new ObjectInputStream(clientSocket.getInputStream());
				out.flush();
				
				out.writeObject(new Message(playerId));
				
			} catch (IOException e) {
				System.err.println("System: Error while getting streams.");
				disconnect();
				return;
			}
			
			try {
				while (true) {
					Message msg = (Message) in.readObject();
					Object data = msg.GetData();
					switch(msg.GetType()) { 
					case Name:
						name = (String)data;
						//gameState playerlist update
						Player player=new Player(name,playerId);
						List<Player> oldPlayers=controller.getPlayers();
						for(Player p:oldPlayers) {//Add previous players to the new one
							Send(new Message(eMsgType.Action,new AddPlayerAction(p)),playerId);
						}
						//Add the new player for the previous ones
						controller.executeAction(playerId, new AddPlayerAction(player));
						
						//when every player connected, start the game
						if(connectedClients==num_threads) {
							Action action=controller.initGame();
							SendtoAll(new Message(eMsgType.Action,action));
						}
						break;
					case Text:
						// Send the chat messages for every client included the sender one
						SendtoAll(new Message(eMsgType.Text,name + ": " + (String)data + "\n"));
						break;
					case Action:
						//The controller try to execute the action
						controller.executeAction(playerId,(Action)data);
						break;
					default:
						break;
					}
					System.out.println("Player"+playerId+" --> System: " + msg.GetData());
				}
			} catch (Exception ex) {
				System.out.println(ex.getMessage());
				System.err.println("System: Player disconnected!");
				ex.printStackTrace(System.err);
			} finally {
				disconnect();
			}
		}
	}
	/**
	 * Bind the server to port and creating the threads
	 * @param num_players - Number of players
	 */
	public void Connect(int num_players) {
		//creates a thread for all the clients
		num_threads=num_players;		
		try {
			if(serverSocket != null)
				disconnect();
			serverSocket = new ServerSocket(455);

			clientArray= new ArrayList<ReceiverThread>();
			// Listener threads
			for(int i=0; i<num_threads; i++) {
				ReceiverThread rec = new ReceiverThread();
				clientArray.add(rec);
				new Thread(rec).start();
			}
		} catch (IOException e) {
			System.err.println("System: Connection error");
		}
	}
	/**
	 * Send message to the destination client
	 * @param msg  - the message
	 * @param dest - the ID of the destination client
	 */
	public void Send(Message msg, int dest) {
		if(getThread(dest) == null) {
			System.out.println("Ez a szál még nincs csatlakozva");
			return;
		}
		if (getThread(dest).clientSocket == null)
			return; 
		try {
			getThread(dest).out.writeObject(msg);
			getThread(dest).out.flush();	
		} catch (IOException e) {
			System.err.println("System: Send error.");
		}	
	}
	/**
	 * Send the message for all connected clients.
	 * @param msg
	 */
	public void SendtoAll(Message msg) {
		for(int i=0; i<connectedClients; ++i) {
			Send(msg,i);
		}
	}
	
	/**
	 * Disconnect the server.
	 */
	void disconnect() {
		try {
			for(int i = 0; i < num_threads; ++i) {
			if (clientArray.get(i).out != null)
				clientArray.get(i).out.close();
			if (clientArray.get(i).in != null)
				clientArray.get(i).in.close();
			if (clientArray.get(i).clientSocket != null)
				clientArray.get(i).clientSocket.close();
			if (serverSocket != null)
				serverSocket.close();
			}
		} catch (IOException ex) {
			Logger.getLogger(SerialServer.class.getName()).log(Level.SEVERE,
			null, ex);
		}
	}
	/**
	 * This method finds thread what's assigned to PlayerId
	 * @param id
	 * @return
	 */
	private ReceiverThread getThread (int id) {
		for(int i = 0; i < num_threads; ++i) {
			if(clientArray.get(i).playerId == id)
				return clientArray.get(i);
		}
		return null;
	}
	
	
}
