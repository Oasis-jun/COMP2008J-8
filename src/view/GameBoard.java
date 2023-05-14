package view;

import controller.GameController;

import javax.swing.*;
import java.awt.*;

public class GameBoard {


    int playerNum;
    private GameController controller;


    private GameBoardFrame gameBoardFrame;


    public GameBoard() throws HeadlessException {
        controller = new GameController();

    }


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
            Integer playerNum = Integer.valueOf((String) comboBox.getSelectedItem());
            controller.init(playerNum);
            gameBoardFrame = new GameBoardFrame(controller);
            menuFrame.setVisible(false);
            gameBoardFrame.setVisible(true);
        });
        menuFrame.setVisible(true);
        menuFrame.pack();
    }

    public static void main(String[] args) {
        new GameBoard().start();
    }


}
