package network;

import java.io.*;
import java.net.*;

import config.Config;
import gameLogic.Action;
import gameLogic.ClientController;
/**
 * TCP/IP client for the communication.
 * @author Péter
 *
 */
public class SerialClient {
    /**
     * Client socket connecting to server and transfer data
     */
	private Socket socket = null;
	/**
	 * Input stream for receiving messages
	 */
	private ObjectInputStream in;
	/**
	 * Output strean for sending messages
	 */
	private ObjectOutputStream out;
	/**
	 * Execute the action
	 */
	private ClientController controller;
	/**
	 * Indicates that is text received in chat
	 */
	private boolean isTextReceived = false;
	/**
	 * Message 
	 */
	private Message msg = null;
	
	/**
	 * This handles the receiving messages
	 * @author Péter
	 *
	 */
	private class ReceiverThread implements Runnable{
		public void run(){
			try {
			while(true) {
					// Blocking
					msg = (Message) in.readObject();
					switch(msg.GetType()) {
					case Identification:
						controller.setLocalPlayerID((int)msg.GetData());
						break;
					case Text:
						isTextReceived = true;
						break;
					case Action:
						controller.executeAction(controller.getLocalPlayerID(),(Action)msg.GetData());
						break;
					default:
						break;
					}

			}
				} catch (Exception ex) {
					System.out.println(ex.getMessage());
					System.err.println("Player" + controller.getLocalPlayerID() + ": System disconnected!");
					ex.printStackTrace(System.err);
				}
			}
		}
	
	/**
	 * Constructor
	 */
	public SerialClient(){
		controller=new ClientController(this);
	}
	
	/**
	 * Connect client for a port number and ip address
	 * and create the thread for receiving messages
	 * @param ip
	 */
	public void Connect(String ip) {
		try {
			socket = new Socket(InetAddress.getByName(ip), Config.Network.port);
			System.out.println("Player" + controller.getLocalPlayerID() + ": Connecting to System.");
			
			out = new ObjectOutputStream(socket.getOutputStream());
			in = new ObjectInputStream(socket.getInputStream());
			out.flush();
			
			Thread rec = new Thread(new ReceiverThread());
			rec.start();
		} 
		
		catch (UnknownHostException e) {
			System.err.println("Don't know about host");
		} catch (IOException e) {
			System.err.println("Player" + controller.getLocalPlayerID() + ": Connection failed to Server");
		}
	}
	/**
	 * Send messages to the server
	 * @param msg
	 */
	public void Send(Message msg) {
		try {
			out.writeObject(msg);
			out.flush();
			
		} catch (IOException e) {
			System.err.println("Player" + controller.getLocalPlayerID() + ": The msg wasn't sent.");
		} 
	}
	/**
	 * Disconnect the client
	 */
	public void disconnect() {
		try {
			if (out != null)
				out.close();
			if (in != null)
				in.close();
			if (socket != null)
				socket.close();
		} catch (IOException ex) {
			System.err.println("Error while closing conn.");
		}
	
	}
	/**
	 * Get player Id
	 * @return
	 */
	public int GetId() {
		return this.controller.getLocalPlayerID();
	}
	/**
	 * 
	 * @return - the flag what indicates whether received text message
	 */
	public boolean isRecText() {
		return isTextReceived;
	}
	/**
	 * Get message and reset the TextReceived flag
	 * @return
	 */
	public Message getMsg() {
		isTextReceived = false;
		return msg;
	}
	/**
	 * Get player name
	 * @return
	 */
	public String getName() {
		return controller.getLocalPlayerName();
	}
	/**
	 * Get controller object
	 * @return
	 */
	public ClientController getController() {
		return controller;
	}
}
