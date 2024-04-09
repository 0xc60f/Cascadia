package org.example;

import javax.swing.*;

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
