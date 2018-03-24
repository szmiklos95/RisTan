package gameLogic;

//The possible building levels of tiles
enum BuildingLevel{
	None(1,1),
	Village(2,4),
	Town(3,6);
	
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
