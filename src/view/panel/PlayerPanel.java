package view.panel;

import config.GameConfig;
import model.card.*;
import controller.GameController;
import model.player.*;
import view.GameBoard;
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
    private final int width = 2000;
    private final int height = 200;


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
        setMaximumSize(new Dimension((int) (width* GameConfig.SIZE_FACTOR), (int) (height*GameConfig.SIZE_FACTOR)));
        // 下面为每个按钮创建点击事件
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
            updatePlayer();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void payActionButton() {
        try {
            GameRequest gameRequest = player.getGameRequest();
            boolean justSayNo =controller.payWithJustSayNo(player);
            if (!justSayNo){
                gameRequest.execute(player);
            }
            parent.playerActionPerforming();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.INFORMATION_MESSAGE);
        }
    }


    private  void actionButtonAction() {
        try {
            controller.playerAction(player);
            performAction();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.INFORMATION_MESSAGE);
        }
    }


    private void performAction() {
        // 由于double the rent必须要跟rent card一起用，所以首先判断是否存在double the rent card
        if (executeDoubleTheRentCard()){
            return;
        }

        ActionCard actionCard = player.getTurnInfo().getPerformingActionCard().get(0);
        // 如果是rent card则跳出rent card的颜色选择框，并且定义confirm函数
        if (actionCard instanceof RentCard){
            RentCard rentCard = (RentCard) actionCard;
            this.setEnabled(false);
            PerformingRentActionDialog performingRentActionDialog = new PerformingRentActionDialog(actionCard);
            // 并且定义confirm函数
            performingRentActionDialog.addConfirmAction(e->{
                try {
                    controller.playerRentAction(player,rentCard);
                    parent.playerActionPerforming();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.INFORMATION_MESSAGE);
                }
                performingRentActionDialog.setVisible(false);
                this.setEnabled(true);
            });
        }else {
            // 如果不是rent card，则根据action card的name执行相应的逻辑，由于有需要交互，所以不适合把逻辑写在controller里面
            List<Player> players = controller.getPlayers().stream().filter(p -> p != player).collect(Collectors.toList());
            switch (actionCard.getName()){
                case "Pass Go":
                    controller.playerPassGoAction(player);
                    break;
                case "Debt Collector":
                    Player selectedPlayer = (Player)JOptionPane.showInputDialog(this, "Select targeted player", "Select player", 1, null, players.toArray(), players.get(0));
                    controller.playerAskPayAction(player,5,selectedPlayer);
                    break;
                case "It's My Birthday":
                    controller.playerAskPayAction(player,2,players);
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
            // 成功执行action card后设置一些列状态 只有成功执行了 才从相应的位置remove card
            player.getTurnInfo().getPerformingActionCard().remove(actionCard);
            CardApi.putCardToCenter(player, actionCard, controller);
            player.getTurnInfo().cardAvailable= player.getTurnInfo().cardAvailable-1;
            parent.playerActionPerforming();

        }
    }

    private boolean executeDoubleTheRentCard() {
        // 由于double the rent必须要跟rent card一起用，所以首先判断是否存在double the rent card
        Optional<ActionCard> doubleTheRentOpt = player.getTurnInfo().getPerformingActionCard().stream().filter(a -> a.getName().equals("Double The Rent")).findAny();
        if (doubleTheRentOpt.isPresent()){
            ActionCard doubleTheRentCard = doubleTheRentOpt.get();
            List<Card> availableRentCard=CardApi.getRentCards(player.getTurnInfo().getPerformingActionCard());
            // 如果存在double the rent 但是不存在rent card则报错返回
            if (availableRentCard.isEmpty()){
                JOptionPane.showMessageDialog(null, "'Double The Rent' card must be used with rent card", "Error", JOptionPane.INFORMATION_MESSAGE);
                this.setEnabled(true);
            }else {
                // 如果存在的话，就给这个card的rent card的rent factor设置值为2 供使用这张卡的时候计算
                CardSelectDialog cardSelectDialog = new CardSelectDialog(availableRentCard);
                cardSelectDialog.addConfirmAction(e->{
                    try {
                        // 如果存在的话，就给这个card的rent card的rent factor设置值为2 供使用这张卡的时候计算
                        controller.playerDoubleARentCard(player,availableRentCard,doubleTheRentCard);
                        cardSelectDialog.setVisible(false);
                        //
                        parent.playerActionPerforming();
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
            if (player.getTurnInfo().hasPropertyWildCard()){
                player.setStatus(Player.Status.decidingWildCardChange);
                new SelectPropertyFrame(player,this);
            }else {
                updatePlayer();
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void depositAction() {
        try {
            controller.playerDeposit(player);
            updatePlayer();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void updateHandCards(){
        handCardPanel.removeAll();
        for (Card handCard : player.getHandCards()) {
            handCard.setSelected(false);
            // 允许出牌的玩家和paying的玩家看自己的牌
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
     * 更新玩家的游戏状态
     */
    public void updatePlayer(){
        if (Player.Status.playing.equals(player.getStatus())||Player.Status.action.equals(player.getStatus())){
            this.setBackground(Color.ORANGE);
        }else if (Player.Status.paying.equals(player.getStatus())){
            this.setBackground(Color.blue);
        }else {
            this.setBackground(Color.white);
        }
        // 一些列的面板更新操作
        updateHandCards();
        // 里面有很多游戏的操作逻辑
        updateOperationPanel();
        updatePropertyPanel();
        updateBankPanel();
        updateInfoPanel();
        doLayout();
        repaint();
        // 当controller的状态变成playing 说明当前操作的玩家就是选择出牌的玩家
        // 如果当前出牌的玩家的状态还是action 说明当前出牌的玩家还有action card正需要处理
        if (controller.getStatus()==GameController.Status.playing&&player.getStatus() == Player.Status.action){
            performAction();
        }
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
        parent.updatePlayerPanelList();
        parent.doLayout();
        parent.repaint();
    }


    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        operationPanel.enableAll(enabled);
    }


}
