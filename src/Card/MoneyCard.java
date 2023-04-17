package Card;

import java.awt.Image;

public class MoneyCard extends Card{
	private int value;
	
	public MoneyCard(Image image, int value) {
		super(image,value);
		this.value = value;
			
		}
}