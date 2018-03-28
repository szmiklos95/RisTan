package gameLogic;

abstract class ObjectWithID {
	private static int nextID=0;
	private static int getNextID() {
		int ret=nextID;
		nextID++;
		return ret;
	}
	
	private int ID;
	
	ObjectWithID(){
		ID=getNextID();
	}
	
	ObjectWithID(int ID){
		this.ID=ID;
		if(ID>=nextID) {
			nextID=ID+1;
		}
	}
	
	int getID() {
		return ID;
	}
}
