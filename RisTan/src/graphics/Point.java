package graphics;


//A point in the coordinate system
public class Point{
	
	private int x;
	private int y;
	
	public Point(Point p) {
		this.x = p.x;
		this.y = p.y;
	}
	
	public Point(int x,int y){
		this.x=x;
		this.y=y;
	}
	
	public int getX() {
		return x;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	@Override
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
		return (that.x==x)&&(that.y==y);
	}
	
	public void printPoint() {
		System.out.printf("(%d:%d)\n", x,y);
	}
	
}
