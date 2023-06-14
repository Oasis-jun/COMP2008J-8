package view.panel;

import config.GameConfig;
import controller.GameController;
import model.player.Player;
import view.GameBoardFrame;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GamePanel extends JLabel{

    private List<PlayerPanel> playerPanelList;

    private GameController controller;

    private GameBoardFrame parent;

    private int width=1700;
    private int height = 2500;

    /**
     * 游戏的主要内容为三大部分， 玩家面板，消息面板，开始按钮面板
     * @param controller
     * @param gameBoardFrame
     */
    public GamePanel(GameController controller, GameBoardFrame gameBoardFrame) {
        parent= gameBoardFrame;
        this.controller=controller;
        setBorder(BorderFactory.createTitledBorder("Monopoly Deal"));
        playerPanelList = new ArrayList<>();
        List<Player> players=controller.getPlayers();
        // 根据玩家信息初始化玩家的面板
        for (Player player : players) {
            PlayerPanel playerPanel = new PlayerPanel(player,controller,this);
            add(playerPanel);
            playerPanelList.add(playerPanel);

        }


        // 开始按钮面板
        JPanel horizontalBox = new  JPanel();

        Button startButton = new Button("Start");
        horizontalBox.add(startButton);
        add(horizontalBox);
        horizontalBox.setPreferredSize(new Dimension((int) (width* GameConfig.SIZE_FACTOR), (int) (height*GameConfig.SIZE_FACTOR)));
        setLayout(new FlowLayout(FlowLayout.CENTER));
        setPreferredSize(new Dimension((int) (width* GameConfig.SIZE_FACTOR), (int) (height*GameConfig.SIZE_FACTOR)));

        startButton.addActionListener(e->{

            controller.startGame();
            this.remove(horizontalBox);
            // 根据游戏的状态更新每一个玩家的游戏板块
            updatePlayerPanelList();
            doLayout();
            repaint();
        });
    }

    void updatePlayerPanelList() {
        // 根据游戏的状态更新每一个玩家的游戏板块
        for (PlayerPanel playerPanel : playerPanelList) {
            playerPanel.updatePlayer();
        }
    }


    public void playerActionPerforming() {
        controller.turnToNextPayingPlayer();
        updatePlayerPanelList();
    }


    public void clearGame() {
        parent.clearGame();
    }
}
