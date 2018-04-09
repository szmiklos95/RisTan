package network;

import network.Message.*;

/* This demo application is about sending and receiving
 * an object (TransferClass instance) what is serializable,
 * between a server (System) and a client (Player)*/
public class Main {

	public static void main(String[] args) {
		
		SerialServer rendszer = new SerialServer();
		rendszer.Connect("localhost");
		
		SerialClient andris = new SerialClient();
		andris.Connect("localhost");
		
		SerialClient miki = new SerialClient();
		miki.Connect("localhost");
		
		SerialClient peti = new SerialClient();
		peti.Connect("localhost");
		
		// Assign the Client and Server's clientThread
		andris.Send(new Message(eMsgType.Identification, 0)); // player0
		miki.Send(new Message(eMsgType.Identification, 1));   // player1
		peti.Send(new Message(eMsgType.Identification, 2));   // player2
		
		andris.Send(new Message("Szoftvertechnológia házi feladat"));
		rendszer.Send(new Message("üzenet a szervertõl a 0. játékosnak"), 0); // rendszer -> andris
		rendszer.Send(new Message("Példa mondat."), 2); // rendszer -> peti
		
		//rendszer.disconnect();
		
		
		
	}
}
