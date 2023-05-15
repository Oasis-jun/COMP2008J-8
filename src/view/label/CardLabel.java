package view.label;

import card.Card;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CardLabel extends JLabel {


    private Card card;

    private Image image;

    public CardLabel(Card card, int width, int height) {
        ImageIcon imageIcon = new ImageIcon();
        image = card.getImage();
        Image scaledInstance = image.getScaledInstance(width, height, Image.SCALE_DEFAULT);
        imageIcon.setImage(scaledInstance);
        setPreferredSize(new Dimension(width,height));
        setIcon(imageIcon);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                card.setSelected(!card.isSelected());
                if (card.isSelected()){
                    imageIcon.setImage(image.getScaledInstance(width+width/3,height+width/3,Image.SCALE_SMOOTH));
                    setPreferredSize(new Dimension(width+width/3,height+width/3));
                }else {
                    imageIcon.setImage(image.getScaledInstance(width,height,Image.SCALE_SMOOTH));
                    setPreferredSize(new Dimension(width,height));
                }
                setIcon(imageIcon);
                Container parent = e.getComponent().getParent();
                parent.doLayout();
                parent.repaint();
            }
        });
    }



    public CardLabel(Card card) {
        this(card,60,150);
    }
}
