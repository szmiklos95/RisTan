package network;

/**
 * This is a test for the chat functions:
 * - now this working with 1 player
 * - illustrate the menu in my concept
 * Problems:
 * - the input field for the player's name is needed
 * - Now the Client stored its ID according to the number of instances
 * 	 BUT when the program is running more instances, this number can't use for this
 *   --> The server has to hand out the IDs
 * @author Péter
 *
 */
public class Main {

	public static void main(String[] args) {
		new GameMenu();
		
	}
}
