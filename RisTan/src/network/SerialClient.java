package network;

import java.io.*;
import java.net.*;

public class SerialClient {
    /* Class variables */
	private Socket socket = null;
	// The data transfer is done through the Stream
	private ObjectInputStream in;
	private ObjectOutputStream out;
	
	/* Thread is necessary for handling receiving messages 
	 * readObject() is a blocking method
	 * */
	private class ReceiverThread implements Runnable{
		public void run(){
			System.out.println("Player: Waiting for messages");
			try {
			while(true) {
					// Blocking
					TransferClass rec = (TransferClass) in.readObject();
					System.out.println("Player: arrived - s=" + rec.GetString() + "  i=" + rec.GetInt() + "  d=" + rec.GetDouble());
			}
				} catch (Exception ex) {
					System.out.println(ex.getMessage());
					System.err.println("Player: System disconnected!");
				}
			}
		}
	
	// Methods
	public void Connect(String ip) {
		try {
			socket = new Socket(ip, 455);
			System.out.println("Player: Connecting to System.");
			
			out = new ObjectOutputStream(socket.getOutputStream());
			in = new ObjectInputStream(socket.getInputStream());
			out.flush();
			
			Thread rec = new Thread(new ReceiverThread());
			rec.start();
			
		} catch (IOException e) {
			System.err.println("Player: Connection failed to Server");
		}
	}
	
	public void Send(TransferClass data) {
		try {
			System.out.println("Player: sending - s=" + data.GetString() + "  i=" + data.GetInt() + "  d=" + data.GetDouble());
			out.writeObject(data);
			out.flush();
			
		} catch (IOException e) {
			System.err.println("Player: The msg wasn't sent.");
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
