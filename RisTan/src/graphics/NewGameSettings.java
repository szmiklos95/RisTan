package graphics;

import config.Config;

public class NewGameSettings {
	private int playerCount;
	private String playerName;
	
	public NewGameSettings() {
		playerCount = Config.GUI.default_playerCount;
	}
	
	public void setPlayerCount(int playerCount) {
		this.playerCount = playerCount;
	}
	
	public int getPlayerCount() {
		return playerCount;
	}
	
	public void setPlayerName(String serverPlayerName) {
		this.playerName = serverPlayerName;
	}
	
	public String getPlayerName() {
		return playerName;
	}
}
