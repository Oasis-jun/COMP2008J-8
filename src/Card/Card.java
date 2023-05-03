package Card;
import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;


public abstract class Card {
	private boolean visible;
	private Image image;
	private int worth;
	
	
	public int getWorth() {
		return this.worth;
	}
	public void setWorth(int worth) {
		this.worth = worth;
	}
	public boolean getVisible() {
		return this.visible;
	}
	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	public Image getImage() {
		return this.image;
	}
	public void setImage(String path) {
		try {
            this.image = ImageIO.read(this.getClass().getResourceAsStream(path));
            } catch (IOException e) {
                System.out.println("加载图片失败");
              }
      }

}
