package model.player;

public class SwapRequest extends GameRequest {


    public Property getIssuerProperty() {
        return issuerProperty;
    }

    public void setIssuerProperty(Property issuerProperty) {
        this.issuerProperty = issuerProperty;
    }

    public Property getTargetPlayerProperty() {
        return targetPlayerProperty;
    }

    public void setTargetPlayerProperty(Property targetPlayerProperty) {
        this.targetPlayerProperty = targetPlayerProperty;
    }

    Property issuerProperty;

    Property targetPlayerProperty;

    public SwapRequest(Player issuer, Property issuerProperty, Player targetPlayer, Property targetPlayerProperty) {
        super(issuer,targetPlayer);
        this.issuerProperty = issuerProperty;
        this.targetPlayerProperty = targetPlayerProperty;
    }

    public void execute(Player player){
        Player issuer = this.getIssuer();
        Property issuerProperty = this.getIssuerProperty();
        Property targetPlayerProperty = this.getTargetPlayerProperty();
        issuer.addProperty(targetPlayerProperty.reduceProperty());
        player.addProperty(issuerProperty.reduceProperty());
        player.setStatus(Player.Status.waiting);
    }
}
