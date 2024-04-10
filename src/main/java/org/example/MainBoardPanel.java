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
    private Polygon viewB1, viewB2;

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
        g.fillRoundRect(width/2 + width/6, height/100, width/8, height/14, 10, 10);
        g.fillRoundRect(width/2 + width/6, height/100 + height/14 + height/100, width/8, height/14, 10, 10);
        g.setColor(beigeColor);
        g.fillRect(width-width/div, 0, width/div, height);

        g.fillRoundRect(width/2 + width/6, height/100, width/8, height/14, 30, 30);

        g.fillRoundRect(width/2 + width/6, height/100 + height/14 + height/100, width/8, height/14, 30, 30);

        g.setColor(Color.BLACK);
        int text = width/2 + width/6 + width/23;

        g.drawString("Player 2", text, height/19); // Change text to change with turn
        g.drawString("Player 3", text, height/19 + height/14 + height/100);

        int debugRectWidth = width/8;
        int debugRectHeight = height/14;
        int debugXPos = width/2 + width/6;
        int debugYPos = height/100 + height/14 + height/100;

        int[] xPoints = {debugXPos, debugXPos, debugXPos+debugRectWidth, debugXPos+debugRectWidth};
        int[] yPoints = {debugYPos+debugRectHeight, debugYPos, debugYPos, debugYPos+debugRectHeight};

        viewB1 = new Polygon(xPoints, yPoints, 4);

        //g.drawPolygon(viewB1);

        int debugRectWidth2 = width/8;
        int debugRectHeight2 = height/14;
        int debugXPos2 = width/2 + width/6;
        int debugYPos2 = height/100;

        int[] xPoints2 = {debugXPos2, debugXPos2, debugXPos2+debugRectWidth2, debugXPos2+debugRectWidth2};
        int[] yPoints2 = {debugYPos2+debugRectHeight2, debugYPos2, debugYPos2, debugYPos2+debugRectHeight2};

        viewB2 = new Polygon(xPoints2, yPoints2, 4);

        //g.drawPolygon(viewB2);
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
        if (viewB1.contains(x, y)) {
            System.out.println("B1 Clicked");
        } else if (viewB2.contains(x, y)) {
            System.out.println("B2 Clicked");
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
