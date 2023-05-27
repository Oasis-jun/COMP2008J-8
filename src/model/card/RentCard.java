package model.card;
import java.util.ArrayList;
import java.util.List;

public class RentCard extends ActionCard{
	private ArrayList<PropertyCard> selectablePropertyCards = new ArrayList<>();

	private int rentFactor = 1;
	public RentCard(String name, String path, int worth, PropertyCard... selectablePropertyCards) {
		super(name,path, worth);
		this.setSelectablePropertyCards(selectablePropertyCards);
	}
	public void setSelectablePropertyCards(PropertyCard[] selectablePropertyCards) {
		for(int i=0; i<selectablePropertyCards.length; i++) {
			this.selectablePropertyCards.add(selectablePropertyCards[i]);
		}
	}

	public List<PropertyCard> getSelectablePropertyCards() {
		return selectablePropertyCards;
	}

	public void doubleTheRent() {
		rentFactor=rentFactor*2;
	}

	public int getRentFactor() {
		return rentFactor;
	}
}
