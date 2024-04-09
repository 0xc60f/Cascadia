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
import org.example.CascadiaPanel.*;


public class MainBoardPanel extends JPanel implements MouseListener   {
    private Polygon start,rules;

    private BufferedImage backgroundImage, bearScoring, hawkScoring, salmonScoring, elkScoring, foxScoring;
    private boolean isVisible = true;
    public MainBoardPanel() {

        addMouseListener(this);
        importImages();
    }

    public void paint(Graphics g, int width, int height) {
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
        //Draw a large rectangle covering the bottom of the screen
        g.fillRect(0, height-height/div, width, height/div);
        drawScoring(g, width, height);

    }
    /**
     * Imports the images that are used in the game. The images are stored in the resources folder, and are imported using the ImageIO class.
     */
    private void importImages(){
        try{
            backgroundImage = ImageIO.read(Objects.requireNonNull(CascadiaPanel.class.getResource("/Menu/Background.jpg")));
            bearScoring = ImageIO.read(Objects.requireNonNull(CascadiaPanel.class.getResource("/Images/WildlifeTokens/bear-small.jpg")));
            hawkScoring = ImageIO.read(Objects.requireNonNull(CascadiaPanel.class.getResource("/Images/WildlifeTokens/hawk-small.jpg")));
            salmonScoring = ImageIO.read(Objects.requireNonNull(CascadiaPanel.class.getResource("/Images/WildlifeTokens/salmon-small.jpg")));
            elkScoring = ImageIO.read(Objects.requireNonNull(CascadiaPanel.class.getResource("/Images/WildlifeTokens/elk-small.jpg")));
            foxScoring = ImageIO.read(Objects.requireNonNull(CascadiaPanel.class.getResource("/Images/WildlifeTokens/fox-small.jpg")));
        }
        catch (IOException e){
            throw new RuntimeException(e);
        }
        bearScoring = CascadiaPanel.resizeImage(bearScoring, 160, 160);
        hawkScoring = CascadiaPanel.resizeImage(hawkScoring, 160, 160);
        salmonScoring = CascadiaPanel.resizeImage(salmonScoring, 160, 160);
        elkScoring = CascadiaPanel.resizeImage(elkScoring, 160, 160);
        foxScoring = CascadiaPanel.resizeImage(foxScoring, 160, 160);
    }

    /**
     * Draws the scoring on the right side of the screen. The scoring is drawn in the order of bear, hawk, salmon, elk, and fox.
     * @param g The <code>Graphics</code> object that is used to draw the scoring.
     * @param width The width of the screen.
     * @param height The height of the screen.
     */
    private void drawScoring(Graphics g, int width, int height){
        int div = 5;
        int x = width - width/div + width/div/2 - bearScoring.getWidth()/2;
        int y = height/div/2 - bearScoring.getHeight()/2;
        g.drawImage(bearScoring, x, y, null);
        y += height/div;
        g.drawImage(hawkScoring, x, y, null);
        y += height/div;
        g.drawImage(salmonScoring, x, y, null);
        y += height/div;
        g.drawImage(elkScoring, x, y, null);
        y += height/div;
        g.drawImage(foxScoring, x, y, null);
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
