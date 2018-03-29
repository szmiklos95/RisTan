package gameLogic;

import java.io.Serializable;
import java.util.Objects;

//A point in the coordinate system
public class Point implements Serializable{
	private static final long serialVersionUID = 1L;
	
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
		return (that.i==i)&&(that.j==j);
	}
	
	@Override
	public int hashCode() {
        return Objects.hash(i,j);
    }
}