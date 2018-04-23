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

public class SerialServer {
	// class variables
	private ServerSocket serverSocket = null;
	private int num_threads = 3;
	private int connectedClients = 0;
	private ArrayList<ReceiverThread> clientArray;
	private ServerController controller;
	
	public SerialServer() {
		controller=new ServerController(this);
	}
	
	/* Thread is necessary for handling receiving messages 
	 * - accept() and readObject() are blocking methods 
	 * */
	private class ReceiverThread implements Runnable {
		private Socket clientSocket = null;
		private ObjectInputStream in;
		private ObjectOutputStream out;
		private int playerId = -1;
		private String name;
		
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
						//gameState játékoslista frissítések
						Player player=new Player(name,playerId);
						List<Player> oldPlayers=controller.getPlayers();
						for(Player p:oldPlayers) {//a most csatlakozóhoz az eddigiek hozzáadása
							Send(new Message(eMsgType.Action,new AddPlayerAction(p)),playerId);
						}
						//mindegyikhez a most csatlakozó hozzáadása
						controller.executeAction(playerId, new AddPlayerAction(player));
						break;
					case Text:
						SendtoAll(new Message(eMsgType.Text,name + ": " + (String)data + "\n"));
						break;
					case Action:
						System.out.println("C "+playerId+" "+msg.GetData());
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
			} finally {
				disconnect();
			}
		}
	}
	
	public void Connect() {
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
	
	public void SendtoAll(Message msg) {
		for(int i=0; i<connectedClients; ++i) {
			Send(msg,i);
		}
	}
	
	
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
	 * This method is find thread what's assigned to PlayerId
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
