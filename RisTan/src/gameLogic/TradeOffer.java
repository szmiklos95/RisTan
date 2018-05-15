package gameLogic;

/**
 * Stores a trade order.
 * @author Andras
 *
 */
public class TradeOffer extends ObjectWithID{
	/**
	 * The ID of the offering player.
	 */
	int offererID;
	/**
	 * The resource which the offerer offers.
	 */
	Resource give;
	/**
	 * The amount of the offered resource.
	 */
	int give_amount;
	/**
	 * The resource which the offerer wants.
	 */
	Resource take;
	/**
	 * The amount of the wanted resource.
	 */
	int take_amount;
	
	/**
	 * Constructor.
	 * @param offererID the ID of the offering player.
	 * @param give the resource which the offerer offers.
	 * @param give_amount the amount of the offered resource.
	 * @param take the resource which the offerer wants.
	 * @param take_amount the amount of the wanted resource.
	 */
	public TradeOffer(int offererID,Resource give,int give_amount,Resource take,int take_amount){
		init(offererID,give,give_amount,take,take_amount);
	}
	
	/**
	 * Constructor.
	 * @param ID the ID of the trade offer.
	 * @param offererID the ID of the offering player.
	 * @param give the resource which the offerer offers.
	 * @param give_amount the amount of the offered resource.
	 * @param take the resource which the offerer wants.
	 * @param take_amount the amount of the wanted resource.
	 */
	public TradeOffer(int ID,int offererID,Resource give,int give_amount,Resource take,int take_amount){
		super(ID);
		init(offererID,give,give_amount,take,take_amount);
	}
	
	/**
	 * The common part of the constructors.
	 * @param offererID the ID of the offering player.
	 * @param give the resource which the offerer offers.
	 * @param give_amount the amount of the offered resource.
	 * @param take the resource which the offerer wants.
	 * @param take_amount the amount of the wanted resource.
	 */
	private void init(int offererID,Resource give,int give_amount,Resource take,int take_amount) {
		this.offererID=offererID;
		this.give=give;
		this.give_amount=give_amount;
		this.take=take;
		this.take_amount=take_amount;
	}
	
	public int getID() {
		return super.getID();
	}
	
	/**
	 * Gets the offerer ID.
	 * @return the offerer ID.
	 */
	public int getOffererID() {
		return offererID;
	}
	
	/**
	 * Gets the given resource.
	 * @return the given resource.
	 */
	public Resource getGive() {
		return give;
	}
	
	/**
	 * Gets the amount of the given resource.
	 * @return the amount of the given resource.
	 */
	public int getGive_amount() {
		return give_amount;
	}
	
	/**
	 * Gets the wanted resource.
	 * @return the wanted resource.
	 */
	public Resource getTake() {
		return take;
	}
	
	/**
	 * Gets the amount of the wanted resource.
	 * @return the amount of the wnated resource.
	 */
	public int getTake_amount() {
		return take_amount;
	}
}
