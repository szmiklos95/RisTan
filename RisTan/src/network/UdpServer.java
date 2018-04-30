package network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
//w w  w .  j  ava  2 s .  c  o  m

import config.Config;

public class UdpServer {
	private DatagramSocket socket;
	private InterfaceAddress ServerInterface = null;
	private InetAddress BroadcastAddress = null;
	private InetAddress ServerAddress = null;
	
	private class TxThread extends Thread {
	    private byte[] buf = new byte[4];
		public void run(){
			// UDP üzenetek küldése a broadcast címre
			buf = ServerAddress.getAddress();
			while(true) {
				try {
					// 1 sec
					sleep(Config.Server.UdpPeriodicSendTime);
					
					DatagramPacket packet  = new DatagramPacket(buf, buf.length,BroadcastAddress,Config.Udp.ClientPort);
					socket.send(packet);
					System.out.printf("%x.%x.%x.%x\n",buf[0],buf[1],buf[2],buf[3] );
					
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public UdpServer() {
		GetServerAddress();
		BroadcastAddress = ServerInterface.getBroadcast();
		ServerAddress = ServerInterface.getAddress();
	}

	private void GetServerAddress() {

		Enumeration<NetworkInterface> en = null;
		try {
			en = NetworkInterface.getNetworkInterfaces();
		} catch (SocketException e) {
			e.printStackTrace();
		}
		while (en.hasMoreElements()) {
			NetworkInterface ni = en.nextElement();

			List<InterfaceAddress> list = ni.getInterfaceAddresses();
			Iterator<InterfaceAddress> it = list.iterator();

			while (it.hasNext()) {
				InterfaceAddress ia = it.next();
				// Find the broadcast IP address
				if (ia.getBroadcast() != null) {
					if (ia.getBroadcast().getHostAddress() != null) {
						ServerInterface = ia;
					}
				}
			}
		}
	}
	
	public void connect() {
		try {
			socket = new DatagramSocket(Config.Udp.ServerPort);
		} catch (SocketException e) {
			e.printStackTrace();
		}
		
		TxThread tx = new TxThread();
		tx.start();
	}
	
	public void disconnect() {
		socket.close();
	}
	
	public InetAddress getBroadcastAddress() {
		return BroadcastAddress;
	}
	
	public InetAddress getServerAddress() {
		return ServerAddress;
	}
}
