package Card;

public  class ActionCard extends Card implements Namable{
	private String name;
	public ActionCard(String path, int worth, String name) {
		this.setName(name);
        this.setImage(path);
		this.setWorth(worth);
		this.setVisible(false);
	}
	@Override
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
}
