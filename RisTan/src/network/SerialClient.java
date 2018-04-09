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
	private int playerId = 0;
	
	/* Thread is necessary for handling receiving messages 
	 * readObject() is a blocking method
	 * */
	private class ReceiverThread implements Runnable{
		public void run(){
			try {
			while(true) {
					// Blocking
					Message rec = (Message) in.readObject();
					if(rec.GetType() == eMsgType.Identification) {
						playerId = (int) rec.GetData();
						System.out.println("Player id=" +(int)rec.GetData());
					}
					else if (rec.GetType() == eMsgType.String)
						System.out.println("System  --> Player"+ playerId +" " + rec.GetData() );
						
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
}
