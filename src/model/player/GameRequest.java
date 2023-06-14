package model.player;

import java.util.ArrayList;
import java.util.List;

public abstract class GameRequest {

    Player issuer;

    List<Player> targetPlayers;
    public GameRequest(Player issuer, List<Player> targetPlayers) {
        this.issuer = issuer;
        this.targetPlayers = targetPlayers;
        for (Player targetPlayer : targetPlayers) {
            targetPlayer.acceptRequest(this);
        }

    }
    public GameRequest(Player issuer,  Player selectedPlayer) {
        this.issuer = issuer;
        ArrayList<Player> objects = new ArrayList<>();
        objects.add(selectedPlayer);
        this.targetPlayers =  objects;
        selectedPlayer.acceptRequest(this);
    }

    public Player getIssuer() {
        return issuer;
    }

    public List<Player> getTargetPlayers() {
        return targetPlayers;
    }


    public void execute(Player player){
        player.setStatus(Player.Status.waiting);
    };

}
