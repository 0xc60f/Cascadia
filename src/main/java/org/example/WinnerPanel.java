package org.example;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.Reader;
import java.util.Objects;

public class WinnerPanel extends JPanel implements MouseListener {


    private Polygon viewWinner;
    private BufferedImage backgroundImage;
    private boolean isVisible = true;
    private String winnerText = "Player X Wins!";

    public WinnerPanel() {
        addMouseListener(this);
    }

    public void paint(Graphics g, int width, int height) {
        super.paint(g);
        try {
            backgroundImage = ImageIO.read(Objects.requireNonNull(StartPanel.class.getResource("/Menu/Background.jpg")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Font defFont = new Font("Arial", Font.BOLD, width / 90);

        g.drawImage(backgroundImage, 0, 0, null);
        g.setFont(defFont);
        int debugRectWidth = width / 4;
        int debugRectHeight = height / 7;
        int debugXPos = width / 2 - debugRectWidth / 2;
        int debugYPos = height - (debugRectHeight / 2) * 5;
        Color beigeColor = new Color(255, 221, 122);

        int[] xPoints = {debugXPos, debugXPos, debugXPos + debugRectWidth, debugXPos + debugRectWidth};
        int[] yPoints = {debugYPos + debugRectHeight, debugYPos, debugYPos, debugYPos + debugRectHeight};

        viewWinner = new Polygon(xPoints, yPoints, 4);
        g.setColor(Color.black);
        g.fillRoundRect(debugXPos, debugYPos, debugRectWidth, debugRectHeight, 10, 10);
        g.setColor(beigeColor);
        g.fillRoundRect(debugXPos, debugYPos, debugRectWidth, debugRectHeight, 30, 30);

        int widthmult = 2;
        int heightmult = 2;

        g.fillRoundRect(debugXPos - (debugRectWidth * widthmult) / (2 * widthmult), debugYPos - debugRectHeight * heightmult - height / 8, debugRectWidth * widthmult, debugRectHeight * heightmult, 30, 30);

        int textx = debugXPos - (debugRectWidth * widthmult) / (2 * widthmult);
        int texty = debugYPos - debugRectHeight * heightmult - height / 8;

        Rectangle textAlign = new Rectangle(debugXPos - (debugRectWidth * widthmult) / (2 * widthmult), debugYPos - debugRectHeight * heightmult - height / 8, debugRectWidth * widthmult, debugRectHeight * heightmult);

        g.setColor(Color.black);

        drawCenteredString(g, winnerText, textAlign, defFont);

        /*
        g.setColor(Color.red);
        g.drawPolygon(viewWinner);
         */
        Rectangle scoreAlign = new Rectangle(width/2 - debugRectWidth/2, height - (debugRectHeight/2) * 5, width/4, height/7);

        drawCenteredString(g, "View Scores", scoreAlign, defFont);

    }

    public void drawCenteredString(Graphics g, String text, Rectangle rect, Font font) {
        // Get the FontMetrics
        FontMetrics metrics = g.getFontMetrics(font);
        // Determine the X coordinate for the text
        int x = rect.x + (rect.width - metrics.stringWidth(text)) / 2;
        // Determine the Y coordinate for the text (note we add the ascent, as in java 2d 0 is top of the screen)
        int y = rect.y + ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();
        // Set the font
        g.setFont(font);
        // Draw the String
        g.drawString(text, x, y);
    }


    public void setWinnerText(String wt) {
        winnerText = wt;
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
        if (viewWinner.contains(x, y)) {
            isVisible = false;
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
}
