package graphics;

import java.util.ArrayList;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.SwingConstants;

import config.Config;

/**
 * Class that is responsible for handling the system messages.
 * @author Miklós
 *
 */
class SystemMessage {
    
	static private String systemMessage = null;
	static private ArrayList<String> subMessages = null;
	
	// Variables for the dot animation
	static private String previousSysMsg = null;
	static private int dotCount = 0;
	static private boolean dotAnimationRanOnce = false;
    
    SystemMessage(){
    	setSystemMessage(Config.SystemMessages.defaultMsg);
    	subMessages = new ArrayList<String>();
    }

	static String getSystemMessage() {
		return systemMessage;
	}

	static void setSystemMessage(String systemMessage) {
		if(systemMessage.length() > Config.SystemMessages.maxSysMsgLength) SystemMessage.systemMessage = Config.SystemMessages.error_tooLong;
		else {
			SystemMessage.systemMessage = systemMessage;
		}
		
		//reset the animation
		dotAnimationRanOnce = false;
		
		// When setting a new system message clear all sub messages
		if(subMessages!=null) subMessages.clear();
	}
	
	static void addSubMessage(String subMessage) {
		if(subMessages==null) subMessages = new ArrayList<String>();
		subMessages.add(subMessage);
	}
	
    /**
     * Displays the system message stored in the "systemMessage" variable
     * Place: Menubar
     */
    static void write() {
		JMenuBar menubar = CardSync.frame.getJMenuBar();			
		
		//Get the last menu item
		JMenu sysMsgItem = menubar.getMenu(menubar.getMenuCount()-1);
		
		// Change text
		sysMsgItem.setText(systemMessage);
		// Remove all menu items
		sysMsgItem.removeAll();
		
		// Iterate through the sub messages string list and add items
		if(subMessages!=null) {
			for(String s:subMessages) {
				JMenuItem item = new JMenuItem(s);
				item.setHorizontalAlignment(SwingConstants.RIGHT);
				sysMsgItem.add(item);
			}
		}
		
		CardSync.frame.setJMenuBar(menubar);
    }
    
    /**
     * Draws little dots after the current systemMessage. If reached the max number of dots, the dots willl be removed.
     */
    static void dotAnimation() {
    	// If we are starting save the original message
    	if(dotCount==0) {
    		
    		if(!dotAnimationRanOnce) {
        		for(int i=0; i<Config.SystemMessages.dotAnimationMaxDots; ++i) {
        			systemMessage+=" ";
        		}
    		}

    		 previousSysMsg = systemMessage;
    		
    	}
    	
    	// Add dots
    	if(dotCount<Config.SystemMessages.dotAnimationMaxDots) {
    		int index = systemMessage.length()- Config.SystemMessages.dotAnimationMaxDots + dotCount;
    		systemMessage = systemMessage.substring(0,index)+'.'+systemMessage.substring(index+1);
    		dotCount++;
    	}
    	
    	// if max reached reset the original message and the dot counter
    	if(dotCount==Config.SystemMessages.dotAnimationMaxDots) {
    		systemMessage = previousSysMsg;
    		dotCount=0;
    	}
    	
    	dotAnimationRanOnce = true;
    }
    
    
}
