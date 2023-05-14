package view;

import controller.GameController;
import view.panel.GamePanel;
import view.panel.MenuPanel;

import javax.swing.*;
import java.awt.*;

public class GameBoardFrame extends JFrame {



    private GamePanel gamePanel;


    public GameBoardFrame(GameController gameController) throws HeadlessException {
        gamePanel = new GamePanel(gameController);
        add(gamePanel);
        pack();

    }




}

