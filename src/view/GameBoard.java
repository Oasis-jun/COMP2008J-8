package view;

import controller.GameController;

import javax.swing.*;
import java.awt.*;

public class GameBoard {

    /**
     * MVC中的controller
     */
    private GameController controller;


    /**
     * MVC中的view
     */
    private GameBoardFrame gameBoardFrame;


    public GameBoard() throws HeadlessException {
        controller = new GameController();

    }


    /**
     * 大富翁游戏的入口
     * 先启动一个菜单frame，选择人数后在回调函数里面打开游戏的主板frame
     */
    public void start(){
        JFrame menuFrame = new JFrame();
        JLabel label = new JLabel("Please select player number:");
        JButton next = new JButton("Next");
        JComboBox<String> comboBox = new JComboBox<>(new String[]{"2", "3", "4", "5"});
        menuFrame.add(label);
        menuFrame.add(comboBox);
        menuFrame.add(next);
        menuFrame.setLayout(new FlowLayout(FlowLayout.LEFT));
        next.addActionListener(e->{

            // 选择人数后在回调函数里面打开游戏的主板frame
            Integer playerNum = Integer.valueOf((String) comboBox.getSelectedItem());
            controller.init(playerNum);
            gameBoardFrame = new GameBoardFrame(controller);
            menuFrame.setVisible(false);
            gameBoardFrame.setVisible(true);
        });
        menuFrame.setVisible(true);
        menuFrame.pack();
    }




}
