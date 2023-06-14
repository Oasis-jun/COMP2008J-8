package model.player;

import model.card.Card;
import model.card.CardApi;
import model.card.PropertyCard;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class PayingRequest extends GameRequest {
    int bill;

    public PayingRequest(Player issuer, int bill,List<Player> payingPlayer) {
        super(issuer,payingPlayer);
        this.bill = bill;

    }

    public PayingRequest(Player issuer, int bill, Player selectedPlayer) {
        super(issuer,selectedPlayer);
        this.bill = bill;
    }

    public int getBill() {
        return bill;
    }

    public void execute(Player player){
        List<Card> bankCards = CardApi.getSelectedCard(player.getBank().getCardInBank());
        List<Card> propertyCards=CardApi.getSelectedCard(player.getPropertyCards());
        int worthSum = 0;
        for (Card card : bankCards) {
            worthSum = worthSum+card.getWorth();
        }
        for (Card card : propertyCards) {
            worthSum = worthSum+card.getWorth();
        }
        int bill = getBill();
        // 要么付够费用，要么全选了把剩下全付了
        if (worthSum>=bill||(bankCards.size()==player.getBank().getCardInBank().size()&&propertyCards.size()==player.getPropertyCards().size())){
            for (Card card : bankCards) {
                issuer.getBank().deposit(card);
                player.getBank().pay(card);
            }
            for (Card card : propertyCards) {
                issuer.addProperty((PropertyCard) card);
                player.reduceProperty((PropertyCard) card);
            }
            player.setStatus(Player.Status.waiting);
        }else {
            throw new RuntimeException("Selected Card cannot afford the bill");
        }
        issuer.notifyListeners();
    }




}
