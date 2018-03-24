package gameLogic;

import config.Config;

//The possible building levels of tiles
enum BuildingLevel{
	None(Config.BuildingLevel.None.resourceNum,Config.BuildingLevel.None.score),
	Village(Config.BuildingLevel.Village.resourceNum,Config.BuildingLevel.Village.score),
	Town(Config.BuildingLevel.Town.resourceNum,Config.BuildingLevel.Town.score);
	
	private final int resourceNum;
	private final int score;
	
	private BuildingLevel(int resourceNum,int score) {
		this.resourceNum=resourceNum;
		this.score=score;
	}
	
	int getResourceNum() {
		return resourceNum;
	}
	
	int getScore() {
		return score;
	}
}
