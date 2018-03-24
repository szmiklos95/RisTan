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
	
	public boolean hasResource(Resource resource,int amount) {
		return resources.get(resource)>=amount;
	}
	
	public void giveResource(Resource resource,int amount) {
		int eddig=resources.get(resource);
		resources.put(resource,eddig+amount);
	}
	
	public void takeResource(Resource resource,int amount)throws GameLogicException{
		if(!hasResource(resource,amount)) {
			throw new InsufficientResourceException();
		}
		int eddig=resources.get(resource);
		resources.put(resource,eddig-amount);
	}
}
