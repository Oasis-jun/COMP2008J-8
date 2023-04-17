package Card;

import java.awt.Color;
import java.awt.Image;

public class RentCard extends Card implements Colored{
	String color;
	int value;
	public RentCard(String color,int value,Image image) {
		this.color = color;
		this.value = value;
		this.setImage(getImage());
	}
	@Override
	public Color getColor() {
		// TODO Auto-generated method stub
		return null;
	}
	

}
