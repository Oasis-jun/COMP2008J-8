package view.panel;

import config.GameConfig;
import controller.GameController;
import model.player.Player;
import view.GameBoardFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

public class GamePanel extends JLabel{

    private List<PlayerPanel> playerPanelList;

    private GameController controller;

    private GameBoardFrame parent;

    private int width=1700;
    private int height = 2500;

    /**
     * The main content of the game is three parts, the player panel, the message panel, and the start button panel
     * @param controller
     * @param gameBoardFrame
     */
    public GamePanel(GameController controller, GameBoardFrame gameBoardFrame) {
        this.parent= gameBoardFrame;
        this.controller=controller;
        setBorder(BorderFactory.createTitledBorder("Monopoly Deal"));
        playerPanelList = new ArrayList<>();
        List<Player> players=controller.getPlayers();
        // Initializes the player's panel based on player information
        for (Player player : players) {
            PlayerPanel playerPanel = new PlayerPanel(player,controller,this);
            add(playerPanel);
            playerPanelList.add(playerPanel);

        }


        // Start button panel
        JPanel horizontalBox = new  JPanel();

        Button startButton = new Button("Start");
        horizontalBox.add(startButton);
        add(horizontalBox);
        horizontalBox.setPreferredSize(new Dimension((int) (width* GameConfig.SIZE_FACTOR), (int) (height*GameConfig.SIZE_FACTOR)));
        setLayout(new FlowLayout(FlowLayout.CENTER));
        setPreferredSize(new Dimension((int) (width* GameConfig.SIZE_FACTOR), (int) (height*GameConfig.SIZE_FACTOR)));

        startButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.startGame();
                // remove start button
                remove(horizontalBox);
                // Update each player's game board based on the state of the game
                updatePlayerPanelList();
            }
        });
    }

    void updatePlayerPanelList() {
        // Update each player's game board based on the state of the game
        for (PlayerPanel playerPanel : playerPanelList) {
            playerPanel.update();
        }
    }


    public void playerActionPerforming() {
        Player player = controller.turnToNextPayingPlayer();
        player.notifyListeners();
    }

    public void toNextPlayerToPay() {
        Player player = controller.turnToNextPayingPlayer();
        player.notifyListeners();
    }

    public void clearGame() {
        parent.clearGame();
    }
}
