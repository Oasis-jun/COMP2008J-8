package Card;

import java.awt.Color;
import java.awt.Image;

public class RentCard extends Card implements Colored{
	private Color color;
	private int value;
	public RentCard(Color color, int value, Image image) {
		super(image,value);
		this.color = color;
		
	}

	public Color getColor() {
		return color;
	}
	
	public int rent() {
		return value;
		
	}

}
