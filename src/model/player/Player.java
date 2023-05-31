package model.player;

import model.card.ActionCard;
import model.card.Card;
import model.card.PropertyCard;
import model.card.PropertyWildcard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Player {

    private Bank bank = new Bank();

    private List<Card> handCards=new ArrayList<>();


    public Map<String, Property> getProperties() {
        return properties;
    }

    private Map<String,Property> properties = new HashMap();

    private String name;

    private Status status;

    private TurnInfo turnInfo;

    private GameRequest gameRequest;

    public TurnInfo getTurnInfo() {
        return turnInfo;
    }

    public void setTurnInfo(TurnInfo turnInfo) {
        this.turnInfo = turnInfo;
    }

    public void addProperty(PropertyCard selectedCard ) {
        Property property = properties.get(selectedCard.getName());
        if (property==null){
            property=new Property(selectedCard.getName(),selectedCard.getMaxSetNum() , 0);
            properties.put(selectedCard.getName(),property);
        }
        property.increaseSet(selectedCard);


    }


    public void acceptRequest(GameRequest gameRequest) {
        this.gameRequest = gameRequest;
    }

    public GameRequest getGameRequest() {
        return gameRequest;
    }

    public void reduceProperty(PropertyCard card) {
        Property property = properties.get(card.getName());
        property.reduceProperty(card);

    }

    public List<? extends Card> getPropertyCards() {
        ArrayList<Card> propertiesCard = new ArrayList<>();
        for (Property value : this.properties.values()) {
            propertiesCard.addAll(value.getPropertyCardList());
        }
        return propertiesCard;
    }


    public enum Status{
        playing,waiting,action, decidingWildCardChange, paying
    }

    public static class TurnInfo{
         public int cardAvailable=3;

         public List<PropertyWildcard> propertyWildcards = new ArrayList<>();

        public List<ActionCard> performingActionCards = new ArrayList<>();


        public void addPropertyWildCard(PropertyWildcard propertyWildcard) {
            propertyWildcards.add(propertyWildcard);
        }

        public boolean hasPropertyWildCard(){
            return !propertyWildcards.isEmpty();
        }

        public List<ActionCard> getPerformingActionCard() {
            return performingActionCards;
        }


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


    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Property getProperty(String name){
        return properties.get(name);
    }

    @Override
    public String toString() {
        return this.name;
    }
}
