package Card;

public  class ActionCard extends Card implements Namable{
	String name;
	int worth;
	
	public ActionCard(String image, int worth, String name) {
		this.name = name;
		this.worth =worth;
		this.setImage(GetImage.getImage(image));
	}
	
}
