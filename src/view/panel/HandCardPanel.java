package view.panel;

import javax.swing.*;
import java.awt.*;

public class HandCardPanel extends DebugPanel {

    public HandCardPanel() {
        setBorder(BorderFactory.createTitledBorder("Hand"));
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(600, 200));
    }
}
