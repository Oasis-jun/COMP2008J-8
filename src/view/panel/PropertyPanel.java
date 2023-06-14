package view.panel;

import config.GameConfig;
import model.player.Player;

import javax.swing.*;
import java.awt.*;

public class PropertyPanel extends JPanel {

    private final int width = 400;
    private final int height = 200;

    public PropertyPanel(Player player) {

        setBorder(BorderFactory.createTitledBorder("Properties Cards"));
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension((int) (width* GameConfig.SIZE_FACTOR), (int) (height*GameConfig.SIZE_FACTOR)));
    }
}
