package view.panel;

import config.GameConfig;
import model.card.*;
import controller.GameController;
import model.player.*;
import view.GameBoard;
import view.ModelListener;
import view.dialog.CardSelectDialog;
import view.dialog.PerformingRentActionDialog;
import view.dialog.SelectPropertyFrame;
import view.label.CardLabel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class PlayerPanel extends JPanel implements ModelListener {

    private final BankPanel bankPanel;
    private final PropertyPanel propertyPanel;
    private final InfoPanel infoPanel;
    private final OperationPanel operationPanel;
    private  HandCardPanel handCardPanel;
    private Player player;

    private GameController controller;

    private GamePanel parent;
    private final int width = 2000;
    private final int height = 200;


    public PlayerPanel(Player player, GameController controller, GamePanel gamePanel) {
        this.player = player;
        this.controller = controller;
        player.addListener(this);
        this.parent = gamePanel;
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
        setMaximumSize(new Dimension((int) (width* GameConfig.SIZE_FACTOR), (int) (height*GameConfig.SIZE_FACTOR)));
        // Click events are created for each button
        operationPanel.addDepositAction(e->{
            depositAction();
        });

        operationPanel.addNewPropertyAction(e->{
            addNewPropertyAction();
        });

        operationPanel.addActionButtonAction(e->{
            actionButtonAction();
        });

        operationPanel.addPayAction(e->{
            payActionButton();
        });

        operationPanel.addDiscardButtonAction(e->{
            discardActionButton();
        });
        operationPanel.addConfirmAction(e->{
            addConfirmAction();
        });
    }

    private void discardActionButton() {
        try {
            controller.playerDiscardCard(player);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void payActionButton() {
        try {
            controller.pay(player);

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.INFORMATION_MESSAGE);
        }
    }


    private  void actionButtonAction() {
        try {
            controller.playerAction(player);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.INFORMATION_MESSAGE);
        }
    }


    private void performAction() {
        // Since the double the rent must be used with the rent card, first determine whether the double the rent card exists
        if (executeDoubleTheRentCard()){
            return;
        }

        ActionCard actionCard = player.getTurnInfo().getPerformingActionCard().get(0);
        // If it is rent card, the color selection box for rent card is displayed and the confirm function is defined
        if (actionCard instanceof RentCard){
            RentCard rentCard = (RentCard) actionCard;
            this.setEnabled(false);
            PerformingRentActionDialog performingRentActionDialog = new PerformingRentActionDialog(actionCard);
            // And define the confirm function
            performingRentActionDialog.addConfirmAction(e->{
                try {
                    controller.playerRentAction(player,rentCard);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    player.getTurnInfo().getPerformingActionCard().remove(actionCard);
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.INFORMATION_MESSAGE);
                }
                performingRentActionDialog.setVisible(false);
//                this.setEnabled(true);
                // over
            });
        }else {
            // If it is not a rent card, the corresponding logic is executed according to the name of the action card. Because of the need for interaction, it is not suitable to write the logic in the controller
            List<Player> players = controller.getPlayers().stream().filter(p -> p != player).collect(Collectors.toList());
            switch (actionCard.getName()){
                case "Pass Go":
                    controller.playerPassGoAction(player);

                    break;
                case "House":
                    List<Property> properties = player.getProperties().values().stream().filter(p -> p.getSetNumber() >= p.getMaxSetNum() && (!p.getProperty().equals("Railroad") || !p.getProperty().equals("Utility"))).toList();
                    if (properties.isEmpty()){
                        JOptionPane.showMessageDialog(null, "You don't preserve any full set", "Error", JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }
                    Property property = (Property) JOptionPane.showInputDialog(this, "Select full set", "Select full set", 1, null, properties.toArray(), properties.get(0));
                    property.setRentPrice(property.getRentPrice()+3);
                    break;
                case "Hotel":
                    properties = player.getProperties().values().stream().filter(p -> p.getSetNumber() >= p.getMaxSetNum() && (!p.getProperty().equals("Railroad") || !p.getProperty().equals("Utility"))).toList();
                    if (properties.isEmpty()){
                        JOptionPane.showMessageDialog(null, "You don't preserve any full set", "Error", JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }
                    property = (Property) JOptionPane.showInputDialog(this, "Select full set", "Select full set", 1, null, properties.toArray(), properties.get(0));
                    property.setRentPrice(property.getRentPrice()+4);
                    break;
                case "Debt Collector":
                    Player selectedPlayer = (Player)JOptionPane.showInputDialog(this, "Select targeted player", "Select player", 1, null, players.toArray(), players.get(0));
                    controller.playerAskPayAction(player,5,selectedPlayer);
                    break;
                case "It's My Birthday":
                    controller.playerAskPayAction(player,2,players);
                    break;

                case "Force Deal":
                    List<Property> myProperties = player.getProperties().values().stream().filter(p -> p.getSetNumber() < p.getMaxSetNum()&&p.getSetNumber()>0).toList();
                    if (myProperties.isEmpty()){
                        JOptionPane.showMessageDialog(null, "You don't have any property that is not in full set", "Error", JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }
                    Property myProperty = (Property) JOptionPane.showInputDialog(this, "Select property", "Select property", 1, null, myProperties.toArray(), myProperties.get(0));
                    selectedPlayer = (Player) JOptionPane.showInputDialog(this, "Select targeted player", "Select player", 1, null, players.toArray(), players.get(0));
                    List<Property> playerProperties = selectedPlayer.getProperties().values().stream().filter(p -> p.getSetNumber() < p.getMaxSetNum()&&p.getSetNumber()>0).toList();
                    if (playerProperties.isEmpty()){
                        JOptionPane.showMessageDialog(null, "Selected player doesn't have any property that is not in full set", "Error", JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }
                    Property playerProperty = (Property) JOptionPane.showInputDialog(this, "Select player's property", "Select property", 1, null, playerProperties.toArray(), playerProperties.get(0));
                    SwapRequest swapRequest = new SwapRequest(player,myProperty,selectedPlayer,playerProperty);

                    controller.registerPayingPlayer(selectedPlayer);
                    break;
                case "Deal Breaker":
                    selectedPlayer = (Player) JOptionPane.showInputDialog(this, "Select targeted player", "Select player", 1, null, players.toArray(), players.get(0));
                    playerProperties = selectedPlayer.getProperties().values().stream().filter(p -> p.getSetNumber() >= p.getMaxSetNum()).toList();
                    if (playerProperties.isEmpty()){
                        JOptionPane.showMessageDialog(null, "Selected player doesn't have any property that is in full set", "Error", JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }
                    playerProperty = (Property) JOptionPane.showInputDialog(this, "Select player's property", "Select property", 1, null, playerProperties.toArray(), playerProperties.get(0));
                    StealRequest stealRequest = new StealRequest(player,selectedPlayer,playerProperty,true);


                    controller.acceptRequest(stealRequest);
                    break;
                case "Sly Deal":
                    selectedPlayer = (Player) JOptionPane.showInputDialog(this, "Select targeted player", "Select player", 1, null, players.toArray(), players.get(0));
                    playerProperties = selectedPlayer.getProperties().values().stream().filter(p -> p.getSetNumber() < p.getMaxSetNum()&&p.getSetNumber()>0).toList();
                    if (playerProperties.isEmpty()){
                        JOptionPane.showMessageDialog(null, "Selected player doesn't have any property that is not in full set", "Error", JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }
                    playerProperty = (Property) JOptionPane.showInputDialog(this, "Select player's property", "Select property", 1, null, playerProperties.toArray(), playerProperties.get(0));
                    stealRequest = new StealRequest(player,selectedPlayer,playerProperty,false);

                    controller.acceptRequest(stealRequest);
                    break;
            }
            // After the action card is successfully executed, set some column states to remove the card from the corresponding position only after the Action Card is successfully executed
            player.getTurnInfo().getPerformingActionCard().remove(actionCard);
            CardApi.putCardToCenter(player, actionCard, controller);
            player.getTurnInfo().cardAvailable= player.getTurnInfo().cardAvailable-1;
            controller.setStatus(GameController.Status.paying);
            player.notifyListeners();
            // over
        }
    }

    private boolean executeDoubleTheRentCard() {
        // Since the double the rent must be used with the rent card, first determine whether the double the rent card exists
        Optional<ActionCard> doubleTheRentOpt = player.getTurnInfo().getPerformingActionCard().stream().filter(a -> a.getName().equals("Double The Rent")).findAny();
        if (doubleTheRentOpt.isPresent()){
            ActionCard doubleTheRentCard = doubleTheRentOpt.get();
            List<Card> availableRentCard=CardApi.getRentCards(player.getTurnInfo().getPerformingActionCard());
            // If there is a double the rent but no rent card, an error is returned
            if (availableRentCard.isEmpty()){
                JOptionPane.showMessageDialog(null, "'Double The Rent' card must be used with rent card", "Error", JOptionPane.INFORMATION_MESSAGE);
                this.setEnabled(true);
            }else {
                // If it exists, set the rent factor of the card's rent card to 2 to calculate when using the card
                CardSelectDialog cardSelectDialog = new CardSelectDialog(availableRentCard);
                cardSelectDialog.addConfirmAction(e->{
                    try {
                        // If it exists, set the rent factor of the card's rent card to 2 to calculate when using the card
                        controller.playerDoubleARentCard(player,availableRentCard,doubleTheRentCard);
                        cardSelectDialog.setVisible(false);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null,ex.getMessage() , "Error", JOptionPane.INFORMATION_MESSAGE);
                    }
                });
            }
            return true;
        }
        return false;
    }

    private  void addNewPropertyAction() {
        try {
            controller.playerAddProperty(player);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void depositAction() {
        try {
            controller.playerDeposit(player);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void updateHandCards(){
        handCardPanel.removeAll();
        for (Card handCard : player.getHandCards()) {
            handCard.setSelected(false);
            // Allow the player who is playing and the player who is paying to see their cards
            if (Player.Status.playing.equals(player.getStatus())||Player.Status.paying.equals(player.getStatus())){
                handCardPanel.add(new CardLabel(handCard));
            }else {
                handCardPanel.add(new CardLabel(Card.getCardBack()));
            }
        }
        handCardPanel.doLayout();
        handCardPanel.repaint();
    }

    /**
     * Update the player's game status
     */
    public void updatePlayer(){
         if (Player.Status.playing.equals(player.getStatus())||Player.Status.action.equals(player.getStatus())){
            this.setBackground(Color.ORANGE);
        }else if (Player.Status.paying.equals(player.getStatus())){
            this.setBackground(Color.blue);
        }else {
            this.setBackground(Color.white);
        }
        // Some columns of panel update operations
        updateHandCards();
        updateOperationPanel();
        updatePropertyPanel();
        updateBankPanel();
        updateInfoPanel();
        doLayout();
        repaint();
        // When the controller state changes to playing, it means that the current player is the player who chose to play the card
        // If the status of the current player is still action, the current player has an action card that needs to be processed

    }

    private void updateInfoPanel() {
        infoPanel.updateInfo();
    }

    private void updatePropertyPanel() {
        propertyPanel.removeAll();
        for (Card propertyCar : player.getPropertyCards()) {
            propertyCar.setSelected(false);
            propertyPanel.add(new CardLabel(propertyCar,30,75));
        }
        propertyPanel.doLayout();
        propertyPanel.repaint();

    }

    private void updateBankPanel() {
        bankPanel.removeAll();
        for (Card bankCard : player.getBank().getCardInBank()) {
            bankCard.setSelected(false);
            bankPanel.add(new CardLabel(bankCard,30,75));
        }
        bankPanel.doLayout();
        bankPanel.repaint();
    }

    private void updateOperationPanel() {
        if (Player.Status.playing.equals(player.getStatus())){
            operationPanel.enableAll(true);
            operationPanel.enablePaying(false);
        }else if (Player.Status.paying.equals(player.getStatus())){
            operationPanel.enableAll(false);
            operationPanel.enablePaying(true);
        }else {
            operationPanel.enableAll(false);
        }
        operationPanel.doLayout();
        operationPanel.repaint();
    }

    public void addConfirmAction() {
        if (player.win()){
            JOptionPane.showMessageDialog(null, player.getName()+ " has win the game", "Win!", JOptionPane.INFORMATION_MESSAGE);
            int i = JOptionPane.showConfirmDialog(this, "Do you want to start the next game?");
            if (i==-1){
                System.exit(1);
            }else {
                this.parent.clearGame();
                new GameBoard().start();
            }
            return;
        }
        if (player.getHandCards().size()>7){
            JOptionPane.showMessageDialog(null, "User contains more than 7 cards in his hand.", "Error", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        controller.turnToNextPlayer();
        parent.doLayout();
        parent.repaint();
    }


    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        operationPanel.enableAll(enabled);
    }


    @Override
    public void update() {
        updatePlayer();
        if (!player.getStatus().equals(Player.Status.paying)&&controller.getStatus().equals(GameController.Status.paying)){
            parent.toNextPlayerToPay();
        }else if (controller.getStatus()==GameController.Status.playing&&player.getStatus() == Player.Status.action){
            performAction();
        }else if (Player.Status.decidingWildCardChange.equals(player.getStatus())){
            new SelectPropertyFrame(player,this);
        }
    }
}
