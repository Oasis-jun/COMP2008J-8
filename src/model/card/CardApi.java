package model.card;

import controller.GameController;
import model.player.Player;

import java.util.ArrayList;
import java.util.List;

public class CardApi {


    public static  void putCardToCenter(Player player, Card card, GameController controller) {
        player.getHandCards().remove(card);
        controller.getPlayPile().add(card);
    }

    public static List<Card> getSelectedCard(List<? extends Card> cards) {
        ArrayList<Card> selectedCard = new ArrayList<>();
        for (Card selectablePropertyCard : cards) {
            if (selectablePropertyCard.isSelected()){
                selectedCard.add(selectablePropertyCard);
            }
        }
        return selectedCard;
    }
}
