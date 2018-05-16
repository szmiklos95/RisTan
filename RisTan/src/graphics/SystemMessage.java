package graphics;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import config.Config;

/**
 * Class that is responsible for handling the system messages.
 * 
 * @author Miklós
 *
 */
class SystemMessage {

	/**
	 * The system message to display.
	 */
	static private String systemMessage = null;

	/**
	 * The sub message(s) to appear upon clicking on the system message
	 */
	static private ArrayList<String> subMessages = null;

	// Variables for the dot animation
	/**
	 * The previous system message.
	 */
	static private String previousSysMsg = null;

	/**
	 * The number of dots displayed.
	 */
	static private int dotCount = 0;

	/**
	 * True if we already did a dot animation.
	 */
	static private boolean dotAnimationRanOnce = false;

	// Variables for timed, blocking setter function
	/**
	 * The time elapsed in seconds.
	 */
	static private int elapsedSeconds = 0;

	/**
	 * The time we want to reach in seconds.
	 */
	static private int timeGoalSeconds = 0;

	/**
	 * True if the current system message can't be changed before the timeout.
	 */
	static private boolean setBlocked = false;

	/**
	 * Constructor. Sets a default system message.
	 */
	SystemMessage() {
		setSystemMessage(Config.SystemMessages.defaultMsg);
		subMessages = new ArrayList<String>();
	}

	/**
	 * 
	 * @return the current system message
	 */
	static String getSystemMessage() {
		return systemMessage;
	}

	/**
	 * Sets the system message. Has protection against too long messages.
	 * 
	 * @param systemMessage
	 *            the system message to display
	 */
	static void setSystemMessage(String systemMessage) {

		if (setBlocked)
			return;

		if (systemMessage.length() > Config.SystemMessages.maxSysMsgLength)
			SystemMessage.systemMessage = Config.SystemMessages.error_tooLong;
		else {
			SystemMessage.systemMessage = systemMessage;
		}

		// reset the animation
		dotAnimationRanOnce = false;

		// When setting a new system message clear all sub messages
		if (subMessages != null)
			subMessages.clear();
	}

	/**
	 * Sets a system message for the given time. This function blocks all setter
	 * functions until the given time elapsed.
	 * 
	 * @param systemMessage
	 *            the system message to display
	 * @param s
	 *            the time until this message will be guaranteed to stay on screen
	 */
	static void setSystemMessage(String systemMessage, int seconds) {

		if (setBlocked)
			return;

		setSystemMessage(systemMessage);
		timeGoalSeconds = seconds;

		setBlocked = true;

		int delay = 1000; // milliseconds
		final Timer timer = new Timer(delay, null);
		timer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				elapsedSeconds++;
				if (elapsedSeconds >= timeGoalSeconds) {
					timer.stop();
					elapsedSeconds = 0;
					setBlocked = false;
				}
			}
		});

		timer.start();

	}

	/**
	 * Appends error word for the given string and keeps it on the menu bar for the
	 * default time.
	 * 
	 * @param systemMessage
	 *            the system message without the error string
	 */
	static void setErrorMessage(String systemMessage) {
		setSystemMessage(Config.SystemMessages.error + systemMessage, Config.SystemMessages.defaultErrorTimeSeconds);
	}

	/**
	 * Adds a sub message to the current system message.
	 * 
	 * @param subMessage
	 *            the sub message to append to the other sub messages
	 */
	static void addSubMessage(String subMessage) {
		if (subMessages == null)
			subMessages = new ArrayList<String>();
		subMessages.add(subMessage);
	}

	/**
	 * Displays the system message stored in the "systemMessage" variable Place:
	 * Menubar
	 */
	static void write() {
		JMenuBar menubar = CardSync.frame.getJMenuBar();

		// Get the last menu item
		JMenu sysMsgItem = menubar.getMenu(menubar.getMenuCount() - 1);

		// Remove all menu items
		sysMsgItem.removeAll();

		// Iterate through the sub messages string list and add items
		if (subMessages != null) {

			for (String s : subMessages) {
				JMenuItem item = new JMenuItem(s);
				item.setHorizontalAlignment(SwingConstants.RIGHT);
				sysMsgItem.add(item);
			}
		}

		// Change text
		sysMsgItem.setText(systemMessage);

		CardSync.frame.setJMenuBar(menubar);
	}

	/**
	 * Draws little dots after the current systemMessage. If reached the max number
	 * of dots, the dots willl be removed and the animation starts over.
	 */
	static void dotAnimation() {
		// If we are starting save the original message
		if (dotCount == 0) {

			if (!dotAnimationRanOnce) {
				for (int i = 0; i < Config.SystemMessages.dotAnimationMaxDots; ++i) {
					systemMessage += " ";
				}
			}

			previousSysMsg = systemMessage;

		}

		// Add dots
		if (dotCount < Config.SystemMessages.dotAnimationMaxDots) {
			int index = systemMessage.length() - Config.SystemMessages.dotAnimationMaxDots + dotCount;
			systemMessage = systemMessage.substring(0, index) + '.' + systemMessage.substring(index + 1);
			dotCount++;
		}

		// if max reached reset the original message and the dot counter
		if (dotCount == Config.SystemMessages.dotAnimationMaxDots) {
			systemMessage = previousSysMsg;
			dotCount = 0;
		}

		dotAnimationRanOnce = true;
	}

}
