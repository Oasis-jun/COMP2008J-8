package model.card;


public class PropertyCard extends Card{


	private int maxSetNum;
	private int[] rentRule;
	public PropertyCard(String name, String path, int worth, int[] inputcost) {
		super(name,path,worth);
		this.setCost(inputcost);
		this.maxSetNum = inputcost.length;
	}



	public void setCost(int[] inputcost) {
		rentRule=inputcost;
	}

	public int getMaxSetNum() {
		return maxSetNum;
	}

	public int[] getRentRule() {
		return rentRule;
	}
}
