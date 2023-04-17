package Object;

import Card.MoneyCard;

public class Bank {
	
	private int money;
	public Bank (int money) {
		this.money=money;
	}
	public int bankaccount=0;
	
	public void put(MoneyCard x) {
		bankaccount+=x.getValue();
	}
	public void pay(MoneyCard x) {
		bankaccount-=x.getValue();
		if (bankaccount<=0) {
			System.out.print("You have no money");
			}
		}	
	}
