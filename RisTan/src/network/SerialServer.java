package network;

import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SerialServer {
	// class variables
	private ServerSocket serverSocket = null;
	private Socket clientSocket = null;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	
	/* Thread is necessary for handling receiving messages 
	 * - accept() and readObject() are blocking methods 
	 * */
	private class ReceiverThread implements Runnable {
		public void run() {
			try {
				System.out.println("System: Waiting for Players");
				clientSocket = serverSocket.accept(); // blocking the running
				System.out.println("System: Player connected.");
			} catch (IOException e) {
				System.err.println("System: Accept failed.");
				disconnect();
				return;
			}
			
			try {
				out = new ObjectOutputStream(clientSocket.getOutputStream());
				in = new ObjectInputStream(clientSocket.getInputStream());
				out.flush();
			} catch (IOException e) {
				System.err.println("System: Error while getting streams.");
				disconnect();
				return;
			}
			
			try {
				while (true) {
					TransferClass rec = (TransferClass) in.readObject();
					System.out.println("System: arrived - s=" + rec.GetString() + "  i=" + rec.GetInt() + "  d=" + rec.GetDouble());
				}
			} catch (Exception ex) {
				System.out.println(ex.getMessage());
				System.err.println("System: Player disconnected!");
			} finally {
				disconnect();
			}
		}
	}
	
	public void Connect(String ip) {
		try {
			if(serverSocket != null)
				disconnect();
			serverSocket = new ServerSocket(455);
			//System.out.println(serverSocket.getLocalPort());
			// Listener thread has to be created here
			Thread rec = new Thread(new ReceiverThread());
			rec.start();
			
		} catch (IOException e) {
			System.err.println("System: Connection error");
		}
	}
	
	public void Send(TransferClass data) {
		if (out == null)
			return;
		try {
			System.out.println("System: sending - s=" + data.GetString() + "  i=" + data.GetInt() + "  d=" + data.GetDouble());
			out.writeObject(data);
			out.flush();
			
		} catch (IOException e) {
			System.err.println("System: Send error.");
		}	
	}
	
	
	void disconnect() {
		try {
			if (out != null)
				out.close();
			if (in != null)
				in.close();
			if (clientSocket != null)
				clientSocket.close();
			if (serverSocket != null)
				serverSocket.close();
		} catch (IOException ex) {
			Logger.getLogger(SerialServer.class.getName()).log(Level.SEVERE,
			null, ex);
		}
	}
	
}
