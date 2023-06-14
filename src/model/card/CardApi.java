package model.card;

import controller.GameController;
import model.player.Player;

import java.util.ArrayList;
import java.util.List;

public class CardApi {


    //put the card into the play pile
    public static  void putCardToCenter(Player player, Card card, GameController controller) {
        player.getHandCards().remove(card);
        controller.getPlayPile().add(card);
    }

    //input a list of card and return the selected cards
    public static List<Card> getSelectedCard(List<? extends Card> cards) {
        ArrayList<Card> selectedCard = new ArrayList<>();
        for (Card selectablePropertyCard : cards) {
            if (selectablePropertyCard.isSelected()){
                selectedCard.add(selectablePropertyCard);
            }
        }
        return selectedCard;
    }

    public static List<Card> getRentCards(List<ActionCard> cards) {
        ArrayList<Card> availableRentCard = new ArrayList<>();
        for (ActionCard actionCard : cards) {
            if (actionCard instanceof RentCard){
                availableRentCard.add(actionCard);
            }
        }
        return availableRentCard;
    }
}
