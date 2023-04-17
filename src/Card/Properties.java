package Card;

import java.awt.Color;
import java.awt.Image;

public class Properties extends Card implements Colored{
	private Color color;
	private int level;
	private int num = 0;
	public Properties(int value, Image image, Color color, int level) {	
		super(image,value);
		this.color=color;
		this.num = level;
		
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
