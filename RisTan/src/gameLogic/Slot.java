package gameLogic;

//A slot on the game board
class Slot {
	private Point point;
	private Tile tile;
	
	Slot(Point point){
		this(point,null);
	}
	
	Slot(Point point,Tile tile){
		this.point=point;
		this.tile=tile;
	}
	
	Point getPoint() {
		return point;
	}
	
	Tile getTile() {
		return tile;
	}
	
	void SetTile(Tile tile) {
		this.tile=tile;
	}
}
