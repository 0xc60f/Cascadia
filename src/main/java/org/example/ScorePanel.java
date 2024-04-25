package org.example;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class ScorePanel extends JPanel implements MouseListener  {


    private Polygon viewWinner;
    private BufferedImage backgroundImage, scoringTable, bearScoring, hawkScoring, salmonScoring, elkScoring, foxScoring;
    private boolean isVisible = true;
    public ScorePanel() {
        addMouseListener(this);
    }

    public void paint(Graphics g, int width, int height) {
        super.paint(g);
        try {
            backgroundImage = ImageIO.read(Objects.requireNonNull(StartPanel.class.getResource("/Menu/Background.jpg")));
            scoringTable = ImageIO.read(Objects.requireNonNull(StartPanel.class.getResource("/Images/full-scoring-table.jpg")));
            bearScoring = ImageIO.read(Objects.requireNonNull(CascadiaPanel.class.getResource("/Images/WildlifeTokens/bear-small.jpg")));
            hawkScoring = ImageIO.read(Objects.requireNonNull(CascadiaPanel.class.getResource("/Images/WildlifeTokens/hawk-small.jpg")));
            salmonScoring = ImageIO.read(Objects.requireNonNull(CascadiaPanel.class.getResource("/Images/WildlifeTokens/salmon-small.jpg")));
            elkScoring = ImageIO.read(Objects.requireNonNull(CascadiaPanel.class.getResource("/Images/WildlifeTokens/elk-small.jpg")));
            foxScoring = ImageIO.read(Objects.requireNonNull(CascadiaPanel.class.getResource("/Images/WildlifeTokens/fox-small.jpg")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Color beigeColor = new Color(255, 221, 122);

        Font defFont = new Font("Arial", Font.BOLD, width/90);
        Font smallFont = new Font("Arial", Font.BOLD, width/120);

        g.drawImage(backgroundImage, 0, 0, null);

        int scorepatH = height/4;

        int scorepatW = width/7;

        int scorepatY = height-height/80 - scorepatH;

        int scorepatX = scorepatW;

        g.drawImage(bearScoring, scorepatX, scorepatY, scorepatW, scorepatH, null);
        g.drawImage(hawkScoring, 2*scorepatX, scorepatY, scorepatW, scorepatH, null);
        g.drawImage(salmonScoring, 3*scorepatX, scorepatY, scorepatW, scorepatH, null);
        g.drawImage(elkScoring, 4*scorepatX, scorepatY, scorepatW, scorepatH, null);
        g.drawImage(foxScoring, 5*scorepatX, scorepatY, scorepatW, scorepatH, null);


        int labelWidth = width/4;
        int xdis = width/6 + labelWidth/2;

        int scoreWidth = width/5;
        int scorexdis = width/6 + scoreWidth/2;


        g.setColor(beigeColor);
        g.fillRoundRect(width/3 - xdis, height/20, labelWidth, height/15, 30, 30);
        g.drawImage(scoringTable, width/3 - scorexdis,   height/40 + height/20 + height/8, scoreWidth, height/2, null);
        g.fillRoundRect(2 * width/3 - xdis, height/20, labelWidth, height/15, 30, 30);
        g.drawImage(scoringTable, 2 * width/3 - scorexdis,   height/40 + height/20 + height/8, scoreWidth, height/2, null);
        g.fillRoundRect(width - xdis, height/20, labelWidth, height/15, 30, 30);
        g.drawImage(scoringTable, width - scorexdis,   height/40 + height/20 + height/8, scoreWidth, height/2, null);

        int labelWidth2 = width/9;
        int xdis2 = width/6 + labelWidth2/2;

        g.setColor(Color.black);
        g.fillRoundRect(width/3 - xdis2, height/8, labelWidth2, height/20, 10, 10);
        g.fillRoundRect(2 * width/3 - xdis2, height/8, labelWidth2, height/20, 10, 10);
        g.fillRoundRect(width - xdis2, height/8, labelWidth2, height/20, 10, 10);

        g.setColor(beigeColor);
        g.fillRoundRect(width/3 - xdis2, height/8, labelWidth2, height/20, 30, 30);
        g.fillRoundRect(2 * width/3 - xdis2, height/8, labelWidth2, height/20, 30, 30);
        g.fillRoundRect(width - xdis2, height/8, labelWidth2, height/20, 30, 30);


        Rectangle p1label, p2label, p3label, p1button, p2button, p3button;

        p1label = new Rectangle(width/3 - xdis, height/20, labelWidth, height/15);
        p1button = new Rectangle(width/3 - xdis2, height/8, labelWidth2, height/20);
        p2label = new Rectangle(2 * width/3 - xdis, height/20, labelWidth, height/15);
        p2button = new Rectangle(2 * width/3 - xdis2, height/8, labelWidth2, height/20);
        p3label = new Rectangle(width - xdis, height/20, labelWidth, height/15);
        p3button = new Rectangle(width - xdis2, height/8, labelWidth2, height/20);


        g.setColor(Color.black);
        drawCenteredString(g, "Player 1", p1label, defFont);
        drawCenteredString(g, "Player 2", p2label, defFont);
        drawCenteredString(g, "Player 3", p3label, defFont);
        drawCenteredString(g, "View Board", p1button, smallFont);
        drawCenteredString(g, "View Board", p2button, smallFont);
        drawCenteredString(g, "View Board", p3button, smallFont);
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
        /*if (viewWinner.contains(x, y)) {
            isVisible = false;
        }*/
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
