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
				if(Math.abs(i+j)<=size) {
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
	
	private List<Point> getNeighbourTileCoordinates(Point point){
		List<Point> ret=new ArrayList<Point>();
		int i=point.getI();
		int j=point.getJ();
		int size=Config.Board.size;
		if((i+1<=size)&&(i+j+1<=size)) {
			ret.add(new Point(i+1,j));
		}
		if((i-1>=size)&&(i+j-1>=size)) {
			ret.add(new Point(i-1,j));
		}
		if((j+1<=size)&&(i+j+1<=size)) {
			ret.add(new Point(i,j+1));
		}
		if((j-1>=size)&&(i+j-i<=size)) {
			ret.add(new Point(i,j-1));
		}
		if((i-1>=size)&&(j+1<=size)) {
			ret.add(new Point(i-1,j+1));
		}
		if((i+1<=size)&&(j-1>=size)) {
			ret.add(new Point(i+1,j-1));
		}
		return ret;
	}
	
	List<Point> getFreeNeighbourTileCoordinates(int playerID){
		List<Point> ret=new ArrayList<Point>();
		List<Point> points=getFreeTileCoordinates();
		for(int i=0;i<points.size();++i) {
			Point point=points.get(i);
			List<Point> neighbours=getNeighbourTileCoordinates(point);
			for(int j=0;j<neighbours.size();++j) {
				Point neighbour=neighbours.get(j);
				Tile tile=tiles.get(neighbour);
				if(tile.getOwner().getID()==playerID) {
					ret.add(point);
					break;
				}
			}
		}
		return ret;
	}
	
	List<Point> getPlayerTileCoordinates(int playerID){
		List<Point> ret=new ArrayList<Point>();
		List<Point> points=getTileCoordinates();
		for(int i=0;i<points.size();++i) {
			Point point=points.get(i);
			Tile tile=tiles.get(point);
			if(tile.getOwner().getID()==playerID) {
				ret.add(point);
			}
		}
		return ret;
	}
	
	List<Point> getPlayerEmptyTileCoordinates(int playerID){
		List<Point> ret=new ArrayList<Point>();
		List<Point> points=getPlayerTileCoordinates(playerID);
		for(int i=0;i<points.size();++i) {
			Point point=points.get(i);
			Tile tile=tiles.get(point);
			if(tile.getBuildingLevel()==BuildingLevel.None) {
				ret.add(point);
			}
		}
		return ret;
	}
}
