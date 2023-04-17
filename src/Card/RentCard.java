package Card;

public class RentCard extends Card implements Colored{
	String color;
	int worth;
	public RentCard(String c,int w,String image) {
		color=c;
		worth = w;
		this.setImage(GetImage.getImage(image));
	}
	

}
