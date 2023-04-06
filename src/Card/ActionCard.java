package Card;

public  class ActionCard extends Card implements Namable{
	private String name;
	public ActionCard(String mark, int worth) {
		this.name = name;
		this.image = GetImage.getImage(mark);
	}
	
}
