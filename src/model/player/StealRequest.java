package model.player;

public class StealRequest extends GameRequest{

    private final Property targetPlayerProperty;
    private final boolean fullSet;

    public StealRequest(Player issuer, Player targetPlayer, Property targetPlayerProperty, boolean fullSet ) {
        super(issuer,targetPlayer);
        this.targetPlayerProperty = targetPlayerProperty;
        this.fullSet = fullSet;
    }

    public Property getTargetPlayerProperty() {
        return targetPlayerProperty;
    }

    public boolean isFullSet() {
        return fullSet;
    }
    public void execute(Player player){
        Property targetPlayerProperty = this.getTargetPlayerProperty();
        if (!this.isFullSet()){
            issuer.addProperty(targetPlayerProperty.reduceProperty());
        }else {
            for (int i = 0; i < targetPlayerProperty.getMaxSetNum(); i++) {
                issuer.addProperty(targetPlayerProperty.reduceProperty());
            }
        }
        player.setStatus(Player.Status.waiting);
        issuer.notifyListeners();
    };


}
