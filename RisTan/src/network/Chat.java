package network;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

import network.Message.eMsgType;

public class Chat extends JFrame{
	private static final long serialVersionUID = 1L;
	private SerialClient client = null;
	private TextArea display = null;
	private String name;
	
	private class displayUpdateThread extends Thread implements Runnable{
		private String text = "";
		public void run(){
			while(true) {
				try {
					if(client.isRecText()) {
						String newtext = new String((String)client.getMsg().GetData());
						text = text.concat(newtext);
						display.setText(text);
						}
					//Sleep the thread for 100ms
					sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	Chat(String name){
		super("RisTan Beta - " + name);
		setSize(500, 500);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		GridBagLayout gbl = new GridBagLayout();
		this.setLayout(gbl);
		
		this.name = name;
		
		client = new SerialClient();
		//This is my IP address
		client.Connect("152.66.177.145");
		
		client.Send(new Message(eMsgType.Name,this.name));
		
		// Display messages
		GridBagConstraints GBC_display = new GridBagConstraints();
		GBC_display.gridx = 0;
		GBC_display.gridy = 0;
		GBC_display.gridwidth = 1;
		GBC_display.gridheight = 2;
		GBC_display.fill = GridBagConstraints.WEST;
		GBC_display.insets = new Insets(10,10,10,10);
		
		display = new TextArea();
		display.setEditable(false);
		display.setText("Welcome and good luck!");
		gbl.setConstraints(display, GBC_display);
		this.add(display);
		
		Thread updateThread = new Thread(new displayUpdateThread());
		updateThread.start();
		
		// input field
		GridBagConstraints GBC_input = new GridBagConstraints();
		GBC_input.gridx = 0;
		GBC_input.gridy = 10;
		GBC_input.fill = GridBagConstraints.HORIZONTAL;
		GBC_input.insets = new Insets(10,10,10,10);
		
		TextField input = new TextField();
		gbl.setConstraints(input, GBC_input);
		
		input.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				char id = e.getKeyChar();
				// Enter key pressed
				if ( id == '\n') {
					String msg = new String(input.getText());
					input.setText(" ");
					client.Send(new Message(eMsgType.Text,msg));
				}
				
			}
			@Override
			public void keyReleased(KeyEvent e) {}
			@Override
			public void keyPressed(KeyEvent e) {}
		});
		
		this.add(input);
		
		// send button
		GridBagConstraints GBC_send = new GridBagConstraints();
		GBC_send.gridx = 3;
		GBC_send.gridy = 10;
		GBC_send.fill = GridBagConstraints.NONE;
		GBC_send.insets = new Insets(10,10,10,10);
		
		setVisible(true);
		
	}

}
