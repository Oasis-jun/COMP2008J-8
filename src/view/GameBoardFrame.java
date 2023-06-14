package view;

import controller.GameController;
import view.panel.GamePanel;

import javax.swing.*;
import java.awt.*;

public class GameBoardFrame extends JFrame {



    private GamePanel gamePanel;


    /**
     * The outermost box of the game
     * @param gameController
     * @throws HeadlessException
     */
    public GameBoardFrame(GameController gameController) throws HeadlessException {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        // the main content of the game
        gamePanel = new GamePanel(gameController,this);
        add(gamePanel);
        pack();
    }


    public void clearGame() {
        this.setVisible(false);
    }
}

