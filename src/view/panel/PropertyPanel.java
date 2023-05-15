package view.panel;

import model.Player;

import javax.swing.*;
import java.awt.*;

public class PropertyPanel extends DebugPanel {

    public PropertyPanel(Player player) {

        setBorder(BorderFactory.createTitledBorder("Properties Cards"));
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(400, 200));
    }
}
