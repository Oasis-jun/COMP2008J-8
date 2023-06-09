package test;


import controller.GameController;
import model.card.Card;
import model.card.MoneyCard;
import model.card.PropertyCard;
import model.player.Player;
import org.junit.Assert;
import view.panel.GamePanel;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GameControllerTest {


    @Test
    public void test(){
        GameController gameController = new GameController();
        gameController.init(5);
        gameController.startGame();

        Assert.assertEquals(gameController.getPlayers().size(),5);

        List<Player> players = gameController.getPlayers();

        
        
        Assert.assertEquals(players.get(0).getStatus(), Player.Status.playing);
        Assert.assertEquals(players.get(0).getHandCards().size(), 7);

        for (int i = 1; i < 5; i++) {
            Assert.assertEquals(players.get(i).getStatus(), Player.Status.waiting);
            Assert.assertEquals(players.get(i).getHandCards().size(), 5);
        }

//        gameController.playerAskPayAction();



//        gameController.turnToNextPlayer();
    }
}