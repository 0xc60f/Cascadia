package org.example;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

public class ScorePanel extends JPanel implements MouseListener  {


    private Polygon vb1, vb2, vb3, viewPage;
    private BufferedImage backgroundImage, scoringTable, bearScoring, hawkScoring, salmonScoring, elkScoring, foxScoring;
    private boolean boardVisible = false;
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

        ArrayList<Integer> test = new ArrayList<Integer>();
        test.add(1); test.add(1); test.add(1); test.add(1); test.add(1); test.add(1); test.add(1); test.add(1); test.add(1); test.add(1);
        test.add(1); test.add(1); test.add(1); test.add(1); test.add(1); test.add(1); test.add(1); test.add(1); test.add(1); test.add(1);

        drawScores(g,width/3 - scorexdis,height/40 + height/20 + height/8, scoreWidth, height/2, test);

        g.fillRoundRect(2 * width/3 - xdis, height/20, labelWidth, height/15, 30, 30);
        g.drawImage(scoringTable, 2 * width/3 - scorexdis,   height/40 + height/20 + height/8, scoreWidth, height/2, null);
        drawScores(g,2 * width/3 - scorexdis,   height/40 + height/20 + height/8, scoreWidth, height/2, test);
        g.fillRoundRect(width - xdis, height/20, labelWidth, height/15, 30, 30);
        g.drawImage(scoringTable, width - scorexdis,   height/40 + height/20 + height/8, scoreWidth, height/2, null);
        drawScores(g,width - scorexdis,height/40 + height/20 + height/8, scoreWidth, height/2, test);

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

        int debugRectWidth = labelWidth2;
        int debugRectHeight = height/20;
        int debugXPos = width/3 - xdis2;
        int debugYPos = height/8;

        int[] xPoints = {debugXPos, debugXPos, debugXPos + debugRectWidth, debugXPos + debugRectWidth};
        int[] yPoints = {debugYPos + debugRectHeight, debugYPos, debugYPos, debugYPos + debugRectHeight};

        debugXPos = 2*width/3 - xdis2;

        int[] xPoints2 = {debugXPos, debugXPos, debugXPos + debugRectWidth, debugXPos + debugRectWidth};
        int[] yPoints2 = {debugYPos + debugRectHeight, debugYPos, debugYPos, debugYPos + debugRectHeight};

        debugXPos = width - xdis2;

        int[] xPoints3 = {debugXPos, debugXPos, debugXPos + debugRectWidth, debugXPos + debugRectWidth};
        int[] yPoints3 = {debugYPos + debugRectHeight, debugYPos, debugYPos, debugYPos + debugRectHeight};


        int debugRectWidth3 = width/2;
        int debugRectHeight3 = 2 * height/3;
        int debugXPos3 = width/2 - debugRectWidth3/2;
        int debugYPos3 = height/2 - debugRectHeight3/2;

        int[] xPoints4 = {debugXPos3, debugXPos3, debugXPos3 + debugRectWidth3, debugXPos3 + debugRectWidth3};
        int[] yPoints4 = {debugYPos3 + debugRectHeight3, debugYPos3, debugYPos3, debugYPos3 + debugRectHeight3};

        viewPage = new Polygon(xPoints4, yPoints4, 4);

        if (boardVisible) {
            g.setColor(beigeColor);
            g.fillPolygon(viewPage);
        }

        vb1 = new Polygon(xPoints, yPoints, 4);
        vb2 = new Polygon(xPoints2, yPoints2, 4);
        vb3 = new Polygon(xPoints3, yPoints3, 4);

        /*
        g.drawPolygon(vb1);
        g.drawPolygon(vb2);
        g.drawPolygon(vb3);
        */

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

    public void drawScores(Graphics g, int x, int y, int w, int h, ArrayList<Integer> score) {

        System.out.println(x);
        g.setColor(Color.black);
        int boxWidth = w/5;
        int boxHeight = h/8;
        Font smallFont = new Font("Arial", Font.BOLD, w/15);
        Color beigeColor = new Color(255, 221, 122);
        int xmult = 1;
        int ymult = 0;
        for (int i = 0; i < score.size(); i++){
            if (i == 7) {
                xmult = 2;
            }
            if (i == 8) {
                xmult = 3;
                ymult = 1;
            } else if (i == 14) {
                xmult = 4;
                ymult = 1;
            } else if (i == 20) {
                xmult = 5;
                ymult = 1;
            }
            /*
            g.setColor(beigeColor);
            g.drawRect(x+boxWidth*xmult, y + boxHeight*ymult, boxWidth, h/8);
            */
            Rectangle r = new Rectangle(x+boxWidth*xmult, y + boxHeight*ymult, boxWidth, h/8);
            drawCenteredString(g, score.get(i).toString(), r, smallFont);
            ymult++;
        }
        g.setColor(beigeColor);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        if (vb1.contains(x, y)) {
            boardVisible = true;
        } else if (vb2.contains(x, y)) {
            boardVisible = true;
        } else if(vb3.contains(x, y)) {
            boardVisible = true;
        } else if (viewPage.contains(x, y)) {
            boardVisible = false;
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