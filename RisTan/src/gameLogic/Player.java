package gameLogic;

import java.util.HashMap;
import java.util.Map;

//A player of the game
public class Player extends ObjectWithID{
	private String name;
	private Map<Resource,Integer> resources;
	private int score;
	
	public Player(String name) {
		init(name);
	}
	
	public Player(String name,int ID) {
		super(ID);
		init(name);
	}
	
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
	
	public String getName() {
		return name;
	}
	
	public int getScore() {
		return score;
	}
	
	void addScore(int score) {
		this.score+=score;
	}
	
	//checks whether the player has the given amount of the given resource
	public boolean hasResource(Resource resource,int amount) {
		return resources.get(resource)>=amount;
	}
	
	//checks whether the player has the given amounts of the given resources
	public boolean hasResource(Map<Resource,Integer>what) {
		for(Map.Entry<Resource,Integer> e:what.entrySet()) {
			if(!hasResource(e.getKey(),e.getValue())) {
				return false;
			}
		}
		return true;
	}
	
	//gives the given amount of the given resource to the player
	public void giveResource(Resource resource,int amount) {
		int eddig=resources.get(resource);
		resources.put(resource,eddig+amount);
	}
	
	//gives the given amounts of the given resources to the player
	public void giveResource(Map<Resource,Integer>what) {
		for(Map.Entry<Resource,Integer> e:what.entrySet()) {
			giveResource(e.getKey(),e.getValue());
		}
	}
	
	//takes the given amount of the given resource from the player if it can, else throws an InsufficientResourceException
	public void takeResource(Resource resource,int amount)throws InsufficientResourceException{
		if(!hasResource(resource,amount)) {
			throw new InsufficientResourceException();
		}
		int eddig=resources.get(resource);
		resources.put(resource,eddig-amount);
	}
	
	//takes the given amount of the given resource from the player if it can, else throws an InsufficientResourceException
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
