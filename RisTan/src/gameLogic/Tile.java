package gameLogic;

/**
 * A tile of the game.
 * @author Andras
 *
 */
public class Tile {
	/**
	 * The resource of the tile.
	 */
	private Resource resource;
	/**
	 * The building level of the tile.
	 */
	private BuildingLevel buildingLevel;
	/**
	 * The owner of the tile.
	 */
	private Player owner;
	
	/**
	 * Constructor, creates a tile with the given resource. Used in the server for generation.
	 * @param resource the resource of the tile.
	 */
	Tile(Resource resource) {
		init(resource);
	}
	
	/**
	 * Constructor, recreates a tile from a given generator string. (Custom deserializer.)
	 * @param generator the generator string.
	 */
	Tile(String generator){
		int ordinal=Integer.parseInt(generator);
		init(Resource.values()[ordinal]);
	}
	
	/**
	 * The common part of the constructors.
	 * @param resource the resource of the tile.
	 */
	private void init(Resource resource) {
		this.resource=resource;
		buildingLevel=BuildingLevel.None;
		owner=null;
	}
	
	/**
	 * Gets the owner of the tile.
	 * @return the owner of the tile.
	 */
	public Player getOwner() {
		return owner;
	}
	
	/**
	 * Gets a string from which the same tile can be generated. (Custom serializer.)
	 * @return the generator string.
	 */
	String getGeneratorString() {
		return ""+resource.ordinal();
	}
	
	/**
	 * The owner of the tile gets the tile resource in an amount based on the building level.
	 */
	void harvest() {
		if(owner!=null) {
			int amount=buildingLevel.getResourceNum();
			owner.giveResource(resource, amount);
		}
	}
	
	/**
	 * Gets the building level.
	 * @return the building level.
	 */
	public BuildingLevel getBuildingLevel() {
		return buildingLevel;
	}
	
	/**
	 * Sets the building level.
	 * @param buildingLevel the nw building level.
	 */
	void setBuildingLevel(BuildingLevel buildingLevel) {
		if(owner!=null) {
			owner.addScore(-this.buildingLevel.getScore());
			owner.addScore(buildingLevel.getScore());
		}
		this.buildingLevel=buildingLevel;
	}
	
	/**
	 * The new owner occupies the tile, the function also updates the scores.
	 * @param newOwner the new owner of the tile.
	 */
	void occupy(Player newOwner) {
		int score=buildingLevel.getScore();
		if(owner!=null) {
			owner.addScore(-score);
		}
		owner=newOwner;
		owner.addScore(score);
	}
	
	/**
	 * Gets the resource.
	 * @return the tile.
	 * 
	 * @author Miklós
	 */
	public Resource getResource() {
		return resource;
	}
}