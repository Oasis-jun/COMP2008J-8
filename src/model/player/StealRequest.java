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

    public void execute(Player player){
        Player issuer = this.getIssuer();
        Property targetPlayerProperty = this.getTargetPlayerProperty();
        if (!this.isFullSet()){
            issuer.addProperty(targetPlayerProperty.reduceProperty());
        }else {
            for (int i = 0; i < targetPlayerProperty.getMaxSetNum(); i++) {
                issuer.addProperty(targetPlayerProperty.reduceProperty());
            }
        }
        player.setStatus(Player.Status.waiting);
    };


}
