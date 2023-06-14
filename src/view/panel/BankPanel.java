package view.panel;

import config.GameConfig;
import model.player.Player;

import javax.swing.*;
import javax.swing.JPanel;
import java.awt.*;

public class BankPanel extends JPanel {

   private int width = 500;
   private int height = 200;
    public BankPanel(Player player) {
        setBorder(BorderFactory.createTitledBorder("Bank"));
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension((int) (width* GameConfig.SIZE_FACTOR), (int) (height*GameConfig.SIZE_FACTOR)));

    }
}
