package model.player;

import model.card.Card;

import java.util.ArrayList;
import java.util.List;

public class Bank {

    // the sum of the value of the cards in bank
    int balance;
    private List<Card> cardInBank=new ArrayList<>();

    public Bank() {

    }
    public List<Card> getCardInBank() {
        return cardInBank;
    }

    public int getBalance() {
        return balance;
    }
    public void deposit(Card handCard) {
        balance=handCard.getWorth()+balance;
        cardInBank.add(handCard);
    }


    public void deposit(List<Card> selectedCards) {
        for (Card selectedCard : selectedCards) {
            deposit(selectedCard);
        }
    }

    public void pay(Card card) {
        boolean remove = cardInBank.remove(card);
        if (remove){
            balance=balance-card.getWorth();
        }
    }
}
