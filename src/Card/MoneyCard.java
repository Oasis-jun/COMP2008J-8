package Card;

public class MoneyCard extends Card{
	public MoneyCard(String path, int worth) {
		this.setImage(path);
		this.setWorth(worth);
		this.setVisible(false);
	}
}
