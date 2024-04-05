package org.example;

import javax.swing.*;

public class CascadiaFrame extends JFrame {

    public CascadiaFrame() {
        setTitle("Cascadia");
        setSize(getWidth(), getHeight());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //add(new StartPanel());
        add(new CascadiaPanel());
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setVisible(true);
    }
}
