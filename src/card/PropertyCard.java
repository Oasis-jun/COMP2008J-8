package card;
import java.util.HashMap;


public class PropertyCard extends Card{


	private String type;
	private int setnum;
	private int maxsetnum;
	private int[] rentRule;
	public PropertyCard(String type, String path, int worth, int[] inputcost) {
		super(path,worth);
		this.type = type;
		this.setCost(inputcost);
		this.maxsetnum = inputcost.length;
		this.setnum = 0;
	}
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}


	public void setCost(int[] inputcost) {
		rentRule=inputcost;
	}
}
