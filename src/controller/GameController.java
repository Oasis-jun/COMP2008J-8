package controller;

import model.card.*;
import model.player.GameRequest;
import model.player.PayingRequest;
import model.player.Player;
import model.player.Property;

import javax.swing.*;
import java.util.*;
import java.util.stream.Collectors;
 
public class GameController {
    private List<Player> playerList;

    private List<Card> drawPile;


    private List<Card> playPile;

    private Integer roundIdx=-1;

    private List<Player> payingPlayerList = new ArrayList<>();

    private Status status;




    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status=status;
    }


    /**
     * Deal cards to the player
     * @param player
     * @param num
     */
    public void drawCardsToPlayer(Player player, int num) {
        for (int i = 0; i < num; i++) {
            player.getHandCards().add(drawPile.remove(0));
        }
    }

    /**
     *Players choose to put their cards in the bank
     * @param player
     */
    public void playerDeposit(Player player) {
        List<Card> handCards = player.getHandCards();
        ArrayList<Card> selectedCards = new ArrayList<>();
        for (Card handCard : handCards) {
            if (handCard.isSelected()){
                if (handCard instanceof PropertyCard){
                    throw new RuntimeException("Cannot put a property model.card into bank");
                }
                selectedCards.add(handCard);
            }
        }
        if (selectedCards.size()> player.getTurnInfo().cardAvailable){
            throw new RuntimeException("User has selected more than 3 three model.card in this turn");
        }else {
            player.getTurnInfo().cardAvailable= player.getTurnInfo().cardAvailable-selectedCards.size();
        }
        for (Card selectedCard : selectedCards) {
            selectedCard.setSelected(false);
            player.getBank().deposit(selectedCard);
            handCards.remove(selectedCard);
        }
    }

    /**
     * The player chooses the asset card in hand as the asset
     * @param player
     */
    public void playerAddProperty(Player player) {
        List<Card> handCards = player.getHandCards();
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
        if (selectedCards.size()> player.getTurnInfo().cardAvailable){
            throw new RuntimeException("User has selected more than 3 three model.card in this turn");
        }else {
            player.getTurnInfo().cardAvailable= player.getTurnInfo().cardAvailable-selectedCards.size();
        }
        Iterator<Card> iterator = selectedCards.iterator();
        while (iterator.hasNext()){
            Card selectedCard = iterator.next();
            if (selectedCard instanceof PropertyWildcard){
                player.getTurnInfo().addPropertyWildCard((PropertyWildcard)selectedCard);
                iterator.remove();
                handCards.remove(selectedCard);
            }
        }
        for (Card selectedCard : selectedCards) {
            selectedCard.setSelected(false);
            player.addProperty((PropertyCard)selectedCard);
            handCards.remove(selectedCard);
        }
    }


    /**
     * The player executes the action card
     * @param player
     */
    public void playerAction(Player player) {
        List<Card> handCards = player.getHandCards();
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
        if (selectedCards.size()> player.getTurnInfo().cardAvailable){
            throw new RuntimeException("User has selected more than 3 three card in this turn");
        }
        player.getTurnInfo().getPerformingActionCard().addAll(selectedCards);
        this.setStatus(Status.paying);
        player.setStatus(Player.Status.action);
    }

    /**
     * Determine if the player is using Just Say No card
     * @param player
     * @return
     */
    public boolean payWithJustSayNo(Player player) {
        List<Card> handCards = CardApi.getSelectedCard(player.getHandCards());
        List<Card> bankCards = CardApi.getSelectedCard(player.getBank().getCardInBank());
        List<Card> propertyCards=CardApi.getSelectedCard(player.getPropertyCards());
        //Check whether it is selected. just say no
        if (!handCards.isEmpty()){
            if (bankCards.size()+propertyCards.size()>0){
                throw new RuntimeException("Cannot select hand cards with table card ");
            }else if (handCards.size()>1){
                throw new RuntimeException("Cannot select more than one hand card");
            }else {
                Card card = handCards.get(0);
                if (card.getName().equals("Just Say No")){
                    player.setStatus(Player.Status.waiting);
                    CardApi.putCardToCenter(player,card,this);
                    return true;
                }else {
                    throw new RuntimeException(card.getName()+" Cannot be selected to pay the bill");
                }
            }
        }
        return false;
    }


    /**
     * The player uses the rent card
     * @param player
     * @param rentCard
     */
    public void playerRentAction(Player player, RentCard rentCard) {
        List<Card> selectedPropertyCards = CardApi.getSelectedCard(rentCard.getSelectablePropertyCards());
        if (selectedPropertyCards.size()==0){
            throw new RuntimeException("No property model.card is selected");
        }
        PropertyCard propertyCard = (PropertyCard) selectedPropertyCards.remove(0);
        if (selectedPropertyCards.size()>1){
            throw new RuntimeException("Only one property model.card can be selected");
        }
        Property property = player.getProperty(propertyCard.getName());
        if (property==null){
            throw new RuntimeException("You don't have the selected property model.card");
        }else {
            List<Player> payingPlayers = this.getPlayers().stream().filter(p -> p != player).collect(Collectors.toList());
            playerAskPayAction(player,property.getRentPrice()*rentCard.getRentFactor(),payingPlayers);
            CardApi.putCardToCenter(player, rentCard, this);
            player.getTurnInfo().cardAvailable= player.getTurnInfo().cardAvailable-1;
            player.setStatus(Player.Status.action);
            player.getTurnInfo().getPerformingActionCard().remove(rentCard);

        }
    }

    /**
     * Player discard card
     * @param player
     */
    public void playerDiscardCard(Player player) {

        List<Card> handCards = player.getHandCards();
        List<Card> selectedHandCard = CardApi.getSelectedCard(handCards);
        if (handCards.size()-selectedHandCard.size()<7){
            throw new RuntimeException("The number of the rest card cannot be less than 7");
        }else {
            for (Card card : selectedHandCard) {
                CardApi.putCardToCenter(player, card, this);
            }
        }
    }



    public void startGame() {
        // Deal cards
        dealCardToPlayers();
        // Rotation Here rotation goes to Player 1 to play the game
        turnToNextPlayer();

    }

    public void init(Integer playerNum) {
        drawPile = initDrawPile();
        playerList = new ArrayList<>();
        playPile = new ArrayList<>();
        for (int i = 0; i < playerNum; i++) {
            Player player = new Player("player" + (i + 1));
            player.setStatus(Player.Status.waiting);
            playerList.add(player);
        }
        this.status = Status.playing;
    }
    public List<Card> getPlayPile() {
        return playPile;
    }

    private List<Card> initDrawPile() {
        List<Card> cards =CardFactory.createMonopolyDrawCards();
        Collections.shuffle(cards, new Random());
        return cards;
    }

    /**
     * Deal 5 cards to each player
     */
    public void dealCardToPlayers() {
        for (int i = 0; i < 5; i++) {
            for (Player player : playerList) {
                player.getHandCards().add(drawPile.remove(0));
            }
        }
    }

    public List<Player> getPlayers() {
        return this.playerList;
    }


    /**
     * After confirming, the player selects the next player and deals 2 cards to the next player
     */
    public void turnToNextPlayer() {
        // Set the status of the previous player to waiting
        if (roundIdx>-1){
            Player player = playerList.get(roundIdx );
            player.setStatus(Player.Status.waiting);
        }
        roundIdx = (roundIdx+1)%playerList.size();
        Player player = playerList.get(roundIdx );
        player.setStatus(Player.Status.playing);
        player.setTurnInfo(new Player.TurnInfo());
        // 5 cards are dealt when the player's card is 0
        if (player.getHandCards().isEmpty()) {
            for (int i = 0; i < 5; i++) {
                player.getHandCards().add(drawPile.remove(0));
            }
            // Otherwise, two at a time
        } else {
            for (int i = 0; i < 2; i++) {
                player.getHandCards().add(drawPile.remove(0));
            }
        }
    }


    /**
     * Accept the request from the player and send the request to the specified player
     * @param payingRequest
     */
    public void acceptRequest(GameRequest payingRequest) {
        for (Player player : payingRequest.getTargetPlayers()) {
            player.acceptRequest(payingRequest);
            payingPlayerList.add(player);
        }
    }

    public Player getPlayingUser(){
        return playerList.get(roundIdx);
    }

    public void turnToNextPayingPlayer() {
        Player actionPlayer = getPlayingUser();
        if (payingPlayerList.isEmpty()){
                //The action player status is changed to playing only when the action Player does not have an action card
              if (actionPlayer.getTurnInfo().getPerformingActionCard().isEmpty()){
                actionPlayer.setStatus(Player.Status.playing);
            }
            this.status=Status.playing;

        }else {
            Player player = payingPlayerList.remove(0);
            player.setStatus(Player.Status.paying);
            this.status=Status.paying;
        }

    }
}
