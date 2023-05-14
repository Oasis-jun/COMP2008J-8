package view.panel;

import javax.swing.*;
import java.awt.*;

public class InfoPanel extends DebugPanel{
    public InfoPanel() {
        setBorder(BorderFactory.createTitledBorder("Info"));
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(100, 200));
    }
}
