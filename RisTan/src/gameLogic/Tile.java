package gameLogic;

//A tile of the board
class Tile {
	private Resource resource;
	private BuildingLevel buildingLevel;
	private Player owner;
	
	Tile(Resource resource) {
		this.resource=resource;
		buildingLevel=BuildingLevel.None;
		owner=null;
	}
	
	void harvest() {
		if(owner!=null) {
			int amount=buildingLevel.getResourceNum();
			owner.giveResource(resource, amount);
		}
	}
	
	void setBuildingLevel(BuildingLevel buildingLevel) {
		this.buildingLevel=buildingLevel;
	}
	
	void occupy(Player newOwner) {
		int score=buildingLevel.getScore();
		if(owner!=null) {
			owner.addScore(-score);
		}
		owner=newOwner;
		owner.addScore(score);
	}
}