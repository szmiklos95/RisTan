package gameLogic;

//A point in the coordinate system
public class Point{
	private int i;
	private int j;
	
	public Point(int i,int j){
		this.i=i;
		this.j=j;
	}
	
	public int getI() {
		return i;
	}
	
	public int getJ() {
		return j;
	}
	
	public boolean equals(Object other) {
		if(other==null) {
			return false;
		}
		if(other==this) {
			return true;
		}
		if(!(other instanceof Point)) {
			return false;
		}
		Point that=(Point)other;
		return (that.i==i)&&(that.j==j);
	}
}