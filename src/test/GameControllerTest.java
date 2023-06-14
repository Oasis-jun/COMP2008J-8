package test;


import controller.GameController;
import model.card.Card;
import model.card.CardFactory;
import model.card.MoneyCard;
import model.card.PropertyCard;
import model.player.PayingRequest;
import model.player.Player;
import org.junit.Assert;
import view.panel.GamePanel;
import org.junit.Test;

import java.util.Iterator;
import java.util.List;

public class GameControllerTest {


    @Test
    public void test(){
        GameController gameController = new GameController();
        gameController.init(5);
        gameController.startGame();
        // judge if the number of the players is correct
        Assert.assertEquals(gameController.getPlayers().size(),5);

        List<Player> players = gameController.getPlayers();
        // Determine if the first player is playing and the other players are waiting
        // Determine if the first player has 7 cards and the other player has 5
        Assert.assertEquals(players.get(0).getStatus(), Player.Status.playing);
        Assert.assertEquals(players.get(0).getHandCards().size(), 7);

        for (int i = 1; i < 5; i++) {
            Assert.assertEquals(players.get(i).getStatus(), Player.Status.waiting);
            Assert.assertEquals(players.get(i).getHandCards().size(), 5);
        }
        Player playingUser = gameController.getPlayingUser();
        Card oneMillionCard = CardFactory.createOneMillionCard();
        oneMillionCard.setSelected(true);
        playingUser.getHandCards().add(oneMillionCard);
        gameController.playerDeposit(playingUser);
        Assert.assertTrue(playingUser.getBank().getCardInBank().contains(oneMillionCard));
        oneMillionCard.setSelected(false);
        oneMillionCard = CardFactory.createOneMillionCard();
        playingUser.getHandCards().add(oneMillionCard);
        oneMillionCard.setSelected(true);
        Assert.assertThrows("Selected cards contain non-property model.card",RuntimeException.class,()->gameController.playerAddProperty(playingUser));
//        gameController.turnToNextPlayer();
    }


    @Test
    public void testUseMoneyCard(){
        GameController gameController = new GameController();
        gameController.init(5);
        gameController.startGame();
        Player playingUser = gameController.getPlayingUser();
        Card oneMillionCard = CardFactory.createOneMillionCard();
        oneMillionCard.setSelected(true);
        playingUser.getHandCards().add(oneMillionCard);
        gameController.playerDeposit(playingUser);
        Assert.assertTrue(playingUser.getBank().getCardInBank().contains(oneMillionCard));
        oneMillionCard.setSelected(false);

        oneMillionCard = CardFactory.createOneMillionCard();
        playingUser.getHandCards().add(oneMillionCard);
        oneMillionCard.setSelected(true);
        Assert.assertThrows("Selected cards contain non-property model.card",RuntimeException.class,()->gameController.playerAddProperty(playingUser));
        oneMillionCard.setSelected(false);


        oneMillionCard = CardFactory.createOneMillionCard();
        playingUser.getHandCards().add(oneMillionCard);
        oneMillionCard.setSelected(true);
        Assert.assertThrows(" Selected cards contain non-action model.card",RuntimeException.class,()->gameController.playerAction(playingUser));
        oneMillionCard.setSelected(false);

    }
    
    @Test
    public void testUsePropertyCard(){
        GameController gameController = new GameController();
        gameController.init(5);
        gameController.startGame();
        Player playingUser = gameController.getPlayingUser();
        Card greenCard = CardFactory.createGreenCard();
        greenCard.setSelected(true);
        playingUser.getHandCards().add(greenCard);
        Assert.assertThrows("Cannot put a property model.card into bank",RuntimeException.class,()->gameController.playerDeposit(playingUser));
        Assert.assertFalse(playingUser.getBank().getCardInBank().contains(greenCard));
        Assert.assertFalse(playingUser.getPropertyCards().contains(greenCard));
        greenCard.setSelected(false);

        greenCard = CardFactory.createGreenCard();
        playingUser.getHandCards().add(greenCard);
        greenCard.setSelected(true);
        gameController.playerAddProperty(playingUser);
        Assert.assertTrue(playingUser.getPropertyCards().contains(greenCard));
        greenCard.setSelected(false);


        greenCard = CardFactory.createGreenCard();
        playingUser.getHandCards().add(greenCard);
        greenCard.setSelected(true);
        Assert.assertThrows(" Selected cards contain non-action model.card",RuntimeException.class,()->gameController.playerAction(playingUser));
        greenCard.setSelected(false);
        Assert.assertFalse(playingUser.getPropertyCards().contains(greenCard));
    }


    @Test
    public void testUseActionCard(){
        GameController gameController = new GameController();
        gameController.init(5);
        gameController.startGame();
        Player playingUser = gameController.getPlayingUser();
        Card hotelCard = CardFactory.createHotelActionCard();
        hotelCard.setSelected(true);
        playingUser.getHandCards().add(hotelCard);
        gameController.playerDeposit(playingUser);
        Assert.assertTrue(playingUser.getBank().getCardInBank().contains(hotelCard));
        hotelCard.setSelected(false);

        hotelCard = CardFactory.createHotelActionCard();
        playingUser.getHandCards().add(hotelCard);
        hotelCard.setSelected(true);
        Assert.assertThrows("Selected cards contain non-property model.card",RuntimeException.class,()->gameController.playerAddProperty(playingUser));
        Assert.assertFalse(playingUser.getPropertyCards().contains(hotelCard));
        hotelCard.setSelected(false);


        hotelCard = CardFactory.createHotelActionCard();
        playingUser.getHandCards().add(hotelCard);
        hotelCard.setSelected(true);
        gameController.playerAction(playingUser);
        hotelCard.setSelected(false);
    }


    @Test
    public void testUsePay(){
        GameController gameController = new GameController();
        gameController.init(5);
        gameController.startGame();
        Player playingUser = gameController.getPlayingUser();
        PayingRequest payingRequest = new PayingRequest(playingUser, 5, gameController.getPlayers().stream().filter(player -> player != playingUser).toList());
        gameController.acceptRequest(payingRequest);
        gameController.dealCardToPlayers();
    }
}
