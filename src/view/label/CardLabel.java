package view.label;

import config.GameConfig;
import model.card.Card;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CardLabel extends JLabel {


    private final ImageIcon imageIcon;
    private final int width;
    private final int height;
    private Card card;

    private Image image;

    public CardLabel(Card card, int width, int height) {
//        model.card.setSelected(false);
        this.card = card;
        this.width = width;
        this.height = height;
        imageIcon = new ImageIcon();
        image = card.getImage();
        Image scaledInstance = image.getScaledInstance(width, height, Image.SCALE_DEFAULT);
        imageIcon.setImage(scaledInstance);
        setPreferredSize(new Dimension(width,height));
        setIcon(imageIcon);
        if (card.isSelected()){
            enlarge();
        }else {
            recoverSize();
        }
        addMouseListener(new MouseAdapter() {


            @Override
            public void mousePressed(MouseEvent e) {
                card.setSelected(!card.isSelected());
                if (card.isSelected()){
                    enlarge();
                }else {
                    recoverSize();
                }
                setIcon(imageIcon);
                Container parent = e.getComponent().getParent();

                parent.doLayout();
                parent.repaint();
            }
        });
    }

    public void enlarge() {
        imageIcon.setImage(image.getScaledInstance((int) (width *1.3*GameConfig.SIZE_FACTOR), (int) (height *1.3*GameConfig.SIZE_FACTOR),Image.SCALE_SMOOTH));
        setPreferredSize(new Dimension((int) (width *1.3*GameConfig.SIZE_FACTOR), (int) (height *1.3*GameConfig.SIZE_FACTOR)));
    }

    public void recoverSize() {
        imageIcon.setImage(image.getScaledInstance((int) (width* GameConfig.SIZE_FACTOR), (int) (height*GameConfig.SIZE_FACTOR),Image.SCALE_SMOOTH));
        setPreferredSize(new Dimension((int) (width* GameConfig.SIZE_FACTOR), (int) (height*GameConfig.SIZE_FACTOR)));
    }


    public CardLabel(Card card) {

        this(card,60,150);
    }

    public Card getCard() {
        return card;
    }
}
