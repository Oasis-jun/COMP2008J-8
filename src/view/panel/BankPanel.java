package view.panel;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;

import model.Player;

public class BankPanel extends DebugPanel {
    public BankPanel(Player player) {
        setBorder(BorderFactory.createTitledBorder("Bank"));
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(400, 200));

    }
}
