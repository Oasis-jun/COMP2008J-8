package view;

import config.GameConfig;

import javax.swing.*;
import java.awt.*;

public class OperationButton extends JButton {

    private final int width = 200;
    private final int height = 20;

    public OperationButton(String text) {
        super(text);
        setPreferredSize(new Dimension((int) (width* GameConfig.SIZE_FACTOR), (int) (height*GameConfig.SIZE_FACTOR)));
        setMinimumSize(new Dimension((int) (width* GameConfig.SIZE_FACTOR), (int) (height*GameConfig.SIZE_FACTOR)));
    }
}
