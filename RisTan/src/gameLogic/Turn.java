package gameLogic;

abstract class Turn {
	private int remainingTime;
	
	Turn(){
		reset();
	}
	
	void reset(){
		remainingTime=0;
	}
	
	int getRemainingTime() {
		return remainingTime;
	}
	
	void takeTime(int time)throws GameLogicException{
		if(remainingTime<time) {
			throw new NotEnoughTimeException();
		}
		remainingTime-=time;
	}
}
