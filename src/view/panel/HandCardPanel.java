package view.panel;

import model.player.Player;

import javax.swing.*;
import javax.swing.JPanel;
import java.awt.*;

public class HandCardPanel extends JPanel {

    public HandCardPanel(Player player) {
        setBorder(BorderFactory.createTitledBorder("Hand"));
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(600, 200));
    }
}
