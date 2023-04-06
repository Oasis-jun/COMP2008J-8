package Card;

public abstract class Card {
	private boolean visible;
	private Image image;
	private int worth;
	
	
	public int getWorth() {
		return this.worth;
	}
	public boolean getVisible() {
		return this.visible;
	}
	public Image getImage() {
		return this.image;
	}
	
}
