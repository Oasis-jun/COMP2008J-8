package Card;

import java.awt.Image;

public abstract class Card {
	private boolean visible;
	private Image image;
	private int value;
	
	
	public int getWorth() {
		return this.value;
	}
	
	public boolean getVisible() {
		return this.visible;
	}
	
	public Image getImage() {
		return this.image;
	}
	
	public void setImage(Image image) {
		this.image = image;
	}

}
