package Card;

import java.awt.Image;

public  class ActionCard extends Card implements Namable{
	String name;
	int value;
	
	public ActionCard(Image image, int value, String name) {
		this.name = name;
		this.value = value;
		this.setImage(getImage());
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
