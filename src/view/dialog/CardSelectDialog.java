package view.dialog;

import model.card.Card;
import view.label.CardLabel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

public class CardSelectDialog extends JDialog {


    private final JButton confirm;

    public CardSelectDialog(List<Card> availableRentCard) {
        confirm = new JButton("Confirm");
        JPanel availablePropertyPanel = new JPanel();
        availablePropertyPanel.setBorder(BorderFactory.createTitledBorder("Available Cards"));
        for (Card selectablePropertyCard : availableRentCard) {
            CardLabel cardLabel = new CardLabel(selectablePropertyCard);
            availablePropertyPanel.add(cardLabel);
        }
        add(availablePropertyPanel);
        add(confirm);
        setLayout(new FlowLayout(FlowLayout.LEFT));
        pack();
        setVisible(true);
    }

    public void addConfirmAction(ActionListener al){
        confirm.addActionListener(al);
    }
}
