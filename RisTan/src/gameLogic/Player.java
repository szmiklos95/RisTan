package gameLogic;

import java.util.Map;

//A player of the game
public class Player {
	private static int nextID=0;
	private static int getNextID() {
		int ret=nextID;
		nextID++;
		return ret;
	}
	
	private int ID;
	private String name;
	private Map<Resource,Integer> resources;
	private int score;
	
	public Player(String name) {
		ID=getNextID();
		this.name=name;
		Resource[] resources=Resource.values();
		for(int i=0;i<resources.length;++i) {
			this.resources.put(resources[i],0);
		}
		score=0;
	}
	
	//TODO Player(String name,int ID)
	
	public int getID() {
		return ID;
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
	
	//gives the given amount of the given resource to the player
	public void giveResource(Resource resource,int amount) {
		int eddig=resources.get(resource);
		resources.put(resource,eddig+amount);
	}
	
	//takes the given amount of the given resource from the player if it can, else throws an InsufficientResourceException
	public void takeResource(Resource resource,int amount)throws InsufficientResourceException{
		if(!hasResource(resource,amount)) {
			throw new InsufficientResourceException();
		}
		int eddig=resources.get(resource);
		resources.put(resource,eddig-amount);
	}
	
	//TODO: resource functions for fn(Map<Resource,amount>)
}
