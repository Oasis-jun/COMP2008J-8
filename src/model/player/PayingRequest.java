package model.player;

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


}
