package gameLogic;

import java.util.ArrayList;
import java.util.List;

//stores the trade offers
/**
 * Stores the trade offers.
 * @author Andras
 *
 */
public class Market {
	/**
	 * The stored trade offers.
	 */
	private List<TradeOffer> tradeOffers;
	
	/**
	 * Constructor.
	 */
	Market(){
		tradeOffers=new ArrayList<TradeOffer>();
	}
	
	/**
	 * Puts an offer to the store.
	 * @param offer the new offer.
	 */
	void putOffer(TradeOffer offer) {
		tradeOffers.add(offer);
	}
	
	/**
	 * Gets an offer by ID.
	 * @param ID the ID of the offer.
	 * @return the offer with the ID or null if no such exists.
	 */
	TradeOffer getOffer(int ID) {
		for(int i=0;i<tradeOffers.size();++i) {
			TradeOffer offer=tradeOffers.get(i);
			if(offer.getID()==ID) {
				return offer;
			}
		}
		return null;
	}
	
	/**
	 * Gets the list of trade offers.
	 * @return the list of trade offers.
	 */
	public List<TradeOffer> getOffers(){
		return tradeOffers;
	}
	
	/**
	 * Removes an offer by ID.
	 * @param ID the ID of the offer.
	 */
	void removeOffer(int ID) {
		for(int i=0;i<tradeOffers.size();++i) {
			TradeOffer offer=tradeOffers.get(i);
			if(offer.getID()==ID) {
				tradeOffers.remove(i);
				break;
			}
		}
	}
}
