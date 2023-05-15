package view.panel;

import model.Player;

import javax.swing.*;
import java.awt.*;

public class InfoPanel extends DebugPanel{
    public InfoPanel(Player player) {
        setBorder(BorderFactory.createTitledBorder("Info"));
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(100, 200));
    }
}
