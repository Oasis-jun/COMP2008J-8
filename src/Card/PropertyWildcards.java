package Card;

public class PropertyWildcards extends Card implements Colored{
	private String color;
	public PropertyWildcards(String color, String path, int worth) {
		this.color = color;
		this.setImage(path);
		this.setWorth(worth);
		this.setVisible(false);
	}
	
	public String getColor() {
		return this.color;
	}
	public void setColor(String color) {
		this.color = color;
	}
}
