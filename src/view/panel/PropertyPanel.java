package view.panel;

import javax.swing.*;
import java.awt.*;

public class PropertyPanel extends DebugPanel {

    public PropertyPanel() {

        setBorder(BorderFactory.createTitledBorder("Properties Cards"));
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(400, 200));
    }
}
