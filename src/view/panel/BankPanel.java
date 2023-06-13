package view.panel;

import model.player.Player;

import javax.swing.*;
import javax.swing.JPanel;
import java.awt.*;

public class BankPanel extends JPanel {
    public BankPanel(Player player) {
        setBorder(BorderFactory.createTitledBorder("Bank"));
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(400, 200));

    }
}
