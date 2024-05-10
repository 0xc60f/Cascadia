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
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Objects;

public class WinnerPanel extends JPanel implements MouseListener {


    private Polygon viewWinner;
    private BufferedImage backgroundImage;
    private boolean isVisible = true;
    private Game game = null;
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

        Player p1 = game.getPlayers().getFirst();
        int p1score = p1.numNatureTokens() + p1.getBearTokenScore() + p1.getELkTokenScore() + p1.getFoxTokenScore() + p1.getHawkTokenScore() + p1.getSalmonTokenScore() + p1.getMountainScore(p1) + p1.getForestScore(p1) + p1.getDesertScore(p1) + p1.getSwampScore(p1) + p1.getLakeScore(p1) + p1.getMountainBonusScore() + p1.getForestBonusScore() + p1.getDesertBonusScore() + p1.getSwampBonusScore() + p1.getLakeBonusScore();
        Player p2 = game.getPlayers().get(1);
        int p2score = p2.numNatureTokens() + p2.getBearTokenScore() + p2.getELkTokenScore() + p2.getFoxTokenScore() + p2.getHawkTokenScore() + p2.getSalmonTokenScore() + p2.getMountainScore(p2) + p2.getForestScore(p2) + p2.getDesertScore(p2) + p2.getSwampScore(p2) + p2.getLakeScore(p2) + p2.getMountainBonusScore() + p2.getForestBonusScore() + p2.getDesertBonusScore() + p2.getSwampBonusScore() + p2.getLakeBonusScore();
        Player p3 = game.getPlayers().getLast();
        int p3score = p3.numNatureTokens() + p3.getBearTokenScore() + p3.getELkTokenScore() + p3.getFoxTokenScore() + p3.getHawkTokenScore() + p3.getSalmonTokenScore() + p3.getMountainScore(p3) + p3.getForestScore(p3) + p3.getDesertScore(p3) + p3.getSwampScore(p3) + p3.getLakeScore(p3) + p3.getMountainBonusScore() + p3.getForestBonusScore() + p3.getDesertBonusScore() + p3.getSwampBonusScore() + p3.getLakeBonusScore();

        //Compare the players using a comparator and print out which player has the highest score based on the score
        int highestScore = Math.max(Math.max(p1score, p2score), p3score);
        if (highestScore == p1score) {
            winnerText = "Player 1 Wins!";
        } else if (highestScore == p2score) {
            winnerText = "Player 2 Wins!";
        } else {
            winnerText = "Player 3 Wins!";
        }


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

    public void sendScore(Game game) {
        this.game = game;
    }
}
