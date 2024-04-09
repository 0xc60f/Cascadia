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

public class StartPanel extends JPanel implements MouseListener  {


    private Polygon start,rules;

    private boolean isVisible = true;
    public StartPanel() {
        addMouseListener(this);
    }

    public void paint(Graphics g, int width, int height) {
        super.paint(g);
        //Set the color of the hexagon
        setForeground(Color.RED);
        BufferedImage img;
        try {
            img = ImageIO.read(Objects.requireNonNull(StartPanel.class.getResource("/Menu/MainMenu.png")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        int xCenter = img.getWidth() / 2;
        int yCenter = img.getHeight() / 2;
        g.drawImage(img, xCenter - img.getWidth() / 2, yCenter - img.getHeight() / 2, width, height, null);

        int debugRectWidth = width/4;
        int debugRectHeight = height/7;
        int debugXPos = width/2 - debugRectWidth/2;
        int debugYPos = height/2 - (debugRectHeight / 7) * 5;

        int debugRectWidth2 = width/4;
        int debugRectHeight2 = height/7;
        int debugXPos2 = width/2 - debugRectWidth/2;
        int debugYPos2 = height/2 + debugRectHeight/3 + debugRectHeight/5;

        int[] xPoints = {debugXPos, debugXPos, debugXPos+debugRectWidth, debugXPos+debugRectWidth};
        int[] yPoints = {debugYPos+debugRectHeight, debugYPos, debugYPos, debugYPos+debugRectHeight};

        start = new Polygon(xPoints, yPoints, 4);
        g.drawPolygon(start);

        int[] xPoints2 = {debugXPos2, debugXPos2, debugXPos2+debugRectWidth2, debugXPos2+debugRectWidth2};
        int[] yPoints2 = {debugYPos2+debugRectHeight2, debugYPos2, debugYPos2, debugYPos2+debugRectHeight2};

        rules = new Polygon(xPoints2, yPoints2, 4);
        g.drawPolygon(rules);

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
        System.out.println("Whst");
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
