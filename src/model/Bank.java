package model;

import card.Card;

import java.util.ArrayList;
import java.util.List;

public class Bank {

    int balance;
    private List<Card> cardInBank=new ArrayList<>();
    public void deposit(Card handCard) {
        cardInBank.add(handCard);
    }

    public Bank() {

    }

    public List<Card> getCardInBank() {
        return cardInBank;
    }

    public int getBalance() {
        return balance;
    }
}
