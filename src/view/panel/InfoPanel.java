package view.panel;

import config.GameConfig;
import model.player.*;
import view.label.InfoLabel;

import javax.swing.*;
import java.awt.*;

public class InfoPanel extends JPanel{
    Player player;

    private final int width = 200;
    private final int height = 200;
    public InfoPanel(Player player) {
        this.player = player;
        setBorder(BorderFactory.createTitledBorder("Info"));
        setBackground(Color.WHITE);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setPreferredSize(new Dimension((int) (width* GameConfig.SIZE_FACTOR), (int) (height*GameConfig.SIZE_FACTOR)));
//        if (player.getStatus().equals(Player.Status.playing)){
//
//        }
    }

    public void update(Player player) {
    }

    public void updateInfo() {
        removeAll();
        if (Player.Status.playing.equals(player.getStatus())){
            Label label = new InfoLabel("Status: " + player.getStatus() + "(" + player.getTurnInfo().cardAvailable + "/" + "3)");

            add(label);
        }else if (Player.Status.paying.equals(player.getStatus())) {
            if (player.getGameRequest() instanceof PayingRequest){
                add(new InfoLabel("Status: "+player.getStatus()+"("+((PayingRequest)player.getGameRequest()).getBill()+"M)"));
            }else if (player.getGameRequest() instanceof SwapRequest){
                add(new InfoLabel("Status: "+player.getStatus()+"("+((SwapRequest)player.getGameRequest()).getTargetPlayerProperty()+")"));
            }else if ( player.getGameRequest() instanceof StealRequest){
                StealRequest stealRequest = (StealRequest) player.getGameRequest();
                if (stealRequest.isFullSet()){
                    add(new InfoLabel("Status: "+player.getStatus()+"(full "+((StealRequest)player.getGameRequest()).getTargetPlayerProperty()+")"));
                }else {
                    add(new InfoLabel("Status: "+player.getStatus()+"("+((StealRequest)player.getGameRequest()).getTargetPlayerProperty()+")"));
                }
            }
        }else{
            add(new InfoLabel("Status: "+player.getStatus()));
        }
        add(new InfoLabel("Balance: "+player.getBank().getBalance()));
        for (Property value : player.getProperties().values()) {
            if (value.getSetNumber()>0){
                add(new InfoLabel(value.getProperty()+": "+value.getSetNumber()+"/"+value.getMaxSetNum()+" "+value.getRentPrice()));
            }
        }
        this.doLayout();
        this.repaint();
    }
}
