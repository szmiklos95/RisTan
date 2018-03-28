package gameLogic;

//A tile of the board
class Tile {
	private Resource resource;
	private BuildingLevel buildingLevel;
	private Player owner;
	
	Tile(Resource resource) {
		init(resource);
	}
	
	Tile(String generator){
		int ordinal=Integer.parseInt(generator);
		init(Resource.values()[ordinal]);
	}
	
	private void init(Resource resource) {
		this.resource=resource;
		buildingLevel=BuildingLevel.None;
		owner=null;
	}
	
	Player getOwner() {
		return owner;
	}
	
	String getGeneratorString() {
		return ""+resource.ordinal();
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
	
	//the tile gets occupied by the given player, it also modifies the scores accordingly
	void occupy(Player newOwner) {
		int score=buildingLevel.getScore();
		if(owner!=null) {
			owner.addScore(-score);
		}
		owner=newOwner;
		owner.addScore(score);
	}
}