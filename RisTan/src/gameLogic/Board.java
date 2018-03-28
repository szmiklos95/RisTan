package gameLogic;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import config.Config;

//The game board
public class Board {
	private Map<Point,Tile> tiles;
	private Map<Resource,Double> res_prob;
	private Random rand;
	
	public Board(Map<Resource,Double> res_prob) {
		this.res_prob=res_prob;
		rand=new Random();
	}
	
	public void generate() {
		tiles=new HashMap<Point,Tile>();
		int size=Config.Board.size;
		for(int i=-size;i<=size;++i) {
			for(int j=-size;j<=size;++j) {
				if(Math.abs(i-j)<=size) {
					Tile tile;
					do {
						tile=getNewTile();
					}while(tile==null);
					Point point=new Point(i,j);
					tiles.put(point,tile);
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
		tiles=new HashMap<Point,Tile>();
		String[] str=generatorString.split(" ");
		int index=0;
		int size=Config.Board.size;
		for(int i=-size;i<=size;++i) {
			for(int j=-size;j<=size;++j) {
				if(Math.abs(i-j)<=size) {
					Tile tile=new Tile(str[index]);
					Point point=new Point(i,j);
					tiles.put(point,tile);
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
					Tile tile=tiles.get(new Point(i,j));
					stringBuilder.append(tile.getGeneratorString()+" ");
				}
			}
		}
		return stringBuilder.toString();
	}
	
	public void harvest(Player player) {
		int size=Config.Board.size;
		for(int i=-size;i<=size;++i) {
			for(int j=-size;j<=size;++j) {
				if(Math.abs(i-j)<=size) {
					Tile tile=tiles.get(new Point(i,j));
					if(tile.getOwner().getID()==player.getID()) {
						tile.harvest();
					}
				}
			}
		}
	}
}
