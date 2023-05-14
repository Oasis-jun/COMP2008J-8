package view;


import controller.GameController;
import view.panel.GamePanel;

import javax.swing.*;
import java.awt.*;

public class MenuFrame extends JFrame {

    private  GameBoardFrame gameBoardFrame;

    private GameController gameController;

    public MenuFrame(GameController gameController) {
        JLabel label = new JLabel("Please select player number:");
        JButton next = new JButton("Next");
        JComboBox<String> comboBox = new JComboBox<>(new String[]{"2", "3", "4", "5"});
        add(label);
        add(comboBox);
        add(next);
        setLayout(new FlowLayout(FlowLayout.LEFT));
        next.addActionListener(e->{
            Integer playerNum = Integer.valueOf((String) comboBox.getSelectedItem());
            gameController.init(playerNum);
            this.setVisible(false);
        });
        this.pack();
    }
}
