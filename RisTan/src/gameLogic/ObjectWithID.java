package gameLogic;

/**
 * An object which has an automatically incrementing ID. The IDs can be set manually, but in this case the uniqueness of the IDs is not guaranteed.
 * @author Andras
 *
 */
abstract class ObjectWithID {
	/**
	 * The ID of the next object.
	 */
	private static int nextID=0;
	/**
	 * Gets the next ID and automatically increments it.
	 * @return the current next ID.
	 */
	private static int getNextID() {
		int ret=nextID;
		nextID++;
		return ret;
	}
	
	/**
	 * The ID of the object.
	 */
	private int ID;
	
	/**
	 * Constructor, uses the automatically incrementing ID.
	 */
	ObjectWithID(){
		ID=getNextID();
	}
	
	/**
	 * Constructor with arbitrary ID. If this is used, the uniqueness is not guaranteed.
	 * @param ID the ID of the object.
	 */
	ObjectWithID(int ID){
		this.ID=ID;
		if(ID>=nextID) {
			nextID=ID+1;
		}
	}
	
	/**
	 * Gets the ID.
	 * @return the ID.
	 */
	int getID() {
		return ID;
	}
}
