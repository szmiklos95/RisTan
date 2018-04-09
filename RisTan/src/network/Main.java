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
		
		andris.Send(new Message("Szoftvertechnol�gia h�zi feladat"));
		rendszer.Send(new Message("�zenet a szervert�l a 0. j�t�kosnak"), 0); // rendszer -> andris
		rendszer.Send(new Message("P�lda mondat."), 2); // rendszer -> peti
		
		//rendszer.disconnect();
		
		
		
	}
}
