package org.example;

import javax.swing.*;

/**
 * The CascadiaFrame class is a JFrame that will hold the CascadiaPanel. It represents the window that the game will be played in.
 * @author 0xc60f
 */
public class CascadiaFrame extends JFrame {

    public CascadiaFrame() {
        setTitle("Cascadia");
        setSize(1920, 1080);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(new CascadiaPanel());
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setVisible(true);
    }
}
