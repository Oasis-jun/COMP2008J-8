package Card;

public class PropertyWildcards extends Card implements Colored{
	private String color;
	private PropertyWildcards (String color,String image) {	
		this.color=color;
		this.setImage(GetImage.getImage(image));
	}

}
