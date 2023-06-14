package view.panel;

import config.GameConfig;
import model.player.Player;

import javax.swing.*;
import javax.swing.JPanel;
import java.awt.*;

public class HandCardPanel extends JPanel {

    private final int width = 650;
    private final int height = 200;

    public HandCardPanel(Player player) {
        setBorder(BorderFactory.createTitledBorder("Hand"));
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension((int) (width* GameConfig.SIZE_FACTOR), (int) (height*GameConfig.SIZE_FACTOR)));
    }
}
