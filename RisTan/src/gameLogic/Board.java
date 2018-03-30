package gameLogic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
		List<Point> points=getTileCoordinates();
		for(int i=0;i<points.size();++i) {
			Point point=points.get(i);
			Tile tile;
			do{
				tile=getNewTile(res_prob);
			}while(tile==null);
			tiles.put(point,tile);
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
		List<Point> points=getTileCoordinates();
		for(int i=0;i<points.size();++i) {
			Point point=points.get(i);
			Tile tile=new Tile(str[i]);
			tiles.put(point,tile);
		}
	}
	
	//returns a generator string from which the same board can be generated
	public String getGeneratorString() {
		StringBuilder stringBuilder=new StringBuilder();
		List<Point> points=getTileCoordinates();
		for(int i=0;i<points.size();++i) {
			Point point=points.get(i);
			Tile tile=tiles.get(point);
			stringBuilder.append(tile.getGeneratorString()+" ");
		}
		return stringBuilder.toString();
	}
	
	//the given player gets the resources from his/her tiles
	public void harvest(Player player) {
		List<Point> points=getTileCoordinates();
		for(int i=0;i<points.size();++i) {
			Point point=points.get(i);
			Tile tile=tiles.get(point);
			if(tile.getOwner().getID()==player.getID()) {
				tile.harvest();
			}
		}
	}
	
	//gets the tile at the given position
	public Tile getTileAt(Point point) {
		return tiles.get(point);
	}
	
	private List<Point> getTileCoordinates(){
		List<Point> ret=new ArrayList<Point>();
		int size=Config.Board.size;
		for(int i=-size;i<=size;++i) {
			for(int j=-size;j<=size;++j) {
				if(Math.abs(i-j)<=size) {
					ret.add(new Point(i,j));
				}
			}
		}
		return ret;
	}
	
	List<Point> getFreeTileCoordinates(){
		List<Point> ret=new ArrayList<Point>();
		List<Point> points=getTileCoordinates();
		for(int i=0;i<points.size();++i) {
			Point point=points.get(i);
			Tile tile=tiles.get(point);
			if(tile.getOwner()==null) {
				ret.add(point);
			}
		}
		return ret;
	}
}
