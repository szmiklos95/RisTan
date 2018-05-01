package gameLogic;

import config.Config;

/**
 * The possible building levels of tiles.
 * @author Andras
 *
 */
enum BuildingLevel{
	None(Config.BuildingLevel.None.resourceNum,Config.BuildingLevel.None.score),
	Village(Config.BuildingLevel.Village.resourceNum,Config.BuildingLevel.Village.score),
	Town(Config.BuildingLevel.Town.resourceNum,Config.BuildingLevel.Town.score);
	
	/**
	 * The amount of the resource which the player gets from a tile with this building level.
	 */
	private final int resourceNum;
	/**
	 * The score which the player gets from a tile with this building level.
	 */
	private final int score;
	
	/**
	 * Constructor.
	 * @param resourceNum the amount of the resource which the player gets from a tile with this building level.
	 * @param score the score which the player gets from a tile with this building level.
	 */
	private BuildingLevel(int resourceNum,int score) {
		this.resourceNum=resourceNum;
		this.score=score;
	}
	
	/**
	 * Gets the amount of the resource which the player gets from a tile with this building level.
	 * @return the amount of the resource which the player gets from a tile with this building level.
	 */
	int getResourceNum() {
		return resourceNum;
	}
	
	/**
	 * Gets the score which the player gets from a tile with this building level.
	 * @return the score which the player gets from a tile with this building level.
	 */
	int getScore() {
		return score;
	}
}
