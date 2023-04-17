package Card;

import java.awt.Color;
import java.awt.Image;

public class PropertyWildcards extends Card implements Colored{
	private Color color;
	private int level;
	private int num = 0;
	private PropertyWildcards (Color color, int value, Image image, int level) {	
		super(image,value);
		this.color = color;
		this.level = level;
	}

	public Color getColor() {
		return color;
	}

	public void add() {
		num++;
	}
	
	public boolean complete() {
		if(num == level) {
			return true;
		}else {
			return false;
		}
	}
	
}
