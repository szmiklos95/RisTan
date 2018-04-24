package graphics;

import config.Config;

public class NewGameSettings {
	private int playerCount;
	private String serverPlayerName;
	
	public NewGameSettings() {
		playerCount = Config.GUI.default_playerCount;
	}
	
	public void setPlayerCount(int playerCount) {
		this.playerCount = playerCount;
	}
	
	public int getPlayerCount() {
		return playerCount;
	}
	
	public void setServerPlayerName(String serverPlayerName) {
		this.serverPlayerName = serverPlayerName;
	}
	
	public String getServerPlayerName() {
		return serverPlayerName;
	}
}
