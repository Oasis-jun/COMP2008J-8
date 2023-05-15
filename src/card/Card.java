package card;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


public abstract class Card {
	private Image image;
	private int worth;

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	private boolean selected;

	private static Card cardBack;

	public static Card getCardBack(){
		if (cardBack==null)
			cardBack=new Card("img/CardBack.jpg",0){
		};
		return cardBack;
	}

	public Card(String imagePath, int worth) {
		setImage(imagePath);
		this.worth = worth;
	}

	public int getWorth() {
		return this.worth;
	}
	public void setWorth(int worth) {
		this.worth = worth;
	}

	public Image getImage() {
		return this.image;
	}
	public void setImage(String path) {
		try {
            this.image = ImageIO.read(new File(path));
            } catch (IOException e) {
                System.out.println("加载图片失败");
              }
      }

	public boolean isSelected() {
		return selected;
	}
}
