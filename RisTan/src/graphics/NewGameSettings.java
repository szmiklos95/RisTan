package graphics;

import config.Config;

public class NewGameSettings {
	private int playerCount;
	
	
	public NewGameSettings() {
		playerCount = Config.GUI.default_playerCount;
	}
	
	public void setPlayerCount(int playerCount) {
		this.playerCount = playerCount;
	}
	
	public int getPlayerCount() {
		return playerCount;
	}
}
