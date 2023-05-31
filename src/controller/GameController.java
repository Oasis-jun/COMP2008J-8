package controller;

import model.card.*;
import model.player.GameRequest;
import model.player.PayingRequest;
import model.player.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameController {
    private int playerNum;
    private List<Player> playerList;

    private List<Card> drawPile;


    private List<Card> playPile;

    private Integer roundIdx=-1;

    private List<Player> payingPlayerList = new ArrayList<>();
    private Player actionPlayer;

    private Status status;




    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status=status;
    }

    public void drawCardsToPlayer(Player player, int num) {
        for (int i = 0; i < num; i++) {
            player.getHandCards().add(drawPile.remove(0));
        }
    }

    public void setActionPlayer(Player player) {
        actionPlayer = player;
    }

    public enum Status{
        playing, paying
    }
    public void init(Integer playerNum) {
        drawPile = initDrawPile();
        playerList = new ArrayList<Player>();
        playPile = new ArrayList<>();
        this.playerNum = playerNum;
        for (int i = 0; i < playerNum; i++) {
            playerList.add(new Player("player" + (i + 1)));
        }
        this.status = Status.playing;
    }
    public List<Card> getPlayPile() {
        return playPile;
    }

    private List<Card> initDrawPile() {
        List<Card> cards = new ArrayList<>();
        cards.add(CardFactory.createTenMillionCard());
        cards.add(CardFactory.createLightBlueAndBrownCard());
        cards.add(CardFactory.createLightBlueAndRailroadCard());
        cards.add(CardFactory.createDarkBlueAndGreenCard());
        cards.add(CardFactory.createRailroadAndGreenCard());
        cards.add(CardFactory.createUtilityAndRailroadCard());
//        for (int i = 0; i < 200; i++) {
//            cards.add(CardFactory.createBrownCard());
//            cards.add(CardFactory.createGreenCard());
//            cards.add(CardFactory.createDealBreakerActionCard());
//            cards.add(CardFactory.createSlyDealActionCard());
////            cards.add(CardFactory.createSlyDealActionCard());
//        }
        for (int i = 0; i < 2; i++) {
            cards.add(CardFactory.createBrownCard());
            cards.add(CardFactory.createDarkBlueCard());
            cards.add(CardFactory.createFiveMillionCard());
            cards.add(CardFactory.createDarkBlueAndGreenRentCard());
            cards.add(CardFactory.createRedAndYellowRentCard());
            cards.add(CardFactory.createPurpleAndOrangeRentCard());
            cards.add(CardFactory.createLightBlueAndBrownRentCard());
            cards.add(CardFactory.createLightBlueAndBrownRentCard());
            cards.add(CardFactory.createRailroadAndUtilityRentCard());
            cards.add(CardFactory.createPurpleOrangeCard());
            cards.add(CardFactory.createRedYellowCard());
            cards.add(CardFactory.createMultiColorCard());
            cards.add(CardFactory.createDoubleTheRentActionCard());

        }

        for (int i = 0; i < 3; i++) {
            cards.add(CardFactory.createFourMillionCard());
            cards.add(CardFactory.createThreeMillionCard());
            cards.add(CardFactory.createGreenCard());
            cards.add(CardFactory.createLightBlueCard());
            cards.add(CardFactory.createOrangeCard());
            cards.add(CardFactory.createPurleCard());
            cards.add(CardFactory.createUtilityCard());
            cards.add(CardFactory.createYellowCard());
            cards.add(CardFactory.createWildRentCard());
            cards.add(CardFactory.createDebtCollectorActionCard());
            cards.add(CardFactory.createItsMyBirthdayActionCard());
            cards.add(CardFactory.createForceDealActionCard());
            cards.add(CardFactory.createHotelActionCard());
            cards.add(CardFactory.createHouseActionCard());
            cards.add(CardFactory.createJustSayNoActionCard());
            cards.add(CardFactory.createSlyDealActionCard());

        }
        for (int i = 0; i < 4; i++) {
            cards.add(CardFactory.createRailroadCard());
        }
        for (int i = 0; i < 5; i++) {
            cards.add(CardFactory.createTwoMillionCard());
        }
        for (int i = 0; i < 6; i++) {
            cards.add(CardFactory.createOneMillionCard());
        }
        for (int i = 0; i < 10; i++) {
            cards.add(CardFactory.createPassGoActionCard());
        }

        Collections.shuffle(cards);
        Collections.shuffle(cards);
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

    public void acceptRequest(GameRequest payingRequest) {
        Player issuer = payingRequest.getIssuer();
        actionPlayer = issuer;
        for (Player player : payingRequest.getTargetPlayers()) {
            player.acceptRequest(payingRequest);
            payingPlayerList.add(player);
        }
    }


    public void turnToNextPayingPlayer() {
        if (payingPlayerList.isEmpty()){
            if (actionPlayer.getTurnInfo().getPerformingActionCard().isEmpty()){
                actionPlayer.setStatus(Player.Status.playing);
                actionPlayer=null;
            }
            this.status=Status.playing;

        }else {
            Player player = payingPlayerList.remove(0);
            player.setStatus(Player.Status.paying);
            this.status=Status.paying;
        }

    }
}
