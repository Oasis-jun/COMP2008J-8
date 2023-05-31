package model.player;

public class StealRequest extends GameRequest{
    public Property getTargetPlayerProperty() {
        return targetPlayerProperty;
    }

    public boolean isFullSet() {
        return fullSet;
    }

    private final Property targetPlayerProperty;
    private final boolean fullSet;

    public StealRequest(Player issuer, Player targetPlayer, Property targetPlayerProperty, boolean fullSet ) {
        super(issuer,targetPlayer);
        this.targetPlayerProperty = targetPlayerProperty;
        this.fullSet = fullSet;
    }


}
