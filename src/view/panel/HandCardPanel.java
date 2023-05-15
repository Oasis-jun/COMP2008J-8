package view.panel;

import model.Player;

import javax.swing.*;
import java.awt.*;

public class HandCardPanel extends DebugPanel {

    public HandCardPanel(Player player) {
        setBorder(BorderFactory.createTitledBorder("Hand"));
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(600, 200));
    }
}
