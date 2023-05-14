package controller;

import model.*;

import java.util.ArrayList;
import java.util.List;

public class GameController {
    private int playerNum;
    private List<Player> playerList;
    public void init(Integer playerNum) {
        playerList = new ArrayList<Player>();
        this.playerNum=playerNum;
        for (int i = 0; i < playerNum; i++) {
            playerList.add(new Player("player"+(i+1)));
        }

    }

    public int getPlayerNum() {
        return this.playerNum;
    }

    public void dealCardToPlayers() {

    }

    public List<Player> getPlayers() {
        return this.playerList;
    }
}
