package graphics;

import javax.swing.JMenu;
import javax.swing.JMenuBar;

import config.Config;

/**
 * Class that is responsible for handling the system messages.
 * @author Miklós
 *
 */
class SystemMessage {
    
	static private String systemMessage = null;
    
    SystemMessage(){
    	setSystemMessage(Config.SystemMessage.defaultMsg);
    }

	public static String getSystemMessage() {
		return systemMessage;
	}

	public static void setSystemMessage(String systemMessage) {
		SystemMessage.systemMessage = systemMessage;
	}
	
    /**
     * Displays the system message stored in the "systemMessage" variable
     * Place: Menubar
     */
    static void write() {
		JMenuBar menubar = CardSync.frame.getJMenuBar();			
		
		//Get the last menu item
		JMenu textItem = menubar.getMenu(menubar.getMenuCount()-1);
		
		//Change text
		textItem.setText(systemMessage);
		
		CardSync.frame.setJMenuBar(menubar);
    }
}
