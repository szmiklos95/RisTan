package network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import config.Config;

/* This client just receives from the server */
public class UdpClient {
	private DatagramSocket socket;
	private InetAddress TcpServeraddress = null;
	
	private class RxThread extends Thread{
		private byte[] buf = new byte[4];
		public void run(){
			DatagramPacket packet = new DatagramPacket(buf, buf.length);
			while(true) {
				try {
					// blokkol
					socket.receive(packet);
					TcpServeraddress = InetAddress.getByAddress(packet.getData());
					System.out.println(TcpServeraddress);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}



public void connect() {
	try {
		socket = new DatagramSocket(Config.Udp.ClientPort);
	} catch (SocketException e) {
		e.printStackTrace();
	}
	
	RxThread rx = new RxThread();
	rx.start();
	}

public void disconnect() {
	socket.close();
	}

public InetAddress getServerAddress() {
	return TcpServeraddress;
	}
}