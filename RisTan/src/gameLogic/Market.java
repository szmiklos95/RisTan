package gameLogic;

import java.util.ArrayList;
import java.util.List;

//stores the trade offers
public class Market {
	private List<TradeOffer> tradeOffers;
	
	Market(){
		tradeOffers=new ArrayList<TradeOffer>();
	}
	
	void putOffer(TradeOffer offer) {
		tradeOffers.add(offer);
	}
	
	TradeOffer getOffer(int ID) {
		for(int i=0;i<tradeOffers.size();++i) {
			TradeOffer offer=tradeOffers.get(i);
			if(offer.getID()==ID) {
				return offer;
			}
		}
		return null;
	}
	
	public List<TradeOffer> getOffers(){
		return tradeOffers;
	}
	
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
