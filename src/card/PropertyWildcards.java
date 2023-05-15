package card;

public class PropertyWildcards extends Card implements Colored{
	private String color;
	public PropertyWildcards(String color, String path, int worth) {
		super(path,worth);
		this.color = color;
		this.setImage(path);
		this.setWorth(worth);
	}
	
	public String getColor() {
		return this.color;
	}
	public void setColor(String color) {
		this.color = color;
	}
}
