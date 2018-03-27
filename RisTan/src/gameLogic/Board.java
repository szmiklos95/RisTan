package gameLogic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import config.Config;

//The game board
public class Board {
	private List<Slot> slots;
	private Map<Resource,Double> res_prob;
	private Random rand;
	
	public Board(Map<Resource,Double> res_prob) {
		this.res_prob=res_prob;
		rand=new Random();
	}
	
	public void generate() {
		slots=new ArrayList<Slot>();
		int size=Config.Board.size;
		for(int i=-size;i<=size;++i) {
			for(int j=-size;j<=size;++j) {
				if(Math.abs(i-j)<=size) {
					Tile tile;
					do {
						tile=getNewTile();
					}while(tile==null);
					Point point=new Point(i,j);
					Slot slot=new Slot(point,tile);
					slots.add(slot);
				}
			}
		}
	}
	
	private Tile getNewTile() {
		double rnd=rand.nextDouble();
		Resource[] resources=Resource.values();
		for(int i=0;i<resources.length;++i) {
			Resource resource=resources[i];
			if(rnd<res_prob.get(resource)){
				return new Tile(resource);
			}
		}
		return null;
	}
	
	public void generate(String generatorString) {
		slots=new ArrayList<Slot>();
		String[] str=generatorString.split(" ");
		int index=0;
		int size=Config.Board.size;
		for(int i=-size;i<=size;++i) {
			for(int j=-size;j<=size;++j) {
				if(Math.abs(i-j)<=size) {
					Tile tile=new Tile(str[index]);
					Point point=new Point(i,j);
					Slot slot=new Slot(point,tile);
					slots.add(slot);
					++index;
				}
			}
		}
	}
	
	public String getGeneratorString() {
		StringBuilder stringBuilder=new StringBuilder();
		int size=Config.Board.size;
		for(int i=-size;i<=size;++i) {
			for(int j=-size;j<=size;++j) {
				if(Math.abs(i-j)<=size) {
					Tile tile=getTileAt(new Point(i,j));
					stringBuilder.append(tile.getGeneratorString()+" ");
				}
			}
		}
		return stringBuilder.toString();
	}
	
	private Tile getTileAt(Point point) {
		for(int i=0;i<slots.size();++i) {
			Slot slot=slots.get(i);
			if(slot.getPoint().equals(point)) {
				return slot.getTile();
			}
		}
		return null;
	}
	
	public void harvest(Player player) {
		int size=Config.Board.size;
		for(int i=-size;i<=size;++i) {
			for(int j=-size;j<=size;++j) {
				if(Math.abs(i-j)<=size) {
					Tile tile=getTileAt(new Point(i,j));
					if(tile.getOwner().getID()==player.getID()) {
						tile.harvest();
					}
				}
			}
		}
	}
}
