package gameLogic;

import java.util.HashMap;
import java.util.Map;

/**
 * A player of the game.
 * @author Andras
 *
 */
public class Player extends ObjectWithID{
	/**
	 * The name of the player.
	 */
	private String name;
	/**
	 * The resources of the player.
	 */
	private Map<Resource,Integer> resources;
	/**
	 * The score of the player.
	 */
	private int score;
	
	/**
	 * Constructor. The player gets the next ID.
	 * @param name the name of the player.
	 */
	public Player(String name) {
		init(name);
	}
	
	/**
	 * Constructor. The player gets the given ID.
	 * @param name the name of the player.
	 * @param ID the ID of the player.
	 */
	public Player(String name,int ID) {
		super(ID);
		init(name);
	}
	
	/**
	 * The common part of the constructors.
	 * @param name the name of the player.
	 */
	private void init(String name) {
		this.name=name;
		this.resources=new HashMap<Resource,Integer>();
		Resource[] resources=Resource.values();
		for(int i=0;i<resources.length;++i) {
			this.resources.put(resources[i],0);
		}
		score=0;
	}
	
	public int getID() {
		return super.getID();
	}
	
	/**
	 * Gets the name.
	 * @return the name.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Gets the score.
	 * @return the score.
	 */
	public int getScore() {
		return score;
	}
	
	/**
	 * Adds a given score to the player.
	 * @param score the score to add.
	 */
	void addScore(int score) {
		this.score+=score;
	}
	
	/**
	 * Checks whether the player has the given amount of the given resource.
	 * @param resource the resource to check.
	 * @param amount the amount of the resource.
	 * @return true if and only if the player has the given amount of the given resource.
	 */
	public boolean hasResource(Resource resource,int amount) {
		return resources.get(resource)>=amount;
	}
	
	/**
	 * Checks whether the player has the given amounts of the given resources.
	 * @param what the resources to check.
	 * @return true if and only if the player has the given amounts of the given resources.
	 */
	public boolean hasResource(Map<Resource,Integer>what) {
		for(Map.Entry<Resource,Integer> e:what.entrySet()) {
			if(!hasResource(e.getKey(),e.getValue())) {
				return false;
			}
		}
		return true;
	}
	
	//gives the given amount of the given resource to the player
	/**
	 * Gives the given amount of the given resource to the player.
	 * @param resource the resource to give.
	 * @param amount the amount of the resource.
	 */
	public void giveResource(Resource resource,int amount) {
		int eddig=resources.get(resource);
		resources.put(resource,eddig+amount);
	}
	
	/**
	 * Gives the given amounts of the given resources to the player.
	 * @param what the resources to add.
	 */
	public void giveResource(Map<Resource,Integer>what) {
		for(Map.Entry<Resource,Integer> e:what.entrySet()) {
			giveResource(e.getKey(),e.getValue());
		}
	}
	
	/**
	 * Takes the given amount of the given resource from the player if it can, else throws an InsufficientResourceException.
	 * @param resource the resource to take.
	 * @param amount the amount of the resource to take.
	 * @throws InsufficientResourceException if the player does not have enough of the resource.
	 */
	public void takeResource(Resource resource,int amount)throws InsufficientResourceException{
		if(!hasResource(resource,amount)) {
			throw new InsufficientResourceException();
		}
		int eddig=resources.get(resource);
		resources.put(resource,eddig-amount);
	}
	
	//takes the given amount of the given resource from the player if it can, else throws an InsufficientResourceException
	/**
	 * Takes the given amounts of the givens resource from the player if it can, else throws an InsufficientResourceException.
	 * @param what the resources to take.
	 * @throws InsufficientResourceException if the player does not have enough of the resources.
	 */
	public void takeResource(Map<Resource,Integer>what)throws InsufficientResourceException{
		if(!hasResource(what)) {
			throw new InsufficientResourceException();
		}
		for(Map.Entry<Resource,Integer> e:what.entrySet()) {
			takeResource(e.getKey(),e.getValue());
		}
	}
	
	/**
	 * 
	 * @param resource
	 * @return
	 * @author Miklós
	 */
	public int getResourceAmount(Resource resource) {
		return resources.get(resource);
	}
}
