package gameLogic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import config.Config;

/**
 * The game board.
 * @author Andras
 *
 */
public class Board {
	/**
	 * The tiles of the board.
	 */
	private Map<Point,Tile> tiles;
	/**
	 * A random generator used for tile generation.
	 */
	private Random rand;
	
	/**
	 * Constructor. Does not generate the tiles.
	 */
	Board() {
		tiles=null;
		rand=null;
	}
	
	/**
	 * Generates the tile based on the res_prob resource probabilities.
	 * @param res_prob resource probabilities of the tiles.
	 */
	public void generate(Map<Resource,Double> res_prob) {
		rand=new Random();
		tiles=new HashMap<Point,Tile>();
		List<Point> points=getTileCoordinates();
		for(int i=0;i<points.size();++i) {
			Point point=points.get(i);
			Tile tile;
			do {
				tile=getNewTile(res_prob);
			}while(tile==null);
			tiles.put(point,tile);
		}
		rand=null;
	}
	
	/**
	 * Creates a new tile with random resource.
	 * @param res_prob resource probabilities of the tile.
	 * @return the new tile.
	 */
	private Tile getNewTile(Map<Resource,Double> res_prob) {
		double rnd=rand.nextDouble();
		Resource[] resources=Resource.values();
		for(int i=0;i<resources.length;++i) {
			Resource resource=resources[i];
			if(rnd<res_prob.get(resource)){
				return new Tile(resource);
			}else{
				rnd-=res_prob.get(resource);
			}
		}
		return null;
	}
	
	/**
	 * Generates a board from a generator string. (Custom deserializer.)
	 * @param generatorString
	 */
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
	
	/**
	 * Returns a generator string from which the same board can be generated. (Custom serializer.)
	 * @return the generator string.
	 */
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
	
	/**
	 * The given player gets the resources from his/her tiles.
	 * @param player the player who gets the resources.
	 */
	public void harvest(Player player) {
		List<Point> points=getTileCoordinates();
		for(int i=0;i<points.size();++i) {
			Point point=points.get(i);
			Tile tile=tiles.get(point);
			if((tile.getOwner()!=null)&&(tile.getOwner().getID()==player.getID())) {
				tile.harvest();
			}
		}
	}
	
	/**
	 * Gets the tiles.
	 * @return the tiles.
	 */
	public Map<Point,Tile> getTiles(){
		return tiles;
	}
	
	/**
	 * Gets a tile at a given position.
	 * @param point the position of the tile.
	 * @return the tile at the position or null if no such tile exists.
	 */
	public Tile getTileAt(Point point) {
		return tiles.get(point);
	}
	
	//coordinate functions
	
