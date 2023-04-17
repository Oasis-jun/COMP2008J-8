package Card;

import java.awt.Image;

public  class ActionCard extends Card implements Namable{
	private String name;
	
	public ActionCard(Image image, int value, String name) {
		super(image,value);
		this.name = name;
		
	}

	public String getName() {
		return name;
	}
	
}
