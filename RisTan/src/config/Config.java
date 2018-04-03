package config;

public abstract class Config {
	/* **************************
	 *  Graphic configurations
	 * ***************************/
	
	/**
	 * Main menu
	 */
	public static class MainMenu{
		public static final int width = 800;
		public static final int height = 600;
	}
	
	/**
	 * Board config
	 */
	public static class Board{
		//The number of tile layers around the central tile
		public static final int size=3;
	}
	
	/**
	 * Hexagon config
	 */
	public static class Hexagon{
		//The radius of a hexagon
		public static final int radius = 50;
	}
	
	/**
	 * DrawingPanel config
	 */
	public static class DrawingPanel{
		//The radius of a hexagon
		public static final int width = 1200;
		public static final int height = 800;
	}
}
