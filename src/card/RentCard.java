package card;
import java.util.ArrayList;

public class RentCard extends ActionCard{
	private ArrayList<String> color;
	public RentCard(String path, int worth, String name, String[] inputColor) {
		super(path, worth, name);
		this.setColor(inputColor);
	}
	public void setColor(String[] inputColor) {
		for(int i=0; i<inputColor.length; i++) {
			color.add(inputColor[i]);
		}
	}
}
