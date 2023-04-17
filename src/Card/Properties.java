package Card;

import java.awt.Color;
import java.awt.Image;

public class Properties extends Card implements Colored{
	private String color;
	private Properties (String color,Image image) {	
		this.color=color;
		this.setImage(getImage());
	}
	@Override
	public Color getColor() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
