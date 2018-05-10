package gameLogic;

//stores a trade offer
public class TradeOffer extends ObjectWithID{
	int offererID;
	Resource give;
	int give_amount;
	Resource take;
	int take_amount;
	
	public TradeOffer(int offererID,Resource give,int give_amount,Resource take,int take_amount){
		init(offererID,give,give_amount,take,take_amount);
	}
	
	public TradeOffer(int ID,int offererID,Resource give,int give_amount,Resource take,int take_amount){
		super(ID);
		init(offererID,give,give_amount,take,take_amount);
	}
	
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
	
	public int getOffererID() {
		return offererID;
	}
	
	public Resource getGive() {
		return give;
	}
	
	public int getGive_amount() {
		return give_amount;
	}
	
	public Resource getTake() {
		return take;
	}
	
	public int getTake_amount() {
		return take_amount;
	}
}
