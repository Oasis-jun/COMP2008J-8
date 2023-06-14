package model.player;

import model.Subject;
import model.card.*;

import java.util.*;
import java.util.stream.Collectors;


// player is the observed person, and panels in view layer are the observers of player
public class Player extends Subject {

    private Bank bank = new Bank();

    private List<Card> handCards=new ArrayList<>();

    private Map<String,Property> properties = new HashMap();

    private String name;

    private Status status;

    private TurnInfo turnInfo;

    private GameRequest gameRequest;



    public Player(String name) {
        this.name = name;
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
    public TurnInfo getTurnInfo() {
        return turnInfo;
    }
    public void setTurnInfo(TurnInfo turnInfo) {
        this.turnInfo = turnInfo;
    }

    public Map<String, Property> getProperties() {
        return properties;
    }


    // next part is the action of a player
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

    public boolean win() {
        int set=0;
        for (Property value : properties.values()) {
            set  = set + value.getSetNumber()/value.getMaxSetNum();
        }
        return set >2;


    }

    public void deposit() {
        ArrayList<Card> selectedCards = new ArrayList<>();
        for (Card handCard : handCards) {
            if (handCard.isSelected()){
                if (handCard instanceof PropertyCard){
                    throw new RuntimeException("Cannot put a property model.card into bank");
                }
                selectedCards.add(handCard);
            }
        }
        if (selectedCards.size()> getTurnInfo().cardAvailable){
            throw new RuntimeException("User has selected more than 3 three model.card in this turn");
        }else {
            getTurnInfo().cardAvailable= getTurnInfo().cardAvailable-selectedCards.size();
        }
        this.getBank().deposit(selectedCards);
        handCards.removeAll(selectedCards);
        notifyListeners();
    }

    public void addPropertyAction() {
        ArrayList<Card> selectedCards = new ArrayList<>();
        for (Card handCard : handCards) {
            if (handCard.isSelected()){
                if (handCard instanceof PropertyCard){
                    selectedCards.add(handCard);
                }else {
                    throw new RuntimeException("Selected cards contain non-property model.card");
                }
            }
        }
        if (selectedCards.size()> getTurnInfo().cardAvailable){
            throw new RuntimeException("User has selected more than 3 three model.card in this turn");
        }else {
            getTurnInfo().cardAvailable= getTurnInfo().cardAvailable-selectedCards.size();
        }
        Iterator<Card> iterator = selectedCards.iterator();
        while (iterator.hasNext()){
            Card selectedCard = iterator.next();
            if (selectedCard instanceof PropertyWildcard){
               getTurnInfo().addPropertyWildCard((PropertyWildcard)selectedCard);
                iterator.remove();
                handCards.remove(selectedCard);
                this.setStatus(Status.decidingWildCardChange);
            }
        }
        for (Card selectedCard : selectedCards) {
            selectedCard.setSelected(false);
            addProperty((PropertyCard)selectedCard);
            handCards.remove(selectedCard);
        }
        notifyListeners();
    }

    public void executeActionCard() {
        List<Card> handCards = getHandCards();
        ArrayList<ActionCard> selectedCards = new ArrayList<>();
        for (Card handCard : handCards) {
            if (handCard.isSelected()){
                if (handCard instanceof ActionCard){
                    selectedCards.add((ActionCard) handCard);
                }else {
                    throw new RuntimeException("Selected cards contain non-action model.card");
                }
            }
        }
        if (selectedCards.isEmpty()){
            throw new RuntimeException("No model.card is selected");
        }
        if (selectedCards.size()> getTurnInfo().cardAvailable){
            throw new RuntimeException("User has selected more than 3 three card in this turn");
        }
        getTurnInfo().getPerformingActionCard().addAll(selectedCards);
        setStatus(Player.Status.action);
        notifyListeners();
    }

    public Card payRequest() {
        GameRequest gameRequest = getGameRequest();
        Card justSayNoCard =payWithJustSayNo();
        if (justSayNoCard==null){
            // 具体处理玩家的交易逻辑
            gameRequest.execute(this);

        }
        handCards.remove(justSayNoCard);
        notifyListeners();
        return justSayNoCard;
    }

    private Card payWithJustSayNo() {
        List<Card> handCards = CardApi.getSelectedCard(getHandCards());
        List<Card> bankCards = CardApi.getSelectedCard(getBank().getCardInBank());
        List<Card> propertyCards=CardApi.getSelectedCard(getPropertyCards());
        // 判断有无选中just say no
        if (!handCards.isEmpty()){
            if (bankCards.size()+propertyCards.size()>0){
                throw new RuntimeException("Cannot select hand cards with table card ");
            }else if (handCards.size()>1){
                throw new RuntimeException("Cannot select more than one hand card");
            }else {
                Card card = handCards.get(0);
                if (card.getName().equals("Just Say No")){
                    setStatus(Player.Status.waiting);
                    return card;
                }else {
                    throw new RuntimeException(card.getName()+" Cannot be selected to pay the bill");
                }
            }
        }
        return null;
    }

    public void playerAskPayAction( int bill, Player selectedPlayer) {
         new PayingRequest(this,bill,selectedPlayer);
    }

    public void playerAskPayAction(Player player, int i, List<Player> selectedPlayers) {
        PayingRequest payingRequest = new PayingRequest(player,i,selectedPlayers);
    }
    public Card rent(RentCard rentCard,List<Player> players) {
        List<Card> selectedPropertyCards = CardApi.getSelectedCard(rentCard.getSelectablePropertyCards());
        if (selectedPropertyCards.size()==0){
            throw new RuntimeException("No property card is selected");
        }
        PropertyCard propertyCard = (PropertyCard) selectedPropertyCards.remove(0);
        if (selectedPropertyCards.size()>1){
            throw new RuntimeException("Only one property card can be selected");
        }
        Property property = getProperty(propertyCard.getName());
        if (property==null){
            throw new RuntimeException("You don't have the selected property card");
        }else {
            playerAskPayAction(this,property.getRentPrice()*rentCard.getRentFactor(),players);
            getTurnInfo().cardAvailable= getTurnInfo().cardAvailable-1;
            setStatus(Player.Status.action);
            getTurnInfo().getPerformingActionCard().remove(rentCard);
            notifyListeners();
        }
        return rentCard;
    }

    public List<Card> discardCard() {
        List<Card> selectedHandCard = CardApi.getSelectedCard(handCards);
        if (handCards.size()-selectedHandCard.size()<7){
            throw new RuntimeException("The number of the rest card cannot be bigger than 7");
        }
        notifyListeners();
        return selectedHandCard;
    }

    public void doubleARentCard(List<Card> availableRentCard, ActionCard doubleTheRentCard) {
        List<Card> selectedRentCard = CardApi.getSelectedCard(availableRentCard);
        if (selectedRentCard.isEmpty()){
            throw new RuntimeException("No rent is selected");
        }
        if (selectedRentCard.size()>1){
            throw new RuntimeException("Only one rent card can be selected");
        }
        RentCard rentCard = (RentCard) selectedRentCard.remove(0);
        rentCard.doubleTheRent();
        getTurnInfo().getPerformingActionCard().remove(doubleTheRentCard);
        setStatus(Player.Status.action);
        getTurnInfo().cardAvailable=getTurnInfo().cardAvailable-1;
        notifyListeners();
    }

// player has status, the same function as the status in controller
    public enum Status{
        playing,waiting,action, decidingWildCardChange, paying
    }

    // this class is used to show the important information of a player and is corresponding to the infopanel in the view layer
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
}
