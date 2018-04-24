package gameLogic;

//A tile of the board
class Tile {
	private Resource resource;
	private BuildingLevel buildingLevel;
	private Player owner;
	
	//creates tile with the given resource
	Tile(Resource resource) {
		init(resource);
	}
	
	//creates tile from the generator string
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
	
	//returns a string from which the same tile can be generated
	String getGeneratorString() {
		return ""+resource.ordinal();
	}
	
	void harvest() {
		if(owner!=null) {
			int amount=buildingLevel.getResourceNum();
			owner.giveResource(resource, amount);
		}
	}
	
	BuildingLevel getBuildingLevel() {
		return buildingLevel;
	}
	
	void setBuildingLevel(BuildingLevel buildingLevel) {
		if(owner!=null) {
			owner.addScore(-this.buildingLevel.getScore());
			owner.addScore(buildingLevel.getScore());
		}
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
	
	/**
	 * 
	 * @return
	 * 
	 * @author Miklós
	 */
	public Resource getResource() {
		return resource;
	}
}