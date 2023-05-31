package view.panel;

import model.player.*;

import javax.swing.*;
import java.awt.*;

public class InfoPanel extends JPanel{
    Player player;
    public InfoPanel(Player player) {
        this.player = player;
        setBorder(BorderFactory.createTitledBorder("Info"));
        setBackground(Color.WHITE);
        setMaximumSize(new Dimension(200, 200));
        setPreferredSize(new Dimension(200, 200));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
//        if (player.getStatus().equals(Player.Status.playing)){
//
//        }
    }

    public void update(Player player) {
    }

    public void updateInfo() {
        removeAll();
        if (Player.Status.playing.equals(player.getStatus())){
            add(new Label("Status: "+player.getStatus()+"("+player.getTurnInfo().cardAvailable+"/"+"3)"));
        }else if (Player.Status.paying.equals(player.getStatus())) {
            if (player.getGameRequest() instanceof PayingRequest){
                add(new Label("Status: "+player.getStatus()+"("+((PayingRequest)player.getGameRequest()).getBill()+"M)"));
            }else if (player.getGameRequest() instanceof SwapRequest){
                add(new Label("Status: "+player.getStatus()+"("+((SwapRequest)player.getGameRequest()).getTargetPlayerProperty()+")"));
            }else if ( player.getGameRequest() instanceof StealRequest){
                StealRequest stealRequest = (StealRequest) player.getGameRequest();
                if (stealRequest.isFullSet()){
                    add(new Label("Status: "+player.getStatus()+"(full "+((StealRequest)player.getGameRequest()).getTargetPlayerProperty()+")"));
                }else {
                    add(new Label("Status: "+player.getStatus()+"("+((StealRequest)player.getGameRequest()).getTargetPlayerProperty()+")"));
                }
            }
        }else{
            add(new Label("Status: "+player.getStatus()));
        }
        add(new Label("Balance: "+player.getBank().getBalance()));
        for (Property value : player.getProperties().values()) {
            if (value.getSetNumber()>0){
                add(new Label(value.getProperty()+": "+value.getSetNumber()+"/"+value.getMaxSetNum()+" "+value.getRentPrice()));
            }
        }
        this.doLayout();
        this.repaint();
    }
}
