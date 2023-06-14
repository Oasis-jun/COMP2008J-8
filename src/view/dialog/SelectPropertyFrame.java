package view.dialog;

import model.card.Card;
import model.card.PropertyCard;
import model.card.PropertyWildcard;
import model.player.Player;
import view.label.CardLabel;
import view.panel.PlayerPanel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class SelectPropertyFrame extends JDialog {

    public SelectPropertyFrame(Player player, PlayerPanel parent) {
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        JPanel originCardPanel = new JPanel();
        PropertyWildcard remove = player.getTurnInfo().propertyWildcards.remove(0);

        CardLabel originalCardLabel = new CardLabel(remove,70,150);
        originalCardLabel.removeMouseListener(originalCardLabel.getMouseListeners()[0]);
        originCardPanel.add(originalCardLabel);
        originCardPanel.setBorder(BorderFactory.createTitledBorder("Original Card"));

        JButton confirm = new JButton("Confirm");
        JPanel availablePropertyPanel = new JPanel();
        availablePropertyPanel.setBorder(BorderFactory.createTitledBorder("Available Cards"));
        int width =remove.getCardsToChange().size()*70;
        int height =180;
        availablePropertyPanel.setPreferredSize(new Dimension(width+70,height));
        availablePropertyPanel.setMinimumSize(new Dimension(width+70,height));
        availablePropertyPanel.setMaximumSize(new Dimension(width+70,height));
        for (Card propertyCard : remove.getCardsToChange()) {
            CardLabel cardLabel = new CardLabel(propertyCard);
            availablePropertyPanel.add(cardLabel);
        }

        add(originCardPanel);
        add(availablePropertyPanel);
        add(confirm);
        originCardPanel.setSize(originalCardLabel.getSize());
        setLayout(new FlowLayout(FlowLayout.LEFT));
        this.setLocationRelativeTo(parent);
        parent.setEnabled(false);
        pack();
        setVisible(true);

        confirm.addActionListener(e->{
            ArrayList<PropertyCard> propertyWildcards = new ArrayList<>();
            for (Card propertyWildcard : remove.getCardsToChange()) {
                if (propertyWildcard.isSelected()){
                    propertyWildcards.add((PropertyCard) propertyWildcard);
                }
            }

            if (propertyWildcards.size()==0){
                JOptionPane.showMessageDialog(null, "No property model.card is selected", "Error", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            if (propertyWildcards.size()>1){
                JOptionPane.showMessageDialog(null, "Only one property model.card can be selected", "Error", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            player.addProperty(propertyWildcards.remove(0));
            player.setStatus(Player.Status.playing);
            if (player.getTurnInfo().propertyWildcards.isEmpty()){

                parent.setEnabled(true);
                parent.updatePlayer();
            }else {
                new SelectPropertyFrame(player,parent);
            }
            this.setVisible(false);
        });

    }
}
