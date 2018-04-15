package network;

import java.io.*;
import java.net.*;

import network.Message.eMsgType;

public class SerialClient {
    /* Class variables */
	private Socket socket = null;
	// The data transfer is done through the Stream
	private ObjectInputStream in;
	private ObjectOutputStream out;
	static int playersNum = 0;
	private int playerId;
	private String name = null;
	
	private boolean isReceived = false;
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
					isReceived = true;
			
					if (msg.GetType() == eMsgType.String) {
						System.out.println("System  --> Player"+ playerId +" " + msg.GetData() );
					}
	
			}
				} catch (Exception ex) {
					System.out.println(ex.getMessage());
					System.err.println("Player" + playerId + ": System disconnected!");
				}
			}
		}
	
	// Constructor
	SerialClient(){
		playerId = playersNum;
		playersNum++;
	}
	
	SerialClient(String name){
		playerId = playersNum;
		playersNum++;
		System.err.println(playersNum);
		this.name = name;
	}
	
	// Methods
	public void Connect(String ip) {
		try {
			socket = new Socket(ip, 455);
			System.out.println("Player" + playerId + ": Connecting to System.");
			
			out = new ObjectOutputStream(socket.getOutputStream());
			in = new ObjectInputStream(socket.getInputStream());
			out.flush();
			
			Thread rec = new Thread(new ReceiverThread());
			rec.start();
			
		} catch (IOException e) {
			System.err.println("Player" + playerId + ": Connection failed to Server");
		}
	}
	
	public void Send(Message msg) {
		try {
			out.writeObject(msg);
			out.flush();
			
		} catch (IOException e) {
			System.err.println("Player" + playerId + ": The msg wasn't sent.");
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
		return this.playerId;
	}
	
	public boolean isRecText() {
		if(isReceived == true && msg.GetType() == eMsgType.String) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public Message getMsg() {
		isReceived = false;
		return msg;
	}
	
	public String getName() {
		return name;
	}
}
