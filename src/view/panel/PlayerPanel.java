package view.panel;

import model.card.*;
import controller.GameController;
import model.player.*;
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

public class PlayerPanel extends JPanel {

    private final BankPanel bankPanel;
    private final PropertyPanel propertyPanel;
    private final InfoPanel infoPanel;
    private final OperationPanel operationPanel;
    private  HandCardPanel handCardPanel;
    private Player player;

    private GameController controller;

    private GamePanel parent;

    public PlayerPanel(Player player, GameController controller, GamePanel gamePanel) {
        this.player = player;
        this.controller = controller;
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
        setMaximumSize(new Dimension(1800, 200));
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
            GameRequest gameRequest = player.getGameRequest();
            Player target = gameRequest.getIssuer();
            List<Card> handCards = CardApi.getSelectedCard(player.getHandCards());
            List<Card> bankCards = CardApi.getSelectedCard(player.getBank().getCardInBank());
            List<Card> propertyCards=CardApi.getSelectedCard(player.getPropertyCards());
            if (!handCards.isEmpty()){
                if (bankCards.size()+propertyCards.size()>0){
                    JOptionPane.showMessageDialog(null, "Cannot select hand cards with table card ", "Error", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }else if (handCards.size()>1){
                    JOptionPane.showMessageDialog(null, "Cannot select more than one hand card", "Error", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }else {
                    Card card = handCards.get(0);
                    if (card.getName().equals("Just Say No")){
                        player.setStatus(Player.Status.waiting);
                        parent.playerActionPerforming();
                        handCards.remove(card);
                        controller.getPlayPile().add(card);
                        return;
                    }else {
                        JOptionPane.showMessageDialog(null, card.getName()+" Cannot be selected to pay the bill", "Error", JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }
                }
            }

            if (gameRequest instanceof PayingRequest){
                PayingRequest payingRequest = (PayingRequest) gameRequest;
                int worthSum = 0;
                for (Card card : bankCards) {
                    worthSum = worthSum+card.getWorth();
                }
                for (Card card : propertyCards) {
                    worthSum = worthSum+card.getWorth();
                }
                int bill = payingRequest.getBill();
                if (worthSum>bill||(bankCards.size()==player.getBank().getCardInBank().size()&&propertyCards.size()==player.getPropertyCards().size())){
                    for (Card card : bankCards) {
                        target.getBank().deposit(card);
                        player.getBank().pay(card);
                    }
                    for (Card card : propertyCards) {
                        target.addProperty((PropertyCard) card);
                        player.reduceProperty((PropertyCard) card);
                    }
                    player.setStatus(Player.Status.waiting);
                    parent.playerActionPerforming();
                }else {
                    JOptionPane.showMessageDialog(null, "Selected Card cannot afford the bill", "Error", JOptionPane.INFORMATION_MESSAGE);
                }
            }else if (gameRequest instanceof SwapRequest){
                SwapRequest swapRequest = (SwapRequest) gameRequest;
                Player issuer = swapRequest.getIssuer();
                Property issuerProperty = swapRequest.getIssuerProperty();
                Property targetPlayerProperty = swapRequest.getTargetPlayerProperty();
                issuer.addProperty(targetPlayerProperty.reduceProperty());
                player.addProperty(issuerProperty.reduceProperty());
                player.setStatus(Player.Status.waiting);
                parent.playerActionPerforming();
            }else if (gameRequest instanceof StealRequest){
                StealRequest stealRequest = (StealRequest) gameRequest;
                Player issuer = stealRequest.getIssuer();
                Property targetPlayerProperty = stealRequest.getTargetPlayerProperty();
                if (!stealRequest.isFullSet()){
                    issuer.addProperty(targetPlayerProperty.reduceProperty());
                }else {
                    for (int i = 0; i < targetPlayerProperty.getMaxSetNum(); i++) {
                        issuer.addProperty(targetPlayerProperty.reduceProperty());
                    }
                }
                player.setStatus(Player.Status.waiting);
                parent.playerActionPerforming();
            }
        });
    }



    private  void actionButtonAction() {
        List<Card> handCards = player.getHandCards();
        ArrayList<ActionCard> selectedCards = new ArrayList<>();
        for (Card handCard : handCards) {
            if (handCard.isSelected()){
                if (handCard instanceof ActionCard){
                    selectedCards.add((ActionCard) handCard);
                }else {
                    JOptionPane.showMessageDialog(null, "Selected cards contain non-action model.card", "Error", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
            }
        }
        if (selectedCards.isEmpty()){
            JOptionPane.showMessageDialog(null, "No model.card is selected", "Error", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        if (selectedCards.size()> player.getTurnInfo().cardAvailable){
            JOptionPane.showMessageDialog(null, "User has selected more than 3 three model.card in this turn", "Error", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        player.getTurnInfo().getPerformingActionCard().addAll(selectedCards);
        controller.setStatus(GameController.Status.paying);
        performAction();
    }

    private void performAction() {
        Optional<ActionCard> doubleTheRentOpt = player.getTurnInfo().getPerformingActionCard().stream().filter(a -> a.getName().equals("Double The Rent")).findAny();
        if (doubleTheRentOpt.isPresent()){
            ActionCard doubleTheRentCard = doubleTheRentOpt.get();
            ArrayList<Card> availableRentCard = new ArrayList<>();
            for (ActionCard actionCard : player.getTurnInfo().getPerformingActionCard()) {
                if (actionCard instanceof RentCard){
                    availableRentCard.add(actionCard);
                }
            }
            if (availableRentCard.isEmpty()){
                JOptionPane.showMessageDialog(null, "'Double The Rent' card must be used with rent card", "Error", JOptionPane.INFORMATION_MESSAGE);
                this.setEnabled(true);
                return;
            }else {
                CardSelectDialog cardSelectDialog = new CardSelectDialog(availableRentCard);
                cardSelectDialog.addConfirmAction(e->{
                    List<Card> selectedRentCard = CardApi.getSelectedCard(availableRentCard);
                    if (selectedRentCard.isEmpty()){
                        JOptionPane.showMessageDialog(null, "No rent is selected", "Error", JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }
                    if (selectedRentCard.size()>1){
                        JOptionPane.showMessageDialog(null, "Only one rent card can be selected", "Error", JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }
                    RentCard rentCard = (RentCard) selectedRentCard.remove(0);
                    rentCard.doubleTheRent();
                    player.getTurnInfo().getPerformingActionCard().remove(doubleTheRentCard);
                    controller.setActionPlayer(player);
                    cardSelectDialog.setVisible(false);
                    CardApi.putCardToCenter(player, doubleTheRentCard, controller);
                    player.setStatus(Player.Status.action);
                    parent.playerActionPerforming();
                });
                return;
            }
        }
        ActionCard actionCard = player.getTurnInfo().getPerformingActionCard().get(0);
        if (actionCard instanceof RentCard){
            RentCard rentCard = (RentCard) actionCard;
            this.setEnabled(false);
            PerformingRentActionDialog performingRentActionDialog = new PerformingRentActionDialog(actionCard);
            performingRentActionDialog.addConfirmAction(e->{
                List<Card> selectedPropertyCards = CardApi.getSelectedCard(rentCard.getSelectablePropertyCards());
                if (selectedPropertyCards.size()==0){
                    JOptionPane.showMessageDialog(null, "No property model.card is selected", "Error", JOptionPane.INFORMATION_MESSAGE);
                    this.setEnabled(true);
                    return;
                }
                PropertyCard propertyCard = (PropertyCard) selectedPropertyCards.remove(0);
                if (selectedPropertyCards.size()>1){
                    JOptionPane.showMessageDialog(null, "Only one property model.card can be selected", "Error", JOptionPane.INFORMATION_MESSAGE);
                    this.setEnabled(true);
                    return;
                }
                Property property = player.getProperty(propertyCard.getName());
                if (property==null){
                    JOptionPane.showMessageDialog(null, "You don't have the selected property model.card", "Error", JOptionPane.INFORMATION_MESSAGE);
                    this.setEnabled(true);
                }else {
                    List<Player> payingPlayers = controller.getPlayers().stream().filter(p -> p != player).collect(Collectors.toList());
                    PayingRequest payingRequest = new PayingRequest(player,property.getRentPrice()*rentCard.getRentFactor(),payingPlayers);
                    controller.acceptRequest(payingRequest);
                    CardApi.putCardToCenter(player, actionCard, controller);
                    player.setStatus(Player.Status.action);
                    parent.playerActionPerforming();
                    performingRentActionDialog.setVisible(false);
                    this.setEnabled(true);
                    player.getTurnInfo().getPerformingActionCard().remove(actionCard);
                }
            });
        }else {
            switch (actionCard.getName()){
                case "Pass Go":
                    controller.drawCardsToPlayer(player,2);
                    break;
                case "Debt Collector":
                    List<Player> payingPlayers = controller.getPlayers().stream().filter(p -> p != player).collect(Collectors.toList());
                    Player selectedPlayer = (Player)JOptionPane.showInputDialog(this, "Select targeted player", "Select player", 1, null, payingPlayers.toArray(), payingPlayers.get(0));
                    PayingRequest payingRequest = new PayingRequest(player,5,selectedPlayer);
                    controller.acceptRequest(payingRequest);
                    break;
                case "It's My Birthday":
                    payingPlayers = controller.getPlayers().stream().filter(p -> p != player).collect(Collectors.toList());
                    payingRequest = new PayingRequest(player,2,payingPlayers);
                    controller.acceptRequest(payingRequest);
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
                case "Force Deal":
                    List<Property> myProperties = player.getProperties().values().stream().filter(p -> p.getSetNumber() < p.getMaxSetNum()&&p.getSetNumber()>0).toList();
                    if (myProperties.isEmpty()){
                        JOptionPane.showMessageDialog(null, "You don't have any property that is not in full set", "Error", JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }
                    Property myProperty = (Property) JOptionPane.showInputDialog(this, "Select property", "Select property", 1, null, myProperties.toArray(), myProperties.get(0));
                    List<Player> players = controller.getPlayers().stream().filter(p -> p != player).collect(Collectors.toList());
                    selectedPlayer = (Player) JOptionPane.showInputDialog(this, "Select targeted player", "Select player", 1, null, players.toArray(), players.get(0));
                    List<Property> playerProperties = selectedPlayer.getProperties().values().stream().filter(p -> p.getSetNumber() < p.getMaxSetNum()&&p.getSetNumber()>0).toList();
                    if (playerProperties.isEmpty()){
                        JOptionPane.showMessageDialog(null, "Selected player doesn't have any property that is not in full set", "Error", JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }
                    Property playerProperty = (Property) JOptionPane.showInputDialog(this, "Select player's property", "Select property", 1, null, playerProperties.toArray(), playerProperties.get(0));
                    SwapRequest swapRequest = new SwapRequest(player,myProperty,selectedPlayer,playerProperty);
                    controller.acceptRequest(swapRequest);
                    break;
                case "Deal Breaker":
                    players = controller.getPlayers().stream().filter(p -> p != player).collect(Collectors.toList());
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
                    players = controller.getPlayers().stream().filter(p -> p != player).collect(Collectors.toList());
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
            controller.setActionPlayer(player);
            player.setStatus(Player.Status.action);
            parent.playerActionPerforming();
            player.getTurnInfo().getPerformingActionCard().remove(actionCard);
            CardApi.putCardToCenter(player, actionCard, controller);

        }
    }

    private  void addNewPropertyAction() {
        List<Card> handCards = player.getHandCards();
        ArrayList<Card> selectedCards = new ArrayList<>();
        for (Card handCard : handCards) {
            if (handCard.isSelected()){
                if (handCard instanceof PropertyCard){
                    selectedCards.add(handCard);
                }else {
                    JOptionPane.showMessageDialog(null, "Selected cards contain non-property model.card", "Error", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
            }
        }
        if (selectedCards.size()> player.getTurnInfo().cardAvailable){
            JOptionPane.showMessageDialog(null, "User has selected more than 3 three model.card in this turn", "Error", JOptionPane.INFORMATION_MESSAGE);
            return;
        }else {
            player.getTurnInfo().cardAvailable= player.getTurnInfo().cardAvailable-selectedCards.size();
        }

        Iterator<Card> iterator = selectedCards.iterator();
        while (iterator.hasNext()){
            Card selectedCard = iterator.next();
            if (selectedCard instanceof PropertyWildcard){
                player.getTurnInfo().addPropertyWildCard((PropertyWildcard)selectedCard);
                iterator.remove();
                handCards.remove(selectedCard);
            }
        }

        for (Card selectedCard : selectedCards) {
            selectedCard.setSelected(false);
            player.addProperty((PropertyCard)selectedCard);
            handCards.remove(selectedCard);
        }
        if (player.getTurnInfo().hasPropertyWildCard()){
            player.setStatus(Player.Status.decidingWildCardChange);
            new SelectPropertyFrame(player,this);
        }else {
            updatePlayer();
        }
    }

    private void depositAction() {
        List<Card> handCards = player.getHandCards();
        ArrayList<Card> selectedCards = new ArrayList<>();
        for (Card handCard : handCards) {
            if (handCard.isSelected()){
                if (handCard instanceof PropertyCard){
                    JOptionPane.showMessageDialog(null, "Cannot put a property model.card into bank", "Error", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
                selectedCards.add(handCard);
            }
        }
        if (selectedCards.size()> player.getTurnInfo().cardAvailable){
            JOptionPane.showMessageDialog(null, "User has selected more than 3 three model.card in this turn", "Error", JOptionPane.INFORMATION_MESSAGE);
            return;
        }else {
            player.getTurnInfo().cardAvailable= player.getTurnInfo().cardAvailable-selectedCards.size();
        }

        for (Card selectedCard : selectedCards) {
            selectedCard.setSelected(false);
            player.getBank().deposit(selectedCard);
            handCards.remove(selectedCard);
        }
        updatePlayer();
    }

    public void updateHandCards(){
        handCardPanel.removeAll();
        for (Card handCard : player.getHandCards()) {
//            handCard.setSelected(false);
            if (Player.Status.playing.equals(player.getStatus())||Player.Status.paying.equals(player.getStatus())){
                handCardPanel.add(new CardLabel(handCard));
            }else {
                handCardPanel.add(new CardLabel(Card.getCardBack()));
            }
        }
        handCardPanel.doLayout();
        handCardPanel.repaint();
    }

    public void updatePlayer(){
        if (Player.Status.playing.equals(player.getStatus())||Player.Status.action.equals(player.getStatus())){
            this.setBackground(Color.ORANGE);
        }else if (Player.Status.paying.equals(player.getStatus())){
            this.setBackground(Color.blue);
        }else {
            this.setBackground(Color.white);
        }
        updateHandCards();
        updateOperationPanel();
        updatePropertyPanel();
        updateBankPanel();
        updateInfoPanel();
        doLayout();
        repaint();
        if (controller.getStatus()==GameController.Status.playing&&player.getStatus() == Player.Status.action){
            performAction();
        }
    }

    private void updateInfoPanel() {
        infoPanel.updateInfo();
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
        }else if (Player.Status.paying.equals(player.getStatus())){
            operationPanel.enableAll(false);
            operationPanel.enablePaying(true);
        }else {
            operationPanel.enableAll(false);
        }
    }

    public void addConfirmAction(ActionListener listener) {
        operationPanel.addConfirmAction(listener);
    }

    public void addActionButtonAction(ActionListener listener){
        operationPanel.addActionButtonAction(listener);
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        operationPanel.enableAll(enabled);
    }


}
