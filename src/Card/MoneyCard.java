package Card;

import java.awt.Image;

public class MoneyCard extends Card{
	int value;
	public MoneyCard(Image image,int value) {
			this.value = value;
			this.setImage(getImage());
		}
}