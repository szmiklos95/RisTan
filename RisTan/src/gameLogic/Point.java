package gameLogic;

import java.io.Serializable;
import java.util.Objects;

/**
 * A point in the board coordinate system.
 * @author Andras
 *
 */
public class Point implements Serializable{
	private static final long serialVersionUID = 1L;
	
	/**
	 * The i coordinate of the point.
	 */
	private int i;
	/**
	 * The j coordinate of the point.
	 */
	private int j;
	
	/**
	 * Constructor.
	 * @param i the i coordinate of the point.
	 * @param j the j coordinate of the point.
	 */
	public Point(int i,int j){
		this.i=i;
		this.j=j;
	}
	
	/**
	 * Gets the i coordinate of the point.
	 * @return the i coordinate.
	 */
	public int getI() {
		return i;
	}
	
	/**
	 * Gets the j coordinate of the point.
	 * @return the j coordinate.
	 */
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
	
	/**
	 * Conversion function to the Descrates coordinate system.
	 * @return the x Descartes coordinate.
	 */
	public double getDescartesX() {
		return i+(double)j/2;
	}
	
	/**
	 * Conversion function to the Descrates coordinate system.
	 * @return the y Descartes coordinate.
	 */
	public double getDescartesY() {
		return (double)j*Math.sqrt(3)/2;
	}
	
	/**
	 * Writes the coordinates to the console, debug function.
	 */
	public void printPoint() {
		System.out.printf("(%d:%d)\n", i,j);
	}
}