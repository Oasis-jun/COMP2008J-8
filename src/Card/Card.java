package Card;

import java.awt.Image;

public abstract class Card {

	private boolean visible;
	private Image image;
	private int value;
	private int owner;
	
	public Card(Image image, int value) {
		this.image = image;
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}
	
	public boolean getVisible() {
		return visible;
	}
	
	public Image getImage() {
		return image;
	}

}
