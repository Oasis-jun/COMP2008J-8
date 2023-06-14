package view.label;

import config.GameConfig;

import javax.swing.*;
import java.awt.*;

public class InfoLabel extends Label {

    int width = 150;
    int height =20;
    public InfoLabel(String text) {
        super(text);
        setPreferredSize(new Dimension((int) (width* GameConfig.SIZE_FACTOR), (int) (height*GameConfig.SIZE_FACTOR)));
        setMinimumSize(new Dimension((int) (width* GameConfig.SIZE_FACTOR), (int) (height*GameConfig.SIZE_FACTOR)));
        setMaximumSize(new Dimension((int) (width* GameConfig.SIZE_FACTOR), (int) (height*GameConfig.SIZE_FACTOR)));
    }

}
