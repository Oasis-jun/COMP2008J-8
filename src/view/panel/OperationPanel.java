package view.panel;

import card.Card;
import model.Player;
import view.OperationButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class OperationPanel extends DebugPanel {

    private JButton bankingCardButton;
    private JButton actionCardButton;
    private JButton putDownPropertyCardButton;
    private JButton confirmButton;
    public OperationPanel() {
        bankingCardButton=new OperationButton("Depositing");
        actionCardButton = new OperationButton("Perform action");
        putDownPropertyCardButton = new OperationButton("Increase Property");
        confirmButton=new OperationButton("Confirm");
        enableAll(false);
        add(bankingCardButton);
        add(actionCardButton);
        add(putDownPropertyCardButton);
        add(confirmButton);

        setBorder(BorderFactory.createTitledBorder("Operation"));
        setBackground(Color.WHITE);
        setLayout(new FlowLayout(FlowLayout.CENTER));
        setPreferredSize(new Dimension(250, 200));

    }

    public void enableAll(boolean b) {
        bankingCardButton.setEnabled(b);
        actionCardButton.setEnabled(b);
        putDownPropertyCardButton.setEnabled(b);
        confirmButton.setEnabled(b);
    }


    public void addDepositAction(ActionListener al) {
        bankingCardButton.addActionListener(al);
    }

    public void addNewPropertyAction(ActionListener al) {
        putDownPropertyCardButton.addActionListener(al);
    }

    public void addConfirmAction(ActionListener al) {
        confirmButton.addActionListener(al);
    }
}
