package model.card;

import java.util.ArrayList;
import java.util.List;

public class PropertyWildcard extends PropertyCard {
	private String type;

	public List<Card> getCardsToChange() {
		return cardsToChange;
	}

	private List<Card> cardsToChange;

	public PropertyWildcard(String type, String path, int worth,Card ...cards) {
		super(type,path,worth,new int[]{});
		cardsToChange = new ArrayList<>();
		for (Card card : cards) {
			cardsToChange.add(card);
		}
	}

}
