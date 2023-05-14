package view.panel;

import javax.swing.*;
import java.awt.*;

public class BankPanel extends DebugPanel {
    public BankPanel() {
        setBorder(BorderFactory.createTitledBorder("Bank"));
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(400, 200));

    }
}
