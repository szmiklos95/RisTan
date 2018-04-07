package network;

import java.io.Serializable;

public class TransferClass implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// class variables
	private String s;
	private int i;
	private double d;
	
	//Constructor
	TransferClass(String s, int i, double d){
		this.s = s;
		this.i = i;
		this.d = d;
	}
	// Copy Constructor
	TransferClass(TransferClass tf){
		this.s = tf.s;
		this.i = tf.i;
		this.d = tf.d;
	}
	
	//methods
	public void Print() {
		System.out.println("s=" + s +" i=" + i +" d=" + d);
	}
	
	public String GetString() {
		return this.s;
	}
	
	public int GetInt() {
		return this.i;
	}
	
	public double GetDouble() {
		return this.d;
	}
}
