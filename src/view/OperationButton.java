package view;

import javax.swing.*;
import java.awt.*;

public class OperationButton extends JButton {
    public OperationButton(String text) {
        super(text);
        setPreferredSize(new Dimension(200,25));
        setMinimumSize(new Dimension(200,25));

    }
}
