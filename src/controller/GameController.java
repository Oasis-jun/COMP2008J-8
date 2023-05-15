package controller;

import card.Card;
import card.MoneyCard;
import card.PropertyCard;
import model.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameController {
    private int playerNum;
    private List<Player> playerList;

    private List<Card> drawPile;

    private List<Card> playPile;

    private Integer roundIdx=-1;

    public void init(Integer playerNum) {
        drawPile = initDrawPile();
        playerList = new ArrayList<Player>();
        this.playerNum = playerNum;
        for (int i = 0; i < playerNum; i++) {
            playerList.add(new Player("player" + (i + 1)));
        }
    }

    private List<Card> initDrawPile() {
        List<Card> cards = new ArrayList<>();
        cards.add(new MoneyCard("img/10M.jpg", 10));
        for (int i = 0; i < 2; i++) {
            cards.add(new MoneyCard("img/5M.jpg", 5));
        }
        for (int i = 0; i < 3; i++) {
            cards.add(new MoneyCard("img/4M.jpg", 4));
        }
        for (int i = 0; i < 3; i++) {
            cards.add(new MoneyCard("img/3M.jpg", 3));
        }
        for (int i = 0; i < 5; i++) {
            cards.add(new MoneyCard("img/2M.jpg", 2));
        }
        for (int i = 0; i < 6; i++) {
            cards.add(new MoneyCard("img/1M.jpg", 1));
        }
        for (int i = 0; i < 2; i++) {
            cards.add(new PropertyCard("Brown", "img/Brown.jpg", 1, new int[]{1, 2}));
        }
        for (int i = 0; i < 2; i++) {
            cards.add(new PropertyCard("Blue", "img/Blue.jpg", 4, new int[]{3, 8}));
        }
        for (int i = 0; i < 3; i++) {
            cards.add(new PropertyCard("Green", "img/Green.jpg", 4, new int[]{2, 4, 7}));
        }
        for (int i = 0; i < 3; i++) {
            cards.add(new PropertyCard("Light Blue", "img/Light Blue.jpg", 1, new int[]{1, 2, 3}));
        }
        for (int i = 0; i < 3; i++) {
            cards.add(new PropertyCard("Orange", "img/Orange.jpg", 2, new int[]{1, 3, 5}));
        }
        for (int i = 0; i < 3; i++) {
            cards.add(new PropertyCard("Pink", "img/Pink.jpg", 2, new int[]{1, 2, 4}));
        }
        for (int i = 0; i < 4; i++) {
            cards.add(new PropertyCard("Railroad", "img/Railroad.jpg", 2, new int[]{1, 2, 3, 4}));
        }
        for (int i = 0; i < 3; i++) {
            cards.add(new PropertyCard("Utility", "img/Utility.jpg", 2, new int[]{1, 2}));
        }
        for (int i = 0; i < 3; i++) {
            cards.add(new PropertyCard("Yellow", "img/Yellow.jpg", 2, new int[]{1, 2}));
        }


        Collections.shuffle(cards);
        return cards;
    }

    public int getPlayerNum() {
        return this.playerNum;
    }

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

    public void turnToNextPlayer() {
        if (roundIdx>-1){
            Player player = playerList.get(roundIdx );
            player.setStatus(Player.Status.waiting);
        }
        roundIdx = (roundIdx+1)%playerList.size();
        Player player = playerList.get(roundIdx );
        player.setStatus(Player.Status.playing);
        player.setTurnInfo(new Player.TurnInfo());
        if (player.getHandCards().isEmpty()) {
            for (int i = 0; i < 5; i++) {
                player.getHandCards().add(drawPile.remove(0));
            }
        } else {
            for (int i = 0; i < 2; i++) {
                player.getHandCards().add(drawPile.remove(0));
            }
        }


    }
}
