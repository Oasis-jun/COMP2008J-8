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

    public List<Player> getPlayers() {
        return this.playerList;
    }

    public Player getPlayingUser(){
        return playerList.get(roundIdx);
    }

    public List<Card> getPlayPile() {
        return playPile;
    }

    public void startGame() {
        // send the card
        dealCardToPlayers();
        // turn to the first player
        turnToNextPlayer();

    }

    public void init(Integer playerNum) {
        // init the whole game
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

    private List<Card> initDrawPile() {
        List<Card> cards =CardFactory.createMonopolyDrawCards();
        Collections.shuffle(cards, new Random());
        return cards;
    }

    /**
     * send 5 cards to each player
     */
    public void dealCardToPlayers() {
        for (int i = 0; i < 5; i++) {
            for (Player player : playerList) {
                player.getHandCards().add(drawPile.remove(0));
            }
        }
    }

    // send num cards to one player
    public void drawCardsToPlayer(Player player, int num) {
        for (int i = 0; i < num; i++) {
            player.getHandCards().add(drawPile.remove(0));
        }
    }

    /**
     * when the player confirm, turn to next player, send him 2 cards
     */
    public void turnToNextPlayer() {
        // 将上一位进行的玩家的状态设置为 waiting
        if (roundIdx>-1){
            Player player = playerList.get(roundIdx );
            player.setStatus(Player.Status.waiting);
            player.notifyListeners();
        }
        roundIdx = (roundIdx+1)%playerList.size();
        Player player = playerList.get(roundIdx );
        player.setTurnInfo(new Player.TurnInfo());
        player.setStatus(Player.Status.playing);

        // 当玩家的牌为0时发5张牌
        if (player.getHandCards().isEmpty()) {
            for (int i = 0; i < 5; i++) {
                player.getHandCards().add(drawPile.remove(0));
            }
            // 否则每次发两张
        } else {
            for (int i = 0; i < 2; i++) {
                player.getHandCards().add(drawPile.remove(0));
            }
        }
        player.notifyListeners();
    }

    // turn to next player needed to pay
    public Player turnToNextPayingPlayer() {
        Player actionPlayer = getPlayingUser();
        if (payingPlayerList.isEmpty()){
            // 当action player没有action card的时候才会将其状态改为 playing
            if (actionPlayer.getTurnInfo().getPerformingActionCard().isEmpty()){
                actionPlayer.setStatus(Player.Status.playing);
            }
            this.status=Status.playing;
            return actionPlayer;
        }else {
            Player player = payingPlayerList.remove(0);
            player.setStatus(Player.Status.paying);
            this.status=Status.paying;
            return player;
        }


    }
    /**
     * player deposit card to bank
     * @param player
     */
    public void playerDeposit(Player player) {
        player.deposit();
    }


    /**
     * player use the property card
     * @param player
     */
    public void playerAddProperty(Player player) {
        player.addPropertyAction();

    }

    /**
     * player use action card
     * @param player
     */
    public void playerAction(Player player) {

        player.executeActionCard();
    }

    /**
     * player use rent card
     * @param player
     * @param rentCard
     */
    public void playerRentAction(Player player, RentCard rentCard) {
        //拿到除这个玩家外的所有玩家
        List<Player> targetPlayers = this.getPlayers().stream().filter(p -> p != player).collect(Collectors.toList());
        registerPayingPlayer(targetPlayers);
        setStatus(Status.paying);
        player.rent(rentCard, targetPlayers);
        CardApi.putCardToCenter(player,rentCard,this);
    }

    public void registerPayingPlayer(List<Player> targetPlayers) {
        for (Player targetPlayer : targetPlayers) {
            payingPlayerList.add(targetPlayer);
        }
    }

    /**
     * player abandon card
     * @param player
     */
    public void playerDiscardCard(Player player) {
        List<Card> selectedHandCard=player.discardCard();
        for (Card card : selectedHandCard) {
            CardApi.putCardToCenter(player, card, this);
        }
    }

    /**
     * player use the double rent card
     * @param player
     * @param availableRentCard
     * @param doubleTheRentCard
     */
    public void playerDoubleARentCard(Player player, List<Card> availableRentCard, ActionCard doubleTheRentCard) {
        player.doubleARentCard(availableRentCard,doubleTheRentCard);

        CardApi.putCardToCenter(player, doubleTheRentCard, this);


    }



    /**
     * player use Pass And Go card
     * @param player
     */
    public void playerPassGoAction(Player player) {
        drawCardsToPlayer(player,2);
    }

    /**
     * player send a paying request to one player
     * @param player
     * @param i
     * @param selectedPlayer
     */
    public void playerAskPayAction(Player player, int i, Player selectedPlayer) {
        PayingRequest payingRequest = new  PayingRequest(player,i,selectedPlayer);
        acceptRequest(payingRequest);
    }

    /**
     * player send a paying request to the other players
     * @param player
     * @param i
     * @param players
     */
    public void playerAskPayAction(Player player, int i, List<Player> players) {
        PayingRequest payingRequest = new PayingRequest(player,i,players);
        acceptRequest(payingRequest);
        setStatus(Status.paying);
    }

    // player give money property to others
    public void pay(Player player) {
        Card card = player.payRequest();
        if (card!=null){
            CardApi.putCardToCenter(player,card,this);
        }
    }

    // add the paying player to a list
    public void registerPayingPlayer(Player selectedPlayer) {
        this.payingPlayerList.add(selectedPlayer);
    }

    /**
     * controller accept the request and send the request to the paying player
     * @param payingRequest
     */
    public void acceptRequest(GameRequest payingRequest) {
        registerPayingPlayer(payingRequest.getTargetPlayers());
    }

    /**
     * for playing player, controller is playing
     * for paying player, controller is paying
     */
    public enum Status{
        playing, paying
    }
}
