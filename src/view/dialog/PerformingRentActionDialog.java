package view.dialog;

import model.card.*;
import view.label.CardLabel;
import view.panel.PlayerPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class PerformingRentActionDialog extends JDialog {

    private  JButton confirm;


    public PerformingRentActionDialog(Card actionCard) {
        JPanel originCardPanel = new JPanel();
        CardLabel originalCardLabel = new CardLabel(actionCard,70,150);
        originalCardLabel.removeMouseListener(originalCardLabel.getMouseListeners()[0]);
        originCardPanel.add(originalCardLabel);
        originCardPanel.setBorder(BorderFactory.createTitledBorder("Original Card"));
        confirm = new JButton("Confirm");
        RentCard rentCard = (RentCard) actionCard;
        JPanel availablePropertyPanel = new JPanel();
        availablePropertyPanel.setBorder(BorderFactory.createTitledBorder("Available Cards"));
        int width = ((RentCard) actionCard).getSelectablePropertyCards().size()*70;

        int height = 180;
        availablePropertyPanel.setPreferredSize(new Dimension(width+70,height));
        availablePropertyPanel.setMinimumSize(new Dimension(width+70,height));
        availablePropertyPanel.setMaximumSize(new Dimension(width+70,height));
        for (PropertyCard selectablePropertyCard : rentCard.getSelectablePropertyCards()) {
            CardLabel cardLabel = new CardLabel(selectablePropertyCard);
            availablePropertyPanel.add(cardLabel);
        }
        add(originCardPanel);
        add(availablePropertyPanel);
        add(confirm);
        originCardPanel.setSize(originalCardLabel.getSize());
        setLayout(new FlowLayout(FlowLayout.LEFT));
        pack();
        setVisible(true);

    }

    public void addConfirmAction(ActionListener al){
        confirm.addActionListener(al);
    }




}
