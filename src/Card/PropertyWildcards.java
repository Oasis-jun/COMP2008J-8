package Card;

import java.awt.Color;
import java.awt.Image;

public class PropertyWildcards extends Card implements Colored{
	private String color;
	private PropertyWildcards (String color,Image image) {	
		this.color=color;
		this.setImage(getImage());
	}
	@Override
	public Color getColor() {
		// TODO Auto-generated method stub
		return null;
	}

}
