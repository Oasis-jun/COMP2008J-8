package Card;

public class MoneyCard extends Card{
	int worth;
	public MoneyCard(String image,int worth) {
			this.worth=worth;
			this.setImage(GetImage.getImage(image));
		}
}