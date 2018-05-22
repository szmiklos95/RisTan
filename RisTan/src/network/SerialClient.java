package network;

import java.io.*;
import java.net.*;

import gameLogic.Action;
import gameLogic.ClientController;

public class SerialClient {
    /* Class variables */
	private Socket socket = null;
	// The data transfer is done through the Stream
	private ObjectInputStream in;
	private ObjectOutputStream out;
	private ClientController controller;
	
	private boolean isTextReceived = false;
	private Message msg = null;
	
	/* Thread is necessary for handling receiving messages 
	 * readObject() is a blocking method
	 * */
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
				}
			}
		}
	
	// Constructor
	public SerialClient(){
		controller=new ClientController(this);
	}
	
	// Methods
	public void Connect(String ip) {
		try {
			socket = new Socket(InetAddress.getByName(ip), 455);
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
	
	public void Send(Message msg) {
		try {
			out.writeObject(msg);
			out.flush();
			
		} catch (IOException e) {
			System.err.println("Player" + controller.getLocalPlayerID() + ": The msg wasn't sent.");
		} 
	}
	
	void disconnect() {
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
	
	public int GetId() {
		return this.controller.getLocalPlayerID();
	}
	
	public boolean isRecText() {
		return isTextReceived;
	}
	
	public Message getMsg() {
		isTextReceived = false;
		return msg;
	}
	
	public String getName() {
		return controller.getLocalPlayerName();
	}
	
	public ClientController getController() {
		return controller;
	}
}
