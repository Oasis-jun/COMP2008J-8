package view;

import controller.GameController;

import javax.swing.*;
import java.awt.*;

public class GameBoard {

    /**
     * controller in MVC
     */
    private GameController controller;


    /**
     * view in MVC
     */
    private GameBoardFrame gameBoardFrame;


    public GameBoard() throws HeadlessException {
        controller = new GameController();

    }


    /**
     * the entrance of the game
     * Start a menu frame, select the number of people 
     * in the callback function to open the game's Main board frame
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

            /** 
             * Select the number of people in the callback function 
        	 * to open the game's Main board frame
        	 */ 
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
