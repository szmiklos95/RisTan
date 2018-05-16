package graphics;

import config.Config;

/**
 * Stores the settings when making a new game (starting a new server).
 * 
 * @author Miklós
 *
 */
public class NewGameSettings {

	/**
	 * The number of players of this game.
	 */
	private int playerCount;

	/**
	 * The name of the player with this client.
	 */
	private String playerName;

	/**
	 * Constructor.
	 */
	public NewGameSettings() {
		playerCount = Config.GUI.default_playerCount;
	}

	/**
	 * Sets the number of players
	 * 
	 * @param playerCount
	 *            the number of players
	 */
	public void setPlayerCount(int playerCount) {
		this.playerCount = playerCount;
	}

	/**
	 * Returns the number of player settings. If a player loses or exits the game
	 * this does not get updated!
	 * 
	 * @return the setup number of players
	 */
	public int getPlayerCount() {
		return playerCount;
	}

	/**
	 * Set the name of the player with this client.
	 * 
	 * @param playerName
	 *            the name of the player with this client
	 */
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	/**
	 * 
	 * @return the name of the player with this client
	 */
	public String getPlayerName() {
		return playerName;
	}
}
