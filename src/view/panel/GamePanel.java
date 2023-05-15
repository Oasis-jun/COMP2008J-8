package view.panel;

import controller.GameController;
import model.Player;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GamePanel extends JPanel{

    private List<PlayerPanel> playerPanelList;
    public GamePanel(GameController controller) {
        setBorder(BorderFactory.createTitledBorder("Monopoly Deal"));
        playerPanelList = new ArrayList<>();
        List<Player> players=controller.getPlayers();
        for (Player player : players) {
            PlayerPanel playerPanel = new PlayerPanel(player);
            add(playerPanel);
            playerPanelList.add(playerPanel);
            playerPanel.addConfirmAction(e->{
                controller.turnToNextPlayer();
                updatePlayerPanelList();
                doLayout();
                repaint();
            });
        }


        add(new JLabel("---------------------------------------This is a message---------------------------------------"));
        JPanel horizontalBox = new  JPanel();
        horizontalBox.setMinimumSize(new Dimension(1700,30));
        horizontalBox.setPreferredSize(new Dimension(1700,30));
        Button startButton = new Button("Start");
        horizontalBox.add(startButton);
        add(horizontalBox);

        setLayout(new FlowLayout(FlowLayout.CENTER));
        setPreferredSize(new Dimension(1700,2500));
        startButton.addActionListener(e->{
//            this.remove(startButton);
            controller.dealCardToPlayers();
            controller.turnToNextPlayer();
            this.remove(horizontalBox);
            updatePlayerPanelList();
            doLayout();
            repaint();
        });
    }

    private void updatePlayerPanelList() {
        for (PlayerPanel playerPanel : playerPanelList) {
            playerPanel.updatePlayer();
        }

    }


}
