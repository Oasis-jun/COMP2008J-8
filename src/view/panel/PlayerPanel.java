package view.panel;

import card.Card;
import card.PropertyCard;
import model.Player;
import view.label.CardLabel;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PlayerPanel extends DebugPanel {

    private final BankPanel bankPanel;
    private final PropertyPanel propertyPanel;
    private final InfoPanel infoPanel;
    private final OperationPanel operationPanel;
    private  HandCardPanel handCardPanel;
    private Player player;

    public PlayerPanel(Player player) {
        this.player = player;
        setBorder(BorderFactory.createTitledBorder(player.getName()));
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        operationPanel= new OperationPanel();
        handCardPanel = new HandCardPanel(player);
        bankPanel = new BankPanel(player);
        propertyPanel = new PropertyPanel(player);
        infoPanel = new InfoPanel(player);
        add(infoPanel);
        add(handCardPanel);
        add(bankPanel);
        add(propertyPanel);
        add(operationPanel);
        setLayout(new FlowLayout(FlowLayout.LEFT));
        setMaximumSize(new Dimension(1800, 200));
        operationPanel.addDepositAction(e->{
            List<Card> handCards = player.getHandCards();
            ArrayList<Card> selectedCards = new ArrayList<>();
            for (Card handCard : handCards) {
                if (handCard.isSelected()){
                    if (handCard instanceof PropertyCard){
                        JOptionPane.showMessageDialog(null, "Cannot put a property card into bank", "Error", JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }
                    selectedCards.add(handCard);
                }
            }
            if (selectedCards.size()>player.getTurnInfo().cardAvailable){
                JOptionPane.showMessageDialog(null, "User has selected more than 3 three card in this turn", "Error", JOptionPane.INFORMATION_MESSAGE);
                return;
            }else {
                player.getTurnInfo().cardAvailable=player.getTurnInfo().cardAvailable-selectedCards.size();
            }

            for (Card selectedCard : selectedCards) {
                selectedCard.setSelected(false);
                player.getBank().deposit(selectedCard);
                handCards.remove(selectedCard);
            }
            updatePlayer();
        });

        operationPanel.addNewPropertyAction(e->{
            List<Card> handCards = player.getHandCards();
            ArrayList<Card> selectedCards = new ArrayList<>();
            for (Card handCard : handCards) {
                if (handCard.isSelected()){
                    if (handCard instanceof PropertyCard){
                        selectedCards.add(handCard);
                    }else {
                        JOptionPane.showMessageDialog(null, "Selected cards contain non-property card", "Error", JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }
                }
            }
            if (selectedCards.size()>player.getTurnInfo().cardAvailable){
                JOptionPane.showMessageDialog(null, "User has selected more than 3 three card in this turn", "Error", JOptionPane.INFORMATION_MESSAGE);
                return;
            }else {
                player.getTurnInfo().cardAvailable=player.getTurnInfo().cardAvailable-selectedCards.size();
            }
            for (Card selectedCard : selectedCards) {
                selectedCard.setSelected(false);
                player.getPropertyCards().add((PropertyCard)selectedCard);
                handCards.remove(selectedCard);
            }
            updatePlayer();
        });

    }

    public void updateHandCards(){
        handCardPanel.removeAll();
        for (Card handCard : player.getHandCards()) {
            if (Player.Status.playing.equals(player.getStatus())){
                handCardPanel.add(new CardLabel(handCard));
            }else {
                handCardPanel.add(new CardLabel(Card.getCardBack()));
            }
        }
        handCardPanel.doLayout();
        handCardPanel.repaint();
    }

    public void updatePlayer(){
        if (Player.Status.playing.equals(player.getStatus())){
            this.setBackground(Color.ORANGE);
        }else {
            this.setBackground(Color.white);
        }
        updateHandCards();
        updateOperationPanel();
        updatePropertyPanel();
        updateBankPanel();
        doLayout();
        repaint();
    }

    private void updatePropertyPanel() {
        propertyPanel.removeAll();
        for (Card handCard : player.getPropertyCards()) {
            propertyPanel.add(new CardLabel(handCard,30,75));
        }
        propertyPanel.doLayout();
        propertyPanel.repaint();

    }

    private void updateBankPanel() {
        bankPanel.removeAll();
        for (Card handCard : player.getBank().getCardInBank()) {
            bankPanel.add(new CardLabel(handCard,30,75));
        }
        bankPanel.doLayout();
        bankPanel.repaint();
    }

    private void updateOperationPanel() {
        if (Player.Status.playing.equals(player.getStatus())){
            operationPanel.enableAll(true);
        }else {
            operationPanel.enableAll(false);
        }
    }

    public void addConfirmAction(ActionListener listener) {
        operationPanel.addConfirmAction(listener);

    }
}
