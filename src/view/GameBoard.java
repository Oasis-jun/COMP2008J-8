package view;

import controller.GameController;

import javax.swing.*;
import java.awt.*;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class GameBoard {

    /**
     * MVC controller
     */
    private GameController controller;


    /**
     * MVC view
     */
    private GameBoardFrame gameBoardFrame;


    public GameBoard() throws HeadlessException {
        controller = new GameController();

    }


    /**
     * The entrance to the Monopoly game
     * Start a menu frame, select the number of people in the callback function to open the game's motherboard frame
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
        menuFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        next.addActionListener(e->{
            nextOperation(menuFrame, comboBox);
        });
        menuFrame.setVisible(true);
        // The pack function causes the size of the motherboard to be determined by the size of the child containers
        menuFrame.pack();
    }

    private void nextOperation(JFrame menuFrame, JComboBox<String> comboBox) {
        // Select the number of people in the callback function to open the game's motherboard frame
        Integer playerNum = Integer.valueOf((String) comboBox.getSelectedItem());
        controller.init(playerNum);
        // Enter the main game screen
        gameBoardFrame = new GameBoardFrame(controller);
        menuFrame.setVisible(false);
        gameBoardFrame.setVisible(true);
    }


}
