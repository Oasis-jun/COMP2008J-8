package Card;
import java.util.HashMap;

public class Properties extends Card implements Colored{
	private String color;
	private int setnum;
	private int maxsetnum;
	private HashMap<Integer,Integer> cost;
	public Properties(String color, String path, int worth, int[] inputcost) {
		this.color = color;
		this.setImage(path);
		this.setWorth(worth);
		this.setVisible(false);
		this.setCost(inputcost);
		this.maxsetnum = inputcost.length;
		this.setnum = 0;
	}
	public String getColor() {
		return this.color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public HashMap<Integer,Integer> getCost(){
		return this.cost;
	}
	public void setCost(int[] inputcost) {
		for(int i=1; i<inputcost.length+1; i++) {
			cost.put(i, inputcost[i-1]);
		}
	}
}
