package network;

/* This demo application is about sending and receiving
 * an object (TransferClass instance) what is serializable,
 * between a server (System) and a client (Player)*/
public class Main {

	public static void main(String[] args) {
		TransferClass packet1 = new TransferClass("...", 1, 1.1);
		TransferClass packet2 = new TransferClass("---", 200, 2.22);
		
		SerialServer system = new SerialServer();
		system.Connect("localhost");
		
		SerialClient player = new SerialClient();
		player.Connect("localhost");
		
		player.Send(packet1);
		
		system.Send(packet2);
		
		//player.disconnect();
		//system.disconnect();
		
		
		
	}
}
