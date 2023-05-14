package view.panel;

import model.Player;
import view.label.CardLabel;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PlayerPanel extends DebugPanel {

    private final BankPanel bankPanel;
    private final PropertyPanel propertyPanel;
    private final InfoPanel infoPanel;
    private  HandCardPanel handCardPanel;
    private Player player;

    public PlayerPanel(Player player) {
        this.player = player;
        setBorder(BorderFactory.createTitledBorder(player.getName()));
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        handCardPanel = new HandCardPanel();
         bankPanel = new BankPanel();
         propertyPanel = new PropertyPanel();
         infoPanel = new InfoPanel();
        add(infoPanel);
        add(handCardPanel);
        add(bankPanel);
        add(propertyPanel);
        setLayout(new FlowLayout(FlowLayout.LEFT));
        setMaximumSize(new Dimension(1500, 200));
    }
}
