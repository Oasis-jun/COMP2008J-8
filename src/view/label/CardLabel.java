package view.label;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CardLabel extends JLabel {


    private final Image image;

    public CardLabel(String path, int width, int height) {
        ImageIcon imageIcon = new ImageIcon(path);
        image = imageIcon.getImage();
        Image scaledInstance = image.getScaledInstance(width, height, Image.SCALE_DEFAULT);
        imageIcon.setImage(scaledInstance);
        setPreferredSize(new Dimension(width,height));
        setIcon(imageIcon);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println(e.getComponent());
                Container parent = e.getComponent().getParent();
                System.out.println(parent);
                parent.remove(e.getComponent());
                parent.doLayout();
                parent.repaint();

            }
        });
    }

    public CardLabel(String imagePath) {
        this(imagePath,60,150);
    }
}
