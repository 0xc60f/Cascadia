package org.example;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;


public class MainBoardPanel extends JPanel implements MouseListener   {
    private Polygon start,rules;

    private BufferedImage backgroundImage;
    private boolean isVisible = true;
    public MainBoardPanel() {

        addMouseListener(this);
        try {
            backgroundImage = ImageIO.read(Objects.requireNonNull(CascadiaPanel.class.getResource("/Menu/MainMenu.png")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void paint(Graphics g, int width, int height) {
        try {
            backgroundImage = ImageIO.read(Objects.requireNonNull(CascadiaPanel.class.getResource("/Menu/Background.jpg")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        g.drawImage(backgroundImage, 0, 0, null);
        Color beigeColor = new Color(255, 221, 122);
        g.setColor(beigeColor);
        g.fillRoundRect(width/100, height/100, width/8, height/14, 30, 30);
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, width/90));
        g.drawString("Turn: 1", width/19, height/19);

        int div = 5;

        g.drawRect(width-width/div, 0, width/div, height);
        g.setColor(beigeColor);
        g.fillRect(width-width/div, 0, width/div, height);

    }


    public void setVisible(boolean val) {
        isVisible = val;
    }
    public boolean getVisible() {
        return isVisible;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        if (start.contains(x, y)) {
            isVisible = false;
            System.out.println("Game started");
        } else if (rules.contains(x, y)) {
            downloadRules();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    public void downloadRules() {
        String url = "https://www.alderac.com/wp-content/uploads/2021/08/Cascadia-Rules.pdf";
        String home = System.getProperty("user.home");
        System.out.println("Downloading Rules");
        try {
            java.awt.Desktop.getDesktop().browse(java.net.URI.create("https://www.alderac.com/wp-content/uploads/2021/08/Cascadia-Rules.pdf"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
