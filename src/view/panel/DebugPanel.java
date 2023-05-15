package view.panel;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class DebugPanel extends JPanel {
    public DebugPanel() {


        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                System.out.println("("+e.getX()+", "+e.getY()+")");
            }
        });
    }
}
