package view.panel;

import view.OperationButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class OperationPanel extends JPanel {

    private JButton discardButton;
    private JButton bankingCardButton;
    private JButton actionCardButton;
    private JButton putDownPropertyCardButton;
    private JButton confirmButton;

    private JButton payButton;
    public OperationPanel() {
        bankingCardButton=new OperationButton("Depositing");
        actionCardButton = new OperationButton("Perform action");
        putDownPropertyCardButton = new OperationButton("Increase Property");
        confirmButton=new OperationButton("Confirm");
        payButton = new OperationButton("Pay");
        discardButton = new OperationButton("Discard");
        enableAll(false);
        add(bankingCardButton);
        add(actionCardButton);
        add(putDownPropertyCardButton);
        add(payButton);
        add(discardButton);

        add(confirmButton);

        setBorder(BorderFactory.createTitledBorder("Operation"));
        setBackground(Color.WHITE);
//        setLayout(new FlowLayout(FlowLayout.CENTER));
        setPreferredSize(new Dimension(250, 200));

    }

    public void enableAll(boolean b) {
        bankingCardButton.setEnabled(b);
        actionCardButton.setEnabled(b);
        putDownPropertyCardButton.setEnabled(b);
        confirmButton.setEnabled(b);
        payButton.setEnabled(b);
        discardButton.setEnabled(b);

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

    public void addPayAction(ActionListener al){
        payButton.addActionListener(al);
    }

    public void addActionButtonAction(ActionListener al){
        actionCardButton.addActionListener(al);
    }

    public void addDiscardButtonAction(ActionListener al){
        discardButton.addActionListener(al);
    }

    public void enablePaying(boolean b) {
        payButton.setEnabled(b);
    }
}
