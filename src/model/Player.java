package model;

import card.Card;
import card.PropertyCard;

import java.util.ArrayList;
import java.util.List;

public class Player {

    private Bank bank = new Bank();

    private List<Card> handCards=new ArrayList<>();

    private List<PropertyCard> propertyCards=new ArrayList<>();

    private String name;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    private Status status;

    private TurnInfo turnInfo;

    public TurnInfo getTurnInfo() {
        return turnInfo;
    }

    public void setTurnInfo(TurnInfo turnInfo) {
        this.turnInfo = turnInfo;
    }


    public enum Status{
        playing,waiting,action,paying
    }

    public static class TurnInfo{
         public int cardAvailable=3;


    }

    public Bank getBank() {
        return bank;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }

    public List<Card> getHandCards() {
        return handCards;
    }

    public void setHandCards(List<Card> handCards) {
        this.handCards = handCards;
    }


    public Player(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<PropertyCard> getPropertyCards() {
        return propertyCards;
    }

    public void setPropertyCards(List<PropertyCard> propertyCards) {
        this.propertyCards = propertyCards;
    }
}
