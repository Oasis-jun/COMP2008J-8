package model.player;

import java.util.ArrayList;
import java.util.List;

public class GameRequest {

    public GameRequest(Player issuer, List<Player> targetPlayers) {
        this.issuer = issuer;
        this.targetPlayers = targetPlayers;
    }
    public GameRequest(Player issuer,  Player selectedPlayer) {
        this.issuer = issuer;
        ArrayList<Player> objects = new ArrayList<>();
        objects.add(selectedPlayer);
        this.targetPlayers =  objects;
    }

    public Player getIssuer() {
        return issuer;
    }

    public List<Player> getTargetPlayers() {
        return targetPlayers;
    }

    Player issuer;

    List<Player> targetPlayers;

}
