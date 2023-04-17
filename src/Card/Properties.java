package Card;

public class Properties extends Card implements Colored{
	private String color;
	private Properties (String color,String image) {	
		this.color=color;
		this.setImage(GetImage.getImage(image));
	}
	
}