	/**
	 * Gets the coordinates of every tile.
	 * @return the list of tile coordinates.
	 */
	public List<Point> getTileCoordinates(){
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
	
	/**
	 * Gets the 6 neighbour coordinates of a given point.
	 * @param point the given point.
	 * @return list of the negihbour coordintes.
	 */
	private List<Point> getNeighbourPossibleTileCoordinates(Point point){
		List<Point> ret=new ArrayList<Point>();
		int i=point.getI();
		int j=point.getJ();
		ret.add(new Point(i+1,j));
		ret.add(new Point(i-1,j));
		ret.add(new Point(i,j+1));
		ret.add(new Point(i,j-1));
		ret.add(new Point(i-1,j+1));
		ret.add(new Point(i+1,j-1));
		return ret;
	}
	
	/**
	 * Filters a coordinate list for coordinates with free tiles.
	 * @param input the coordinate list to filter.
	 * @return the filtered list, only contains free tile coordinates.
	 */
	private List<Point> filterForFree(List<Point> input){
		List<Point> ret=new ArrayList<Point>();
		for(int i=0;i<input.size();++i) {
			Point point=input.get(i);
			Tile tile=tiles.get(point);
			if(tile.getOwner()==null) {
				ret.add(point);
			}
		}
		return ret;
	}
	
	/**
	 * Filters a coordinate list for coordinates with tiles with a given owner.
	 * @param input the coordinate list to filter.
	 * @param playerID the ID of the owner.
	 * @return the filtered list, only contains tile coordinates of the tiles owned by player identified by playerID.
	 */
	private List<Point> filterForOwn(List<Point> input,int playerID){
		List<Point> ret=new ArrayList<Point>();
		for(int i=0;i<input.size();++i) {
			Point point=input.get(i);
			Tile tile=tiles.get(point);
			if(tile.getOwner()!=null) {
				if(tile.getOwner().getID()==playerID) {
					ret.add(point);
				}
			}
		}
		return ret;
	}
	
	/**
	 * Filters a coordinate list for coordinates with tiles with any owner but a given player.
	 * @param input the coordinate list to filter.
	 * @param playerID the ID of the player whose enemies' tiles are desired.
	 * @return the filtered list, only contains tile coordinates of the tiles owned by any other player but the one identified by playerID.
	 */
	private List<Point> filterForEnemy(List<Point> input,int playerID){
		List<Point> ret=new ArrayList<Point>();
		for(int i=0;i<input.size();++i) {
			Point point=input.get(i);
			Tile tile=tiles.get(point);
			if(tile.getOwner()!=null) {
				if(tile.getOwner().getID()!=playerID) {
					ret.add(point);
				}
			}
		}
		return ret;
	}
	
	/**
	 * Filters a coordinate list for coordinates with tiles with a given building level.
	 * @param input the coordinate list to filter.
	 * @param buildingLevel the desired building level.
	 * @return the filtered list, only contains tile coordinates of the tiles with the desired building level.
	 */
	private List<Point> filterForBuildingLevel(List<Point> input,BuildingLevel buildingLevel){
		List<Point> ret=new ArrayList<Point>();
		for(int i=0;i<input.size();++i) {
			Point point=input.get(i);
			Tile tile=tiles.get(point);
			if(tile.getBuildingLevel()==buildingLevel) {
				ret.add(point);
			}
		}
		return ret;
	}
	
	/**
	 * Filters a coordinate list for coordinates with tiles which are neighbour to any of the given player's tiles.
	 * @param input the coordinate list to filter.
	 * @param buildingLevel the player whose tiles' neighbours are desired.
	 * @return the filtered list, only contains tile coordinates of the tiles which are neighbour to any of the given player's tiles.
	 */
	private List<Point> filterForNeighbourOf(List<Point> input,int playerID){
		List<Point> ret=new ArrayList<Point>();
		for(int i=0;i<input.size();++i) {
			Point point=input.get(i);
			List<Point> neighbours=getNeighbourPossibleTileCoordinates(point);
			for(int j=0;j<neighbours.size();++j) {
				Point neighbour=neighbours.get(j);
				Tile tile=tiles.get(neighbour);
				if((tile!=null)&&(tile.getOwner()!=null)&&(tile.getOwner().getID()==playerID)) {
					ret.add(point);
					break;
				}
			}
		}
		return ret;
	}
	
	/**
	 * Gets the free tile coordinates.
	 * @return a list of the free tile coordinates.
	 */
	List<Point> getFreeTileCoordinates(){
		return filterForFree(getTileCoordinates());
	}
	
	/**
	 * Gets the free neighbour tiles of a given player's territory.
	 * @param playerID the given player's ID
	 * @return a list of free tile coordinates which are negihbours with the given player's territory.
	 */
	List<Point> getFreeNeighbourTileCoordinates(int playerID){
		return filterForNeighbourOf(getFreeTileCoordinates(),playerID);
	}
	
	/**
	 * Gets the coordinates of tiles owned by a given player.
	 * @param playerID the ID of the given player.
	 * @return a list of tile coordinates which are owned by a given player.
	 */
	List<Point> getPlayerTileCoordinates(int playerID){
		return filterForOwn(getTileCoordinates(),playerID);
	}
	
	/**
	 * Gets the coordinates of empty tiles owned by a given player.
	 * @param playerID the ID of the given player.
	 * @return a list of empty tile coordinates which are owned by a given player.
	 */
	List<Point> getPlayerEmptyTileCoordinates(int playerID){
		return filterForBuildingLevel(getPlayerTileCoordinates(playerID),BuildingLevel.None);
	}
	
	/**
	 * Gets the coordinates of village tiles owned by a given player.
	 * @param playerID the ID of the given player.
	 * @return a list of village tile coordinates which are owned by a given player.
	 */
	List<Point> getPlayerVillageCoordinates(int playerID){
		return filterForBuildingLevel(getPlayerTileCoordinates(playerID),BuildingLevel.Village);
	}
	
	/**
	 * Gets the enemy neighbour tiles of a given player's territory.
	 * @param playerID the given player's ID
	 * @return a list of enemy tile coordinates which are negihbours with the given player's territory.
	 */
	private List<Point> getEnemyNeighbourTileCoordinates(int playerID){
		return filterForNeighbourOf(filterForEnemy(getTileCoordinates(),playerID),playerID);
	}
	
	/**
	 * Gets the enemy empty neighbour tiles of a given player's territory.
	 * @param playerID the given player's ID
	 * @return a list of enemy empty tile coordinates which are negihbours with the given player's territory.
	 */
	List<Point> getEnemyNeighbourEmptyTileCoordinates(int playerID){
		return filterForBuildingLevel(getEnemyNeighbourTileCoordinates(playerID),BuildingLevel.None);
	}
	
	/**
	 * Gets the enemy village neighbour tiles of a given player's territory.
	 * @param playerID the given player's ID
	 * @return a list of enemy village tile coordinates which are negihbours with the given player's territory.
	 */
	List<Point> getEnemyNeighbourVillageTileCoordinates(int playerID){
		return filterForBuildingLevel(getEnemyNeighbourTileCoordinates(playerID),BuildingLevel.Village);
	}
	
	/**
	 * Gets the enemy town neighbour tiles of a given player's territory.
	 * @param playerID the given player's ID
	 * @return a list of enemy town tile coordinates which are negihbours with the given player's territory.
	 */
	List<Point> getEnemyNeighbourTownTileCoordinates(int playerID){
		return filterForBuildingLevel(getEnemyNeighbourTileCoordinates(playerID),BuildingLevel.Town);
	}
}
