package model.card;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;


public  class Card  implements Namable{
	private Image image;
	private int worth;

	private String name;

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	private boolean selected;

	private static Card cardBack;

	private static HashMap<String, Image> imageCache = new HashMap<>();

	public static Card getCardBack(){
		if (cardBack==null)
			cardBack=new Card("CardBack","img/CardBack.jpg",0);
		return cardBack;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}
	public Card(String name,String imagePath, int worth) {
		setImage(imagePath);
		this.name = name;
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
			Image cacheImage = imageCache.get(path);
			if (cacheImage==null){
				cacheImage = ImageIO.read(new File(path));
				imageCache.put(path,cacheImage);
			}

			this.image = cacheImage;
            } catch (IOException e) {
                System.out.println("加载图片失败");
		}
      }

	public boolean isSelected() {
		return selected;
	}
}
