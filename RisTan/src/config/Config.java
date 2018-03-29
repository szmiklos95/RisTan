package config;

import java.util.Map;
import java.util.HashMap;

import gameLogic.Resource;

//Configuration options for the game
public abstract class Config {
	//Actions config
	public static class Action{
		public static class BuildVillage{
			public static final int time=2;
			public static final Map<Resource,Integer> cost=createCost();
			private static Map<Resource,Integer> createCost(){
				HashMap<Resource,Integer> ret=new HashMap<Resource,Integer>();
				ret.put(Resource.Stone,1);
				ret.put(Resource.Wheat,1);
				ret.put(Resource.Wood,1);
				return ret;
			}
		}
		public static class OccupyFreeTile{
			public static final int time=2;
			public static final Map<Resource,Integer> cost=new HashMap<Resource,Integer>();
		}
	}
	
	//Board config
	public static class Board{
		//The number of tile layers around the central tile
		public static final int size=3;
		//resource probabilities
		public static final Map<Resource,Double> res_prob=createRes_prob();
		private static Map<Resource,Double> createRes_prob(){
			HashMap<Resource,Double> ret=new HashMap<Resource,Double>();
			ret.put(Resource.Stone,((double)1)/3);
			ret.put(Resource.Wheat,((double)1)/3);
			ret.put(Resource.Wood,((double)1)/3);
			return ret;
		}
	}
	
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
