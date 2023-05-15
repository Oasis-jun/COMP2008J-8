package view.panel;

import model.Player;

import javax.swing.*;
import java.awt.*;

public class BankPanel extends DebugPanel {
    public BankPanel(Player player) {
        setBorder(BorderFactory.createTitledBorder("Bank"));
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(400, 200));

    }
}
