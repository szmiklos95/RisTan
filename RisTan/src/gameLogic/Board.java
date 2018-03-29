package gameLogic;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import config.Config;

//The game board
public class Board {
	private Map<Point,Tile> tiles;
	private Random rand;
	
	Board() {
		tiles=null;
		rand=new Random();
	}
	
	//generates the tile based on the res_prob resource probabilities
	public void generate(Map<Resource,Double> res_prob) {
		tiles=new HashMap<Point,Tile>();
		int size=Config.Board.size;
		for(int i=-size;i<=size;++i) {
			for(int j=-size;j<=size;++j) {
				if(Math.abs(i-j)<=size) {
					Tile tile;
					do {
						tile=getNewTile(res_prob);
					}while(tile==null);
					Point point=new Point(i,j);
					tiles.put(point,tile);
				}
			}
		}
	}
	
	//creates a new tile
	private Tile getNewTile(Map<Resource,Double> res_prob) {
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
	
	//generates a board from a generator string
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
	
	//returns a generator string from which the same board can be generated
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
	
	//the given player gets the resources from his/her tiles
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
