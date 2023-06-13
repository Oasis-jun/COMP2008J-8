package view;

import controller.GameController;
import view.panel.GamePanel;
import view.panel.MenuPanel;

import javax.swing.*;
import java.awt.*;

public class GameBoardFrame extends JFrame {



    private GamePanel gamePanel;


    /**
     * 游戏的最外层框
     * @param gameController
     * @throws HeadlessException
     */
    public GameBoardFrame(GameController gameController) throws HeadlessException {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        // 游戏的主要内容
        gamePanel = new GamePanel(gameController,this);
        add(gamePanel);
        pack();
    }


    public void clearGame() {
        this.setVisible(false);
    }
}

