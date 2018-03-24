package config;

//Configuration options for the game
public abstract class Config {
	//BuildingLevel config
	public static class BuildingLevel{
		public static class None{
			public static final int resourceNum=1;
			public static final int score=1;
		}
		
		public static class Village{
			public static final int resourceNum=1;
			public static final int score=1;
		}
		
		public static class Town{
			public static final int resourceNum=1;
			public static final int score=1;
		}
	}
}
