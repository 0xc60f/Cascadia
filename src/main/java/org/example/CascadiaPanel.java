package org.example;

import org.jetbrains.annotations.NotNull;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

/**
 * The main panel for the Cascadia game. Currently, being used to test and debug tile setups, but will eventually be switched to orchestrate
 * the game, and the end and start screen.
 *
 * @author 0xc60f
 * @see JPanel
 */
public class CascadiaPanel extends JPanel implements MouseListener {
    private Polygon hexagon, hexagon2;
    private StartPanel Menu;
    public CascadiaPanel() {
        add(new JLabel("Hello world!"));
        addMouseListener(this);
        Menu = new StartPanel();
    }

    public void paint(Graphics g) {
        super.paint(g);

        if (Menu.getVisible()) {
            StartPanelSetUp(g);
            Menu.paint(g);
        } else {
            CascadiaPanelSetup(g);
        }
        //Set the color of the hexagon
        setForeground(Color.RED);
        BufferedImage img, animalTile, img2, animalTile2, animalTile3, img3;
        try {
            img = ImageIO.read(Objects.requireNonNull(CascadiaPanel.class.getResource("/Images/Tiles/02.png")));
            animalTile = ImageIO.read(Objects.requireNonNull(CascadiaPanel.class.getResource("/Images/WildlifeTokens/1.png")));
            img2 = ImageIO.read(Objects.requireNonNull(CascadiaPanel.class.getResource("/Images/Tiles/03.png")));
            animalTile2 = ImageIO.read(Objects.requireNonNull(CascadiaPanel.class.getResource("/Images/WildlifeTokens/2.png")));
            animalTile3 = ImageIO.read(Objects.requireNonNull(CascadiaPanel.class.getResource("/Images/WildlifeTokens/3.png")));
            img3 = ImageIO.read(Objects.requireNonNull(CascadiaPanel.class.getResource("/Images/Tiles/08.png")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //g.drawImage(img, getWidth()/2, getHeight()/2, this);
        int x = hexagon.getBounds().x;
        int y = hexagon.getBounds().y;
        int xCenter = x + hexagon.getBounds().width / 2;
        int yCenter = y + hexagon.getBounds().height / 2;
        //Draw the image on the hexagon
        g.drawImage(img, xCenter - img.getWidth() / 2, yCenter - img.getHeight() / 2, this);
        //g.drawPolygon(hexagon);
        BufferedImage combined = drawTiles(img, animalTile);
        BufferedImage combined2 = drawTiles(img2, animalTile2, animalTile3);
        BufferedImage combined3 = drawTiles(img3, animalTile, animalTile2, animalTile3);
        //g.drawImage(combined, getWidth() / 4, getHeight() / 4, null);
        //g.drawImage(combined2, getWidth() / 2, getHeight() / 4, null);
        //g.drawImage(combined3, getWidth() / 4, getHeight() / 2, null);
        BufferedImage rotateCombined3 = rotateImageByDegrees(combined3, 60);
        //g.drawImage(rotateCombined3, getWidth() / 7, getHeight() / 2, null);

        BufferedImage occupiedTile = drawOccupiedTile(rotateCombined3, animalTile3);
        g.drawImage(occupiedTile, getWidth() / 3, getHeight() / 3, null);
        //Find the center of combined and make a polygon around it
        hexagon2 = new Polygon();
        for (int i = 0; i < 6; i++) {
            hexagon2.addPoint((int) (getWidth() / 2 + 60 * Math.cos(i * 2 * Math.PI / 6)),
                    (int) (getHeight() / 4 + 60 * Math.sin(i * 2 * Math.PI / 6)));
        }

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();

        if (Menu.getVisible()) {
            if (start.contains(x, y)) {
                Menu.setVisible(false);
                System.out.println("Game started");
            } else if (rules.contains(x, y)) {
                Menu.downloadRules();
            }
        } else {
            if (hexagon.contains(x, y)) {
                getGraphics().drawString("Clicked", 400, 200);
            } else {
                getGraphics().drawString("Not clicked", 400, 200);
            }
        }
        repaint();
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

    private void StartPanelSetUp(Graphics g) {

        int debugRectWidth = getWidth()/4;
        int debugRectHeight = getHeight()/7;
        int debugXPos = getWidth()/2 - debugRectWidth/2;
        int debugYPos = getHeight()/2 - debugRectHeight/2;

        int debugRectWidth2 = getWidth()/4;
        int debugRectHeight2 = getHeight()/7;
        int debugXPos2 = getWidth()/2 - debugRectWidth/2;
        int debugYPos2 = getHeight()/2 + debugRectHeight/2 + debugRectHeight/3;

        int[] xPoints = {debugXPos, debugXPos, debugXPos+debugRectWidth, debugXPos+debugRectWidth};
        int[] yPoints = {debugYPos+debugRectHeight, debugYPos, debugYPos, debugYPos+debugRectHeight};

        start = new Polygon(xPoints, yPoints, 4);
        g.drawPolygon(start);

        int[] xPoints2 = {debugXPos2, debugXPos2, debugXPos2+debugRectWidth2, debugXPos2+debugRectWidth2};
        int[] yPoints2 = {debugYPos2+debugRectHeight2, debugYPos2, debugYPos2, debugYPos2+debugRectHeight2};

        rules = new Polygon(xPoints2, yPoints2, 4);
        g.drawPolygon(rules);
        System.out.println("check");
    }

    private void CascadiaPanelSetup(Graphics g) {
        hexagon = new Polygon();
        for (int i = 0; i < 6; i++) {
            hexagon.addPoint((int) (100 + 60 * Math.cos(i * 2 * Math.PI / 6)),
                    (int) (100 + 60 * Math.sin(i * 2 * Math.PI / 6)));
        }
        //Set the color of the hexagon
        setForeground(Color.RED);
        BufferedImage img;
        try {
            img = ImageIO.read(Objects.requireNonNull(CascadiaPanel.class.getResource("/Images/Tiles/desert+swamp.png")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //g.drawImage(img, getWidth()/2, getHeight()/2, this);
        BufferedImage newImg = rotateImageByDegrees(img, 90);
        int x = hexagon.getBounds().x;
        int y = hexagon.getBounds().y;
        int xCenter = x + hexagon.getBounds().width / 2;
        int yCenter = y + hexagon.getBounds().height / 2;
        //Draw the image on the hexagon
        g.drawImage(newImg, xCenter - newImg.getWidth() / 2, yCenter - newImg.getHeight() / 2, this);
        //g.drawPolygon(hexagon);
    }

    /**
     * Draws a single animal tile on top of a habitat tile. The animal tile is drawn so that it covers up the possible options for the habitat tile.
     * This is used when a player places an animal tile on a habitat tile.
     * @param habitatTile The <code>BufferedImage</code> of the habitat tile
     * @param animalTile The <code>BufferedImage</code> of the animal tile that will be placed on top of the habitat tile
     * @return A <code>BufferedImage</code> that is the result of drawing the animal tile on top of the habitat tile.
     */
    public BufferedImage drawOccupiedTile(BufferedImage habitatTile, BufferedImage animalTile) {
        BufferedImage combined = new BufferedImage(habitatTile.getWidth(), habitatTile.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = combined.createGraphics();
        g2d.drawImage(habitatTile, 0, 0, null);
        //Draw the animal tile on top of the habitat tile in the center
        //Resize animalTile so that it is half of the original size
        animalTile = resizeImage(animalTile, (int) (animalTile.getWidth() / 1.1), (int) (animalTile.getHeight() / 1.1));
        g2d.drawImage(animalTile, (habitatTile.getWidth() - animalTile.getWidth()) / 2, (habitatTile.getHeight() - animalTile.getHeight()) / 2, null);
        return combined;
    }

    /**
     * Draws a new hexagon at the edge of the current hexagon. The new hexagon is drawn so that it is touching the edge of the current hexagon.
     * @param g The <code>Graphics</code> object that is used to draw the hexagon. Should be called with <code>getGraphics()</code>
     * @param baseTilePolygon The <code>Polygon</code> of the base tile that the new tile will be drawn with respect to.
     * @param tileToDrawPolygon The <code>Polygon</code> of the tile that will be drawn at the edge of the base tile.
     * @param tileToDraw The <code>BufferedImage</code> of the tile that will be drawn at the edge of the base tile.
     * @param edge The edge of the base tile that the new tile will be drawn on. Should be a value from 0 to 5.
     *             0 is the top edge, and the edges are numbered clockwise.
     */
    public void drawTileAtEdge(Graphics g, Polygon baseTilePolygon, Polygon tileToDrawPolygon, BufferedImage tileToDraw, int edge){
        Point center = getCenterOfHexagon(baseTilePolygon);
        Point edgePoint = new Point(baseTilePolygon.xpoints[edge], baseTilePolygon.ypoints[edge]);

    }

    /**
     * Draws a single animal tile on top of a habitat tile. The animal tile is resized to half of its original size, and then drawn in the center of the habitat tile.
     * @param habitatTile A <code>BufferedImage</code> of the habitat tile
     * @param animalTile A <code>BufferedImage</code> of the animal tile
     * @return A <code>BufferedImage</code> that is the result of drawing the animal tile on top of the habitat tile.
     */
    private BufferedImage drawTiles(BufferedImage habitatTile, BufferedImage animalTile) {
        BufferedImage combined = new BufferedImage(habitatTile.getWidth(), habitatTile.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = combined.createGraphics();
        g2d.drawImage(habitatTile, 0, 0, null);
        //Draw the animal tile on top of the habitat tile in the center
        //Resize animalTile so that it is half of the original size
        animalTile = resizeImage(animalTile, animalTile.getWidth() / 2, animalTile.getHeight() / 2);
        g2d.drawImage(animalTile, (habitatTile.getWidth() - animalTile.getWidth()) / 2, (habitatTile.getHeight() - animalTile.getHeight()) / 2, null);
        return combined;
    }

    /**
     * Finds the center of a hexagon by finding the center of the bounding rectangle of the hexagon.
     * @param hexagon The <code>Polygon</code> that represents the hexagon
     * @return A <code>Point</code> that represents the center of the hexagon. You can use getX() and getY() to get the x and y coordinates of the center.
     */
    private Point getCenterOfHexagon(Polygon hexagon) {
        int x = hexagon.getBounds().x;
        int y = hexagon.getBounds().y;
        int xCenter = x + hexagon.getBounds().width / 2;
        int yCenter = y + hexagon.getBounds().height / 2;
        return new Point(xCenter, yCenter);
    }

    /**
     * Draws a habitat tile with two animal tiles on top of it. The animal tiles are resized to half of their original size, and then drawn in the center of the habitat tile.
     * The two animal tiles are slightly offset from each other in the center.
     * @param habitatTile The <code>BufferedImage</code> of the habitat tile
     * @param animalTile1 The <code>BufferedImage</code> of the first animal tile
     * @param animalTile2 The <code>BufferedImage</code> of the second animal tile
     * @return A <code>BufferedImage</code> that is the result of drawing the two animal tiles on top of the habitat tile.
     */
    private BufferedImage drawTiles(BufferedImage habitatTile, BufferedImage animalTile1, BufferedImage animalTile2) {
        BufferedImage combined = new BufferedImage(habitatTile.getWidth(), habitatTile.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = combined.createGraphics();
        g2d.drawImage(habitatTile, 0, 0, null);
        //Draw the animal tile on top of the habitat tile in the center
        //Resize animalTile so that it is half of the original size
        animalTile1 = resizeImage(animalTile1, animalTile1.getWidth() / 2, animalTile1.getHeight() / 2);
        animalTile2 = resizeImage(animalTile2, animalTile2.getWidth() / 2, animalTile2.getHeight() / 2);
        //Draw the two tiles in the middle of the habitat tile, slightly offset from each other
        g2d.drawImage(animalTile1, (habitatTile.getWidth() - animalTile1.getWidth()) / 2 - 10, (habitatTile.getHeight() - animalTile1.getHeight()) / 2, null);
        g2d.drawImage(animalTile2, (habitatTile.getWidth() - animalTile2.getWidth()) / 2 + 10, (habitatTile.getHeight() - animalTile2.getHeight()) / 2, null);
        return combined;
    }

    /**
     * Draws a habitat tile with three animal tiles on top of it. The animal tiles are resized to half of their original size, and then drawn in the center of the habitat tile.
     * The three animal tiles are slightly offset from each other in the center to look like a triangle.
     * @param habitatTile The <code>BufferedImage</code> of the habitat tile
     * @param animalTile1 The <code>BufferedImage</code> of the first animal tile
     * @param animalTile2 The <code>BufferedImage</code> of the second animal tile
     * @param animalTile3 The <code>BufferedImage</code> of the third animal tile
     * @return A <code>BufferedImage</code> that is the result of drawing the three animal tiles on top of the habitat tile.
     */
    private BufferedImage drawTiles(BufferedImage habitatTile, BufferedImage animalTile1, BufferedImage animalTile2, BufferedImage animalTile3) {
        BufferedImage combined = new BufferedImage(habitatTile.getWidth(), habitatTile.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = combined.createGraphics();
        g2d.drawImage(habitatTile, 0, 0, null);
        //Draw the animal tile on top of the habitat tile in the center
        //Resize animalTile so that it is half of the original size
        animalTile1 = resizeImage(animalTile1, animalTile1.getWidth() / 2, animalTile1.getHeight() / 2);
        animalTile2 = resizeImage(animalTile2, animalTile2.getWidth() / 2, animalTile2.getHeight() / 2);
        animalTile3 = resizeImage(animalTile3, animalTile3.getWidth() / 2, animalTile3.getHeight() / 2);
        //Draw the two tiles in the middle of the habitat tile, slightly offset from each other
        g2d.drawImage(animalTile1, (habitatTile.getWidth() - animalTile1.getWidth()) / 2 - 10, ((habitatTile.getHeight() - animalTile1.getHeight()) / 2) + 5, null);
        g2d.drawImage(animalTile2, (habitatTile.getWidth() - animalTile2.getWidth()) / 2 + 10, ((habitatTile.getHeight() - animalTile2.getHeight()) / 2) + 5, null);
        g2d.drawImage(animalTile3, (habitatTile.getWidth() - animalTile3.getWidth()) / 2, (habitatTile.getHeight() - animalTile3.getHeight()) / 2 - 15, null);
        return combined;
    }

    /**
     * Resizes an image to a specified width and height. Uses getScaledInstance to resize the image, and then draws the
     * new image onto a BufferedImage. Returns the resized image.
     * @param img The image to be resized as a <code>BufferedImage</code>
     * @param width The width that the image should be resized to as an <code>int</code>
     * @param height The height that the image should be resized to as an <code>int</code>
     * @return A <code>BufferedImage</code> that is the result of resizing the original image
     */
    @NotNull
    private BufferedImage resizeImage(BufferedImage img, int width, int height) {
        Image tmp = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = resized.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();
        return resized;
    }

    /**
     * Rotates an image by a specified number of degrees. Makes a copy of the original image, and then
     * uses an AffineTransform to rotate the copy. Returns the rotated copy.
     *
     * @param img   The image to be rotated as a BufferedImage
     * @param angle The angle that the image should be rotated, in degrees
     * @return A BufferedImage that is the result of rotating the original image
     * @see AffineTransform
     */
    public BufferedImage rotateImageByDegrees(@org.jetbrains.annotations.NotNull BufferedImage img, double angle) {
        double rads = Math.toRadians(angle);
        double sin = Math.abs(Math.sin(rads)), cos = Math.abs(Math.cos(rads));
        int w = img.getWidth();
        int h = img.getHeight();
        int newWidth = (int) Math.floor(w * cos + h * sin);
        int newHeight = (int) Math.floor(h * cos + w * sin);

        BufferedImage rotated = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = rotated.createGraphics();
        AffineTransform at = new AffineTransform();
        at.translate((double) (newWidth - w) / 2, (double) (newHeight - h) / 2);

        int x = w / 2;
        int y = h / 2;

        at.rotate(rads, x, y);
        g2d.setTransform(at);
        g2d.drawImage(img, 0, 0, this);
        g2d.dispose();

        return rotated;
    }
    /*import java.awt.*;
import javax.swing.*;
import java.awt.image.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.*;
import java.util.HashMap;
import java.util.*;

public class MasterPanel extends JPanel{
    private HashMap<String, BufferedImage> icons;
    private boolean isVisible;

    public MasterPanel() {
        icons = new HashMap<>();
        isVisible = false;
        try {
            icons.put("Gizmos Title", ImageIO.read(MasterPanel.class.getResource("/icons/Gizmos Title.png")));
            icons.put("Convert", ImageIO.read(MasterPanel.class.getResource("/icons/Convert right.png")));
            icons.put("Convert L", ImageIO.read(MasterPanel.class.getResource("/icons/Convert left.png")));
            icons.put("Build", ImageIO.read(MasterPanel.class.getResource("/icons/Build Icon Black.png")));
            icons.put("Research", ImageIO.read(MasterPanel.class.getResource("/icons/Research Icon Black.png")));
            icons.put("File", ImageIO.read(MasterPanel.class.getResource("/icons/File Icon Black.png")));
            icons.put("Pick", ImageIO.read(MasterPanel.class.getResource("/icons/Pick Icon Black.png")));
            icons.put("Upgrade", ImageIO.read(MasterPanel.class.getResource("/icons/Upgrades Icon Black.png")));
            icons.put("Energy Max", ImageIO.read(MasterPanel.class.getResource("/icons/Energy Max Icon.png")));
            icons.put("File Max", ImageIO.read(MasterPanel.class.getResource("/icons/File Max Icon.png")));
            icons.put("Research Max", ImageIO.read(MasterPanel.class.getResource("/icons/Research Icon_1.png")));
            icons.put("Blue Energy", ImageIO.read(MasterPanel.class.getResource("/icons/Blue Energy Icon.png")));
            icons.put("Gray Energy", ImageIO.read(MasterPanel.class.getResource("/icons/Grey Energy Icon.png")));
            icons.put("Red Energy", ImageIO.read(MasterPanel.class.getResource("/icons/Red Energy Icon.png")));
            icons.put("Yellow Energy", ImageIO.read(MasterPanel.class.getResource("/icons/Yellow Energy Icon.png")));
            icons.put("Back", ImageIO.read(MasterPanel.class.getResource("/icons/Level 1 Back.png")));
            icons.put("Victory Point", ImageIO.read(MasterPanel.class.getResource("/icons/Victory Point Icon.png")));
            icons.put("Exit Circle", ImageIO.read(MasterPanel.class.getResource("/icons/Exit circle.png")));
            icons.put("Add", ImageIO.read(MasterPanel.class.getResource("/icons/Add Icon.png")));
            icons.put("Subtract", ImageIO.read(MasterPanel.class.getResource("/icons/Subtract Icon.png")));
        }
        catch(Exception E) {
            System.out.println("Error in MasterPanel");
        }
    }

    public boolean getVisible() {
        return isVisible;
    }
    public void setVisible(boolean b) {
        isVisible = b;
    }

    public void highlightMarbles(ArrayList<Marble> marbles, Graphics g) {

    }

    public void highlightCards(ArrayList<Card> cards, Graphics g) {

    }

    public void drawButtons(Graphics g) {

    }

    public HashMap<String, BufferedImage> icons() {
        return icons;
    }
}
import java.awt.*;
import javax.swing.*;
import java.awt.image.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.*;
import java.util.*;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

public class MainPanel extends JPanel implements MouseListener{
    private HashMap<String, BufferedImage> icons, mImgs;
    private int gameState;
    private ArrayList<Player> players;
    private ArrayList<Marble> marbles;
    private Deck deck1, deck2, deck3;
    private GameManualPanel gameManualPanel;
    private DeckPanel deckPanel;
    private PlayerInventoryPanel playerInventoryPanel;
    private ResearchPanel researchPanel;
    private BuildPanel buildPanel;
    private ChainReactionPanel chainReactionPanel;
    private int currentPlayer;
    private CardMasterList cards;
    private boolean lastRound;

    public MainPanel() {
        icons = new HashMap<>();
        mImgs = new HashMap<>();
        gameState = 0;
        cards = new CardMasterList(0, 3);
        players = new ArrayList<>();
        for(int i =0; i<4; i++) {
            players.add(new Player(i+1));
            Iterator<Card> it = cards.getRank(0).iterator();
            players.get(i).addCard(it.next());
        }
        marbles = new ArrayList<>();
        deck1 = new Deck(cards.getRank(1), "-1:2,0 0:1,3 1:3,3 2:1,5 3:3,5", 150, 650, 60, "/cardImages/Rank I Back.png");
        deck1.shuffleAll(new Random());
        deck2 = new Deck(cards.getRank(2), "-1:0,0 0:3,0 1:5,0 2:7,0",220, 660, 220, "/cardImages/Rank II Back.png");
        deck2.shuffleAll(new Random());
        deck3 = new Deck(cards.getRank(3), "-1:2,0 0:2,3 1:2,5", 150, 780, 60, "/cardImages/Rank III Back.png");
        deck3.shuffleAll(new Random());
        gameManualPanel = new GameManualPanel();
        deckPanel = new DeckPanel();
        playerInventoryPanel = new PlayerInventoryPanel();
        researchPanel = new ResearchPanel();
        buildPanel = new BuildPanel();
        chainReactionPanel = new ChainReactionPanel();
        currentPlayer = 0;
        lastRound = false;
        try {
            icons.put("Gizmos Title", ImageIO.read(MainPanel.class.getResource("/icons/Gizmos Title.png")));
            icons.put("Convert", ImageIO.read(MainPanel.class.getResource("/icons/Convert right.png")));
            icons.put("Build", ImageIO.read(MainPanel.class.getResource("/icons/Build Icon Black.png")));
            icons.put("Research", ImageIO.read(MainPanel.class.getResource("/icons/Research Icon Black.png")));
            icons.put("File", ImageIO.read(MainPanel.class.getResource("/icons/File Icon Black.png")));
            icons.put("Pick", ImageIO.read(MainPanel.class.getResource("/icons/Pick Icon Black.png")));
            icons.put("Upgrade", ImageIO.read(MainPanel.class.getResource("/icons/Upgrades Icon Black.png")));
            icons.put("Energy Max", ImageIO.read(MainPanel.class.getResource("/icons/Energy Max Icon.png")));
            icons.put("File Max", ImageIO.read(MainPanel.class.getResource("/icons/File Max Icon.png")));
            icons.put("Research Max", ImageIO.read(MainPanel.class.getResource("/icons/Research Icon_1.png")));
            icons.put("Blue Energy", ImageIO.read(MainPanel.class.getResource("/icons/Blue Energy Icon.png")));
            icons.put("Gray Energy", ImageIO.read(MainPanel.class.getResource("/icons/Grey Energy Icon.png")));
            icons.put("Red Energy", ImageIO.read(MainPanel.class.getResource("/icons/Red Energy Icon.png")));
            icons.put("Yellow Energy", ImageIO.read(MainPanel.class.getResource("/icons/Yellow Energy Icon.png")));
            icons.put("Back", ImageIO.read(MainPanel.class.getResource("/icons/Level 1 Back.png")));
            icons.put("Victory Point", ImageIO.read(MainPanel.class.getResource("/icons/Victory Point Icon.png")));
            icons.put("Expand", ImageIO.read(MainPanel.class.getResource("/icons/Expand Image.png")));
            icons.put("Crown", ImageIO.read(MainPanel.class.getResource("/icons/Crown_Icon-removebg-preview.png")));
            mImgs.put("Blue", ImageIO.read(MainPanel.class.getResource("/icons/Blue Energy Icon.png")));
            mImgs.put("Gray", ImageIO.read(MainPanel.class.getResource("/icons/Grey Energy Icon.png")));
            mImgs.put("Red", ImageIO.read(MainPanel.class.getResource("/icons/Red Energy Icon.png")));
            mImgs.put("Yellow", ImageIO.read(MainPanel.class.getResource("/icons/Yellow Energy Icon.png")));
        }
        catch(Exception E) {
            System.out.println("Error in MainPanel");
        }

        for(int i = 1; i<=13; i++) {
            marbles.add(new Marble("Blue", 0, 0, 0, mImgs));
        }
        for(int i = 1; i<=13; i++) {
            marbles.add(new Marble("Gray", 0, 0, 0, mImgs));
        }
        for(int i = 1; i<=13; i++) {
            marbles.add(new Marble("Red", 0, 0, 0, mImgs));
        }
        for(int i = 1; i<=13; i++) {
            marbles.add(new Marble("Yellow", 0, 0, 0, mImgs));
        }
        for (int i = 1; i<marbles.size(); i++) {
            int randIndex = (int) (Math.random()*marbles.size());
            Marble temp = marbles.get(randIndex);
            marbles.set(randIndex, marbles.get(i));
            marbles.set(i, temp);
        }
        addMouseListener(this);
    }

    public void paint(Graphics g) {
        super.paint(g);
        Color lightG = new Color(225, 222, 215);
        Color gray = new Color(207, 207, 197);
        Color darkG = new Color(190,188,175);
        g.setColor(lightG);
        g.fillRect(0, 0, getWidth(),  getHeight());
        hideAllCards();
        switch(gameState) {

            case 0 : {
                //game intro
                g.setColor(gray);
                g.fillRect(getWidth()/60, getHeight()/40, getWidth()*58/60, getHeight()*38/40);
                g.drawImage(icons.get("Gizmos Title"), 0, 0, getWidth(), getHeight(), null);
                g.setColor(lightG);
                g.fillRect((int)(getWidth()/2.69), (int)(getHeight()/1.59), (int)(getWidth()/3.87), (int)(getHeight()/7.68));
                g.fillRect((int)(getWidth()/2.69), (int)(getHeight()/1.26), (int)(getWidth()/3.87), (int)(getHeight()/7.68));
                g.setColor(new Color(64, 64, 64));
                g.setFont(new Font("op", 1, (int)(getWidth()/33.68)));
                //617, 625 668, 776
                g.drawString("New Game", (int)(getWidth()/2.39), (int)(getHeight()/1.4));
                g.drawString("Manual", (int)(getWidth()/2.27), (int)(getHeight()/1.14));
                if(gameManualPanel.getVisible()) {
                    gameManualPanel.paint(g, getWidth(), getHeight());
                }
                researchPanel.resetPanel();
                buildPanel.resetBuild();
                break;
            }
            case 1 : {
                //player choice
                drawPlacementMat(g);
                drawPlayerValues(g);
                drawPlayerCards(g);
                drawDeck(g);
                break;
            }
            case 2 : {
                //pick
                drawPlacementMat(g);
                drawPlayerValues(g);
                drawPlayerCards(g);
                drawDeck(g);
                break;
            }
            case 3 : {
                //file
                drawPlacementMat(g);
                drawPlayerValues(g);
                drawPlayerCards(g);
                highlightDeck();
                drawDeck(g);
                if(deckPanel.getVisible()) {
                    deckPanel.paint(g, getWidth(), getHeight(), deck1, deck2, deck3);
                }
                break;
            }
            case 4 : {
                //build
                drawPlacementMat(g);
                drawPlayerValues(g);
                if(!buildPanel.getVisible()) {
                    highlightDeck();
                    highlightFiled();
                }

                drawDeck(g);
                drawPlayerCards(g);
                if(deckPanel.getVisible()) {
                    deckPanel.paint(g, getWidth(), getHeight(), deck1, deck2, deck3);
                }
                else if(buildPanel.getVisible() ) {
                    buildPanel.paint(g, getWidth(), getHeight(), players.get(currentPlayer));
                }
                if(playerInventoryPanel.getVisible()) {
                    playerInventoryPanel.paint(g, getWidth(), getHeight(), players);
                }
                break;
            }
            case 5 : {
                //research
                drawPlacementMat(g);
                drawPlayerValues(g);
                drawPlayerCards(g);
                if(!researchPanel.getVisible()) {
                    highlightDeck();
                }
                drawDeck(g);
                if(deckPanel.getVisible()) {
                    deckPanel.paint(g, getWidth(), getHeight(), deck1, deck2, deck3);
                }

                if(researchPanel.getVisible()) {
                    Color cl = Color.WHITE;
                    if(currentPlayer==0) {
                        cl = new Color(206, 43, 46);
                    }
                    else if(currentPlayer==1) {
                        cl = new Color(231, 178, 64);
                    }
                    else if(currentPlayer==2) {
                        cl = new Color(63, 106, 208);
                    }
                    else if(currentPlayer==3) {
                        cl = new Color(181, 57, 153);
                    }
                    researchPanel.paint(g, getWidth(), getHeight(), cl, players.get(currentPlayer));
                }
                if(playerInventoryPanel.getVisible()) {
                    playerInventoryPanel.paint(g, getWidth(), getHeight(), players);
                }
                break;
            }
            case 6 : {
                //random energy
                drawPlacementMat(g);
                drawPlayerValues(g);
                drawPlayerCards(g);
                drawDeck(g);
                chainReactionPanel.setVisible(true);
                chainReactionPanel.paint(g, getWidth(), getHeight(), players);
                break;
            }
            case 7 : {
                //game Manual
                drawPlacementMat(g);
                drawPlayerValues(g);
                drawPlayerCards(g);
                drawDeck(g);
                gameManualPanel.setVisible(true);
                gameManualPanel.paint(g, getWidth(), getHeight());
                break;
            }
            case 8 : {
                //deck
                drawPlacementMat(g);
                drawPlayerValues(g);
                drawPlayerCards(g);
                drawDeck(g);
                deckPanel.paint(g, getWidth(), getHeight(), deck1, deck2, deck3);
                break;
            }
            case 9 : {
                //player inventory
                drawPlacementMat(g);
                drawPlayerValues(g);
                drawPlayerCards(g);
                drawDeck(g);
                playerInventoryPanel.setVisible(true);
                playerInventoryPanel.paint(g, getWidth(), getHeight(), players);
                break;
            }
            case 10 : {
                //end game
                drawEnd(g);
                if(playerInventoryPanel.getVisible()) {
                    playerInventoryPanel.paint(g, getWidth(), getHeight(), players);
                }
                break;
            }
        }
    }

    public void drawEnd(Graphics g) {
        TreeSet<Player> p = leaderBoard();
        Iterator<Player> it = p.iterator();
        Player t = it.next();
        g.setFont(new Font("f", 1, (int)(getWidth()/18.29)));
        if(t.equals(players.get(0))) {
            g.setColor(new Color(206, 43, 46));
            g.fillRect((int)(getWidth()/26), (int)(getHeight()/3.2), (int)(getWidth()/2.272), (int)(getHeight()/2.94));
            g.setColor(new Color(250, 104, 105));
            g.fillRect((int)(getWidth()/19.83), (int)(getHeight()/2.96), (int)(getWidth()/2.398), (int)(getHeight()/3.47));
            g.setColor(new Color(206, 43, 46));
            g.drawString("Player One", (int)(getWidth()/9.75), (int)(getHeight()/2.1));

        }
        else if(t.equals(players.get(1))) {
            g.setColor(new Color(231, 178, 64));
            g.fillRect((int)(getWidth()/26), (int)(getHeight()/3.2), (int)(getWidth()/2.272), (int)(getHeight()/2.94));
            g.setColor(new Color(255, 216, 102));
            g.fillRect((int)(getWidth()/19.83), (int)(getHeight()/2.96), (int)(getWidth()/2.398), (int)(getHeight()/3.47));
            g.setColor(new Color(231, 178, 64));
            g.drawString("Player Two", (int)(getWidth()/9.75), (int)(getHeight()/2.1));
        }
        else if(t.equals(players.get(2))) {
            g.setColor(new Color(63, 106, 208));
            g.fillRect((int)(getWidth()/26), (int)(getHeight()/3.2), (int)(getWidth()/2.272), (int)(getHeight()/2.94));
            g.setColor(new Color(121, 154, 233));
            g.fillRect((int)(getWidth()/19.83), (int)(getHeight()/2.96), (int)(getWidth()/2.398), (int)(getHeight()/3.47));
            g.setColor(new Color(63, 106, 208));
            g.drawString("Player Three", (int)(getWidth()/9.75), (int)(getHeight()/2.1));
        }
        else if(t.equals(players.get(3))) {
            g.setColor(new Color(181, 57, 153));
            g.fillRect((int)(getWidth()/26), (int)(getHeight()/3.2), (int)(getWidth()/2.272), (int)(getHeight()/2.94));
            g.setColor(new Color(229, 116, 204));
            g.fillRect((int)(getWidth()/19.83), (int)(getHeight()/2.96), (int)(getWidth()/2.398), (int)(getHeight()/3.47));
            g.setColor(new Color(181, 57, 153));
            g.drawString("Player Four", (int)(getWidth()/9.75), (int)(getHeight()/2.1));
        }
        g.setFont(new Font("vp1", 1, 24));
        g.drawString("x "+t.getVicPoints(), (int)(getWidth()/3.98), (int)(getHeight()/1.72));
        g.setFont(new Font("f", 1, 150));
        g.setColor(new Color(255, 199, 54));
        g.drawString("1st", (int)(getWidth()/3.75), (int)(getHeight()/3.76));
        g.setFont(new Font("f", 1, 100));
        g.setColor(new Color(149, 155, 169));
        g.drawString("2nd", (int)(getWidth()/1.96), (int)(getHeight()/3.72));
        g.setColor(new Color(186, 127, 83));
        g.drawString("3rd", (int)(getWidth()/1.96), (int)(getHeight()/1.91));
        g.setColor(new Color(161, 49, 0));
        g.drawString("4th", (int)(getWidth()/1.96), (int)(getHeight()/1.27));
        for(int i = 0; i<3; i++) {
            if(it.hasNext()) {
                t = it.next();
            }
            g.setFont(new Font("o", 1, (int)(getWidth()/28.44)));
            if(t.equals(players.get(0))) {
                g.setColor(new Color(206, 43, 46));
                g.fillRect((int)(getWidth()/1.52), (int)(getHeight()/8.275)+i*((int)(getHeight()/3.87)), (int)(getWidth()/3.391), (int)(getHeight()/4.36));
                g.setColor(new Color(250, 104, 105));
                g.fillRect((int)(getWidth()/1.5), (int)(getHeight()/7.04)+i*((int)(getHeight()/3.92)), (int)(getWidth()/3.62), (int)(getHeight()/5.25));
                g.setColor(new Color(206, 43, 46));
                g.drawString("Player One", (int)(getWidth()/1.4), (int)(getHeight()/4.5)+i*((int)(getHeight()/3.8)));
            }
            else if(t.equals(players.get(1))) {
                g.setColor(new Color(231, 178, 64));
                g.fillRect((int)(getWidth()/1.52), (int)(getHeight()/8.275)+i*((int)(getHeight()/3.87)), (int)(getWidth()/3.391), (int)(getHeight()/4.36));
                g.setColor(new Color(255, 216, 102));
                g.fillRect((int)(getWidth()/1.5), (int)(getHeight()/7.04)+i*((int)(getHeight()/3.92)), (int)(getWidth()/3.62), (int)(getHeight()/5.25));
                g.setColor(new Color(231, 178, 64));
                g.drawString("Player Two", (int)(getWidth()/1.4), (int)(getHeight()/4.5)+i*((int)(getHeight()/3.8)));
            }
            else if(t.equals(players.get(2))) {
                g.setColor(new Color(63, 106, 208));
                g.fillRect((int)(getWidth()/1.52), (int)(getHeight()/8.275)+i*((int)(getHeight()/3.87)), (int)(getWidth()/3.391), (int)(getHeight()/4.36));
                g.setColor(new Color(121, 154, 233));
                g.fillRect((int)(getWidth()/1.5), (int)(getHeight()/7.04)+i*((int)(getHeight()/3.92)), (int)(getWidth()/3.62), (int)(getHeight()/5.25));
                g.setColor(new Color(63, 106, 208));
                g.drawString("Player Three", (int)(getWidth()/1.4), (int)(getHeight()/4.5)+i*((int)(getHeight()/3.8)));
            }
            else if(t.equals(players.get(3))) {
                g.setColor(new Color(181, 57, 153));
                g.fillRect((int)(getWidth()/1.52), (int)(getHeight()/8.275)+i*((int)(getHeight()/3.87)), (int)(getWidth()/3.391), (int)(getHeight()/4.36));
                g.setColor(new Color(229, 116, 204));
                g.fillRect((int)(getWidth()/1.5), (int)(getHeight()/7.04)+i*((int)(getHeight()/3.92)), (int)(getWidth()/3.62), (int)(getHeight()/5.25));
                g.setColor(new Color(181, 57, 153));
                g.drawString("Player Four", (int)(getWidth()/1.4), (int)(getHeight()/4.5)+i*((int)(getHeight()/3.8)));
            }
            g.setFont(new Font("vp", 1, (int)(getWidth()/71.11)));
            g.drawString("x "+t.getVicPoints(), (int)(getWidth()/1.249), (int)(getHeight()/3.45)+i*((int)(getHeight()/3.87)));

            g.setFont(new Font("m", 1, (int)(getWidth()/53.33)));
            g.setColor(new Color(121, 154, 223));
            g.fillRect((int)(getWidth()/27.21), (int)(getHeight()/1.37), (int)(getWidth()/4.64), (int)(getHeight()/6.3));
            g.setColor(new Color(249, 103, 104));
            g.fillRect((int)(getWidth()/3.714), (int)(getHeight()/1.37), (int)(getWidth()/4.64), (int)(getHeight()/6.3));
            g.setColor(new Color(58, 112, 172));
            g.drawString("Main Menu", (int)(getWidth()/11.04), (int)(getHeight()/1.22));
            g.setColor(new Color(140, 17, 20));
            g.drawString("New Game", (int)(getWidth()/3.07), (int)(getHeight()/1.22));
        }

        g.drawImage(icons.get("Crown"), (int)(getWidth()/29.25), (int)(getHeight()/15.05), (int)(getWidth()/4.53), (int)(getHeight()/4.53), null);
        g.drawImage(icons.get("Expand"), (int)(getWidth()/2.272), (int)(getHeight()/2.96), (int)(getWidth()/36.56), (int)(getHeight()/20.69), null);
        g.drawImage(icons.get("Expand"), (int)(getWidth()/1.093), (int)(getHeight()/7.2), (int)(getWidth()/36.56), (int)(getHeight()/20.69), null);
        g.drawImage(icons.get("Expand"), (int)(getWidth()/1.093), (int)(getHeight()/2.52), (int)(getWidth()/36.56), (int)(getHeight()/20.69), null);
        g.drawImage(icons.get("Expand"), (int)(getWidth()/1.093), (int)(getHeight()/1.54), (int)(getWidth()/36.56), (int)(getHeight()/20.69), null);
        g.drawImage(icons.get("Victory Point"), (int)(getWidth()/4.699), (int)(getHeight()/1.9), (int)(getWidth()/30), (int)(getHeight()/12.98), null);
        g.drawImage(icons.get("Victory Point"), (int)(getWidth()/1.294), (int)(getHeight()/4.01), (int)(getWidth()/40.34), (int)(getHeight()/16.97), null);
        g.drawImage(icons.get("Victory Point"), (int)(getWidth()/1.294), (int)(getHeight()/1.98), (int)(getWidth()/40.34), (int)(getHeight()/16.97), null);
        g.drawImage(icons.get("Victory Point"), (int)(getWidth()/1.294), (int)(getHeight()/1.32), (int)(getWidth()/40.34), (int)(getHeight()/16.97), null);

    }
    public void highlightMarbles(Graphics g) {
        if(currentPlayer==0) {
            g.setColor(new Color(206, 43, 46));
        }
        else if(currentPlayer==1) {
            g.setColor(new Color(231, 178, 64));
        }
        else if(currentPlayer==2) {
            g.setColor(new Color(63, 106, 208));
        }
        else if(currentPlayer==3) {
            g.setColor(new Color(181, 57, 153));
        }
        for(int i = 0; i<6; i++) {
            g.fillOval((int)(getWidth()/1.315)+i*((int)(getWidth()/29.25)), (int)(getHeight()/23), (int)(getWidth()/28.07), (int)(getHeight()/15.785));
        }//(int)(getWidth()/32.5), (int)(getHeight()/18.42)
    }

    public void highlightDeck() {
        if(gameState == 3 || gameState == 4) {
            for(Card c : deck1.getDisplay()) {
                c.setHighlighted(true);
            }
            for(Card c : deck2.getDisplay()) {
                c.setHighlighted(true);
            }
            for(Card c : deck3.getDisplay()) {
                c.setHighlighted(true);
            }
        }
        else if(gameState == 5) {
            deck1.getDeckBack().setHighlighted(true);
            deck2.getDeckBack().setHighlighted(true);
            deck3.getDeckBack().setHighlighted(true);
        }
    }

    public void highlightFiled() {
        for(Card c : players.get(currentPlayer).getFiled()) {
            c.setHighlighted(true);
        }
    }

    public void highlightChoices(Graphics g) {
        if(currentPlayer==0) {
            g.setColor(new Color(206, 43, 46));
        }
        else if(currentPlayer==1) {
            g.setColor(new Color(231, 178, 64));
        }
        else if(currentPlayer==2) {
            g.setColor(new Color(63, 106, 208));
        }
        else if(currentPlayer==3) {
            g.setColor(new Color(181, 57, 153));
        }
        if(players.get(currentPlayer).getFiled().size()<players.get(currentPlayer).getFileAmt()) {
            //highlights file
            g.fillRect((int)(getWidth()/7.85), (int)(getHeight()/29), (int)(getWidth()/5.695), (int)(getHeight()/6.44));
            //highlights research
            g.fillRect((int)(getWidth()/3.295), (int)(getHeight()/29.), (int)(getWidth()/5.695), (int)(getHeight()/6.44));
        }
        if(players.get(currentPlayer).getAllMarbles().size()<players.get(currentPlayer).getMarbleAmt()) {
            //highlights pick
            g.fillRect((int)(getWidth()/7.85), (int)(getHeight()/2.07), (int)(getWidth()/5.695), (int)(getHeight()/6.44));
        }
        if(players.get(currentPlayer).getAllMarbles().size()>0) {
            //highlights build
            g.fillRect((int)(getWidth()/3.295), (int)(getHeight()/2.07), (int)(getWidth()/5.695), (int)(getHeight()/6.44));
            //highlights research
            g.fillRect((int)(getWidth()/3.295), (int)(getHeight()/29.), (int)(getWidth()/5.695), (int)(getHeight()/6.44));
        }
    }

    public void drawPlacementMat(Graphics g) {
        Color lightG = new Color(225, 222, 215);
        Color gray = new Color(207, 207, 197);
        Color darkG = new Color(190,188,175);

        g.setColor(gray);
        g.fillRect(getWidth()/85, (int)(getHeight()/4.66), (int)(getWidth()/10.5), (int)(getHeight()/2.66));
        g.fillRect(getWidth()/85, (int)(getHeight()/1.64), (int)(getWidth()/10.5), (int)(getHeight()/2.75));
        g.fillRect((int)(getWidth()/8.54), getHeight()/40, (int)(getWidth()/1.63), getHeight()*38/40);
        g.fillRect((int)(getWidth()/1.35), getHeight()/40, (int)(getWidth()/4.05), getHeight()*38/40);
        g.setColor(darkG);
        g.fillRect((int)(getWidth()/2.04), getHeight()/24, (int)(getWidth()/4.44), (int)(getHeight()/2.73));
        g.fillRect((int)(getWidth()/2.03), (int)(getHeight()/2.35), (int)(getWidth()/9.21), (int)(getHeight()/1.90));
        g.fillRect((int)(getWidth()/1.64), (int)(getHeight()/2.35), (int)(getWidth()/9.512), (int)(getHeight()/1.90));
        if(gameState==1) {
            highlightChoices(g);
        }
        g.setColor(lightG);
        g.fillRect((int)(getWidth()/7.6), getHeight()/24, (int)(getWidth()/5.97), (int)(getHeight()/7.13));
        g.fillRect((int)(getWidth()/3.25), getHeight()/24, (int)(getWidth()/5.97), (int)(getHeight()/7.13));
        g.fillRect((int)(getWidth()/7.6), (int)(getHeight()/2.04), (int)(getWidth()/5.97), (int)(getHeight()/7.13));
        g.fillRect((int)(getWidth()/3.25), (int)(getHeight()/2.04), (int)(getWidth()/5.97), (int)(getHeight()/7.13));
        g.fillRect((int)(getWidth()/1.33), getHeight()/24, (int)(getWidth()/4.43), (int)(getHeight()/14.41));
        g.fillRect((int)(getWidth()/1.33), (int)(getHeight()/1.12), (int)(getWidth()/4.43), (int)(getHeight()/14.73));

        g.setFont(new Font("he he", 1, (int)(getWidth()/55.33)));
        g.setColor(new Color(206, 43, 46));
        g.fillRect((int)(getWidth()/1.33), (int)(getHeight()/8), (int)(getWidth()/4.43), (int)(getHeight()/5.67));
        g.setColor(new Color(250, 104, 105));
        g.fillRect((int)(getWidth()/1.32), (int)(getHeight()/7.25), (int)(getWidth()/4.7), (int)(getHeight()/6.84));
        g.setColor(new Color(206, 43, 46));
        g.drawString("Player One", (int)(getWidth()/1.23), (int)(getHeight()/5.62));

        g.setColor(new Color(231, 178, 64));
        g.fillRect((int)(getWidth()/1.33), (int)(getHeight()/3.15), (int)(getWidth()/4.43), (int)(getHeight()/5.67));
        g.setColor(new Color(255, 216, 102));
        g.fillRect((int)(getWidth()/1.32), (int)(getHeight()/3), (int)(getWidth()/4.7), (int)(getHeight()/6.84));
        g.setColor(new Color(231, 178, 64));
        g.drawString("Player Two", (int)(getWidth()/1.23), (int)(getHeight()/2.71));

        g.setColor(new Color(63, 106, 208));
        g.fillRect((int)(getWidth()/1.33), (int)(getHeight()/1.95), (int)(getWidth()/4.43), (int)(getHeight()/5.67));
        g.setColor(new Color(121, 154, 233));
        g.fillRect((int)(getWidth()/1.32), (int)(getHeight()/1.90), (int)(getWidth()/4.7), (int)(getHeight()/6.84));
        g.setColor(new Color(63, 106, 208));
        g.drawString("Player Three", (int)(getWidth()/1.24), (int)(getHeight()/1.78));

        g.setColor(new Color(181, 57, 153));
        g.fillRect((int)(getWidth()/1.33), (int)(getHeight()/1.42), (int)(getWidth()/4.43), (int)(getHeight()/5.67));
        g.setColor(new Color(229, 116, 204));
        g.fillRect((int)(getWidth()/1.32), (int)(getHeight()/1.39), (int)(getWidth()/4.7), (int)(getHeight()/6.84));
        g.setColor(new Color(181, 57, 153));
        g.drawString("Player Four", (int)(getWidth()/1.235), (int)(getHeight()/1.32));






        if(gameState==1) {
            g.setColor(new Color(250, 104, 105));
        }
        else {
            g.setColor(new Color(121, 154, 233));
        }

        g.fillRect(getWidth()/85, (int)(getHeight()/9.15), (int)(getWidth()/10.5), (int)(getHeight()/14.73));
        g.setColor(new Color(126, 191, 151));//green
        g.fillRect(getWidth()/85, (int)(getHeight()/41.), (int)(getWidth()/10.5), (int)(getHeight()/14.73));

        g.setFont(new Font("lol", 1, (int)(getWidth()/88.235)));
        g.setColor(new Color(64, 64, 64));
        g.drawString("Upgrades", (int)(getWidth()/1.87), (int)(getHeight()/2.11));
        g.drawString("Converters", (int)(getWidth()/1.56), (int)(getHeight()/2.11));
        g.drawString("Help", (int)(getWidth()/21), (int)(getHeight()/15));
        if(gameState==1) {
            g.drawString("End Turn", (int)(getWidth()/30), (int)(getHeight()/6.45));
        }
        else  {
            g.drawString("Back", (int)(getWidth()/22), (int)(getHeight()/6.45));
        }
        g.setFont(new Font("yuh", 1, getWidth()/30));
        g.drawString("Build", (int)(getWidth()/2.69), (int)(getHeight()/1.7));
        g.drawString("Pick", (int)(getWidth()/7.3), (int)(getHeight()/1.74));
        g.drawString("File", (int)(getWidth()/4.45), (int)(getHeight()/8.0));
        g.setFont(new Font("pick", 1, getWidth()/50));
        g.drawString("Research", (int)(getWidth()/2.73), (int)(getHeight()/6.9));

        g.drawImage(icons.get("File"), (int)(getWidth()/6.76), (int)(getHeight()/16), (int)(getWidth()/13.85), (int)(getHeight()/11.63), null);
        g.drawImage(icons.get("Research"), (int)(getWidth()/3.1), (int)(getHeight()/17), (int)(getWidth()/20.17), (int)(getHeight()/10.36), null);
        g.drawImage(icons.get("Pick"), (int)(getWidth()/4.59), (int)(getHeight()/1.93), (int)(getWidth()/15.6), (int)(getHeight()/11.05), null);
        g.drawImage(icons.get("Build"), (int)(getWidth()/3.13), (int)(getHeight()/1.97), (int)(getWidth()/18.57), (int)(getHeight()/10), null);
        g.drawImage(icons.get("Upgrade"), getWidth()/2, (int)(getHeight()/2.3), (int)(getWidth()/30.79), (int)(getHeight()/17.45), null);
        g.drawImage(icons.get("Convert"), (int)(getWidth()/1.62), (int)(getHeight()/2.3), (int)(getWidth()/41.79), (int)(getHeight()/17.45), null);

        g.drawImage(icons.get("File Max"), (int)(getWidth()/48.75), (int)(getHeight()/4.22), (int)(getWidth()/36.56), (int)(getHeight()/27.63), null);
        g.drawImage(icons.get("File Max"), (int)(getWidth()/13.93), (int)(getHeight()/4.22), (int)(getWidth()/36.56), (int)(getHeight()/27.63), null);
        g.drawImage(icons.get("File Max"), (int)(getWidth()/1.93), (int)(getHeight()/2.0), (int)(getWidth()/33.43), (int)(getHeight()/23.68), null);
        g.drawImage(icons.get("Energy Max"), (int)(getWidth()/53.18), (int)(getHeight()/1.59), (int)(getWidth()/29.25), (int)(getHeight()/22.86), null);
        g.drawImage(icons.get("Energy Max"), getWidth()/15, (int)(getHeight()/1.59), (int)(getWidth()/29.25), (int)(getHeight()/22.86), null);
        g.drawImage(icons.get("Energy Max"), (int)(getWidth()/1.81), (int)(getHeight()/2.0), (int)(getWidth()/29.25), (int)(getHeight()/22.86), null);
        g.drawImage(icons.get("Research Max"), (int)(getWidth()/1.9), (int)(getHeight()/1.82), (int)(getWidth()/24.38), (int)(getHeight()/15.79), null);

        g.drawImage(icons.get("Yellow Energy"), (int)(getWidth()/41.79), (int)(getHeight()/1.47), (int)(getWidth()/26.59), (int)(getHeight()/15.42), null);
        g.drawImage(icons.get("Gray Energy"), (int)(getWidth()/41.79), (int)(getHeight()/1.33), (int)(getWidth()/26.59), (int)(getHeight()/15.42), null);
        g.drawImage(icons.get("Blue Energy"), (int)(getWidth()/41.79), (int)(getHeight()/1.21), (int)(getWidth()/26.59), (int)(getHeight()/15.42), null);
        g.drawImage(icons.get("Red Energy"), (int)(getWidth()/41.79), (int)(getHeight()/1.11), (int)(getWidth()/26.59), (int)(getHeight()/15.42), null);

        g.drawImage(icons.get("Back"), (int)(getWidth()/1.21), (int)(getHeight()/5.02), (int)(getWidth()/30.79), (int)(getHeight()/17.45), null);
        g.drawImage(icons.get("Back"), (int)(getWidth()/1.11), (int)(getHeight()/5.02), (int)(getWidth()/30.79), (int)(getHeight()/17.45), null);
        g.drawImage(icons.get("Back"), (int)(getWidth()/1.21), (int)(getHeight()/2.54), (int)(getWidth()/30.79), (int)(getHeight()/17.45), null);
        g.drawImage(icons.get("Back"), (int)(getWidth()/1.11), (int)(getHeight()/2.54), (int)(getWidth()/30.79), (int)(getHeight()/17.45), null);
        g.drawImage(icons.get("Back"), (int)(getWidth()/1.21), (int)(getHeight()/1.7), (int)(getWidth()/30.79), (int)(getHeight()/17.45), null);
        g.drawImage(icons.get("Back"), (int)(getWidth()/1.11), (int)(getHeight()/1.7), (int)(getWidth()/30.79), (int)(getHeight()/17.45), null);
        g.drawImage(icons.get("Back"), (int)(getWidth()/1.21), (int)(getHeight()/1.28), (int)(getWidth()/30.79), (int)(getHeight()/17.45), null);
        g.drawImage(icons.get("Back"), (int)(getWidth()/1.11), (int)(getHeight()/1.28), (int)(getWidth()/30.79), (int)(getHeight()/17.45), null);
        g.drawImage(icons.get("Victory Point"), (int)(getWidth()/1.31), (int)(getHeight()/5.02), (int)(getWidth()/41.79), getHeight()/17, null);
        g.drawImage(icons.get("Victory Point"), (int)(getWidth()/1.185), (int)(getHeight()/5.02), (int)(getWidth()/41.79), getHeight()/17, null);
        g.drawImage(icons.get("Victory Point"), (int)(getWidth()/1.31), (int)(getHeight()/2.54), (int)(getWidth()/41.79), getHeight()/17, null);
        g.drawImage(icons.get("Victory Point"), (int)(getWidth()/1.185), (int)(getHeight()/2.54), (int)(getWidth()/41.79), getHeight()/17, null);
        g.drawImage(icons.get("Victory Point"), (int)(getWidth()/1.31), (int)(getHeight()/1.7), (int)(getWidth()/41.79), getHeight()/17, null);
        g.drawImage(icons.get("Victory Point"), (int)(getWidth()/1.185), (int)(getHeight()/1.7), (int)(getWidth()/41.79), getHeight()/17, null);
        g.drawImage(icons.get("Victory Point"), (int)(getWidth()/1.31), (int)(getHeight()/1.28), (int)(getWidth()/41.79), getHeight()/17, null);
        g.drawImage(icons.get("Victory Point"), (int)(getWidth()/1.185), (int)(getHeight()/1.28), (int)(getWidth()/41.79), getHeight()/17, null);

        if(!playerInventoryPanel.getVisible() && gameState==1 || gameState==4 || gameState==5 || gameState == 6) {
            g.drawImage(icons.get("Expand"), (int)(getWidth()/1.053), (int)(getHeight()/7.25), (int)(getWidth()/53.18), (int)(getHeight()/30.14), null);
            g.drawImage(icons.get("Expand"), (int)(getWidth()/1.053), (int)(getHeight()/3), (int)(getWidth()/53.18), (int)(getHeight()/30.14), null);
            g.drawImage(icons.get("Expand"), (int)(getWidth()/1.053), (int)(getHeight()/1.9), (int)(getWidth()/53.18), (int)(getHeight()/30.14), null);
            g.drawImage(icons.get("Expand"), (int)(getWidth()/1.053), (int)(getHeight()/1.39), (int)(getWidth()/53.18), (int)(getHeight()/30.14), null);
        }
        if(gameState==1 || gameState==3 || gameState==4 || gameState==5) {
            g.drawImage(icons.get("Expand"), (int)(getWidth()/1.45), (int)(getHeight()/24.03), (int)(getWidth()/40.34), (int)(getHeight()/22.86), null);
        }
        ArrayList<Marble> temp = new ArrayList<>();
        for(int i = 0; i<6; i++) {
            int x = (int)(getWidth()/1.31)+i*((int)(getWidth()/32.5)+(int)(getWidth()/292.5));
            int y = (int)(getHeight()/21.05);
            int r = (int)(getWidth()/32.5/2);
            marbles.get(i).setX(x);
            //System.out.println(marbles.get(i).getX()+" "+(marbles.get(i).getX()-marbles.get(i).getRadius())+" "+getWidth());
            marbles.get(i).setY(y);
            marbles.get(i).setRadius(r);
            temp.add(marbles.get(i));
        }
        if(gameState==2) {
            highlightMarbles(g);
        }
        for(int i = 0; i<6; i++) {
            g.drawImage(marbles.get(i).getImage(), (int)(getWidth()/1.31)+i*((int)(getWidth()/32.5)+(int)(getWidth()/292.5)), (int)(getHeight()/21.05), (int)(getWidth()/32.5), (int)(getHeight()/18.42), null);
            //System.out.println(marbles.get(i).getCenterX()+" "+marbles.get(i).getCenterY()+" "+marbles.get(i).getRadius());
            //System.out.println(marbles.get(i).getX()-marbles.get(i).getRadius()+" "+(marbles.get(i).getY()-marbles.get(i).getRadius()));
        }
    }

    public void drawPlayerValues(Graphics g) {
        g.setFont(new Font("player", 1, (int)(getWidth()/68.18)));
        g.setColor(new Color(64, 64, 64));
        g.drawString(""+players.get(currentPlayer).getAllMarbles().size()+"    /    "+players.get(currentPlayer).getMarbleAmt(), (int)(getWidth()/30.79), (int)(getHeight()/1.51));
        g.drawString(""+players.get(currentPlayer).getFiled().size()+"    /     "+players.get(currentPlayer).getFileAmt(), (int)(getWidth()/30.79), (int)(getHeight()/3.73));
        g.drawString(""+players.get(currentPlayer).getFileAmt(), (int)(getWidth()/1.89), (int)(getHeight()/1.87));
        g.drawString(""+players.get(currentPlayer).getMarbleAmt(), (int)(getWidth()/1.77), (int)(getHeight()/1.87));
        g.drawString(""+players.get(currentPlayer).getResearchAmt(), (int)(getWidth()/1.83), (int)(getHeight()/1.7));
        //38, 438 618, 351 639, 388
        int y = 0;
        int gr = 0;
        int b = 0;
        int r = 0;
        for(int i = 0; i<players.get(currentPlayer).getAllMarbles().size(); i++) {
            String m = players.get(currentPlayer).getAllMarbles().get(i).getType();
            if(m.equals("Blue")) {
                b++;
            }
            else if(m.equals("Gray")) {
                gr++;
            }
            else if(m.equals("Yellow")) {
                y++;
            }
            else if(m.equals("Red")) {
                r++;
            }
        }
        g.drawString("x "+y, (int)(getWidth()/15.39), (int)(getHeight()/1.38));
        g.drawString("x "+gr, (int)(getWidth()/15.39), (int)(getHeight()/1.25));
        g.drawString("x "+b, (int)(getWidth()/15.39), (int)(getHeight()/1.15));
        g.drawString("x "+r, (int)(getWidth()/15.39), (int)(getHeight()/1.06));
        //76, 481

        g.setColor(new Color(206, 43, 46));
        g.drawString("x "+players.get(0).getVicPoints(), (int)(getWidth()/1.26), (int)(getHeight()/4.23));
        g.drawString("x "+players.get(0).getCardPoints(), (int)(getWidth()/1.15), (int)(getHeight()/4.23));
        g.drawString("x "+players.get(0).getCardAmt(), (int)(getWidth()/1.065), (int)(getHeight()/4.23));
        //925, 155

        g.setColor(new Color(231, 178, 64));
        g.drawString("x "+players.get(1).getVicPoints(), (int)(getWidth()/1.26), (int)(getHeight()/2.32));
        g.drawString("x "+players.get(1).getCardPoints(), (int)(getWidth()/1.15), (int)(getHeight()/2.32));
        g.drawString("x "+players.get(1).getCardAmt(), (int)(getWidth()/1.065), (int)(getHeight()/2.32));

        g.setColor(new Color(63, 106, 208));
        g.drawString("x "+players.get(2).getVicPoints(), (int)(getWidth()/1.26), (int)(getHeight()/1.6));
        g.drawString("x "+players.get(2).getCardPoints(), (int)(getWidth()/1.15), (int)(getHeight()/1.6));
        g.drawString("x "+players.get(2).getCardAmt(), (int)(getWidth()/1.065), (int)(getHeight()/1.6));

        g.setColor(new Color(181, 57, 153));
        g.drawString("x "+players.get(3).getVicPoints(), (int)(getWidth()/1.26), (int)(getHeight()/1.22));
        g.drawString("x "+players.get(3).getCardPoints(), (int)(getWidth()/1.15), (int)(getHeight()/1.22));
        g.drawString("x "+players.get(3).getCardAmt(), (int)(getWidth()/1.065), (int)(getHeight()/1.22));

        g.setFont(new Font("lol", 1, (int)(getWidth()/75.29)));
        switch(currentPlayer) {
            case 0: {
                g.setColor(new Color(206, 43, 46));
                g.drawString("Player One is taking their turn", (int)(getWidth()/1.32), (int)(getHeight()/1.07));
                break;
            }
            case 1: {
                g.setColor(new Color(231, 178, 64));
                g.drawString("Player Two is taking their turn", (int)(getWidth()/1.32), (int)(getHeight()/1.07));
                break;
            }
            case 2: {
                g.setColor(new Color(63, 106, 208));
                g.drawString("Player Three is taking their turn", (int)(getWidth()/1.32), (int)(getHeight()/1.07));
                break;
            }
            case 3: {
                g.setColor(new Color(181, 57, 153));
                g.drawString("Player Four is taking their turn", (int)(getWidth()/1.32), (int)(getHeight()/1.07));
                break;
            }
        }//end of switch case
    }

    public void drawPlayerCards(Graphics g) {
        Player p = players.get(currentPlayer);
        HashMap<String, HashSet<Card>> cs = p.getCards();
        Color cl = Color.WHITE;
        if(currentPlayer==0) {
            cl = new Color(206, 43, 46);
        }
        else if(currentPlayer==1) {
            cl = new Color(231, 178, 64);
        }
        else if(currentPlayer==2) {
            cl = new Color(63, 106, 208);
        }
        else if(currentPlayer==3) {
            cl = new Color(181, 57, 153);
        }
        int count = 0;
        for(Card c : players.get(currentPlayer).getFiled()) {
            c.setHidden(false);
            c.setXPos((int)(getWidth()/48.75));
            c.setYPos((int)(getHeight()/3.35)+ count*((int)(getHeight()/13)));
            c.setSize((int)(getWidth()/13));
            c.paint(cl, g);
            count++;
        }
        count = 0;
        for(Card c : cs.get("archive")) {
            c.setHidden(false);
            c.setXPos((int)(getWidth()/6.09));
            c.setYPos((int)(getHeight()/4.9)+ count*((int)(getHeight()/20.72)));
            c.setSize((int)(getWidth()/9.915));
            c.paint(cl, g);
            count++;
        }
        count = 0;
        for(Card c : cs.get("pick")) {
            c.setHidden(false);
            c.setXPos((int)(getWidth()/6.09));
            c.setYPos((int)(getHeight()/1.54)+ count*((int)(getHeight()/20.72)));
            c.setSize((int)(getWidth()/9.915));
            c.paint(cl, g);
            count++;
        }
        count = 0;
        for(Card c : cs.get("build")) {
            c.setHidden(false);
            c.setXPos((int)(getWidth()/2.91));
            c.setYPos((int)(getHeight()/1.54)+ count*((int)(getHeight()/20.72)));
            c.setSize((int)(getWidth()/9.915));
            c.paint(cl, g);
            count++;
        }
        count = 0;
        for(Card c : cs.get("upgrades")) {
            c.setHidden(false);
            c.setXPos((int)(getWidth()/2.01));
            c.setYPos((int)(getHeight()/1.55)+ count*((int)(getHeight()/17.92)));
            c.setSize((int)(getWidth()/9.75));
            c.paint(cl, g);
            count++;
        }
        count = 0;
        for(Card c : cs.get("converter")) {
            c.setHidden(false);
            c.setXPos((int)(getWidth()/1.625));
            c.setYPos((int)(getHeight()/2.02)+ count*((int)(getHeight()/17.92)));
            c.setSize((int)(getWidth()/11.25));
            c.paint(cl, g);
            count++;
        }
    }

    public void drawDeck(Graphics g) {
        Color cl = Color.WHITE;
        if(currentPlayer==0) {
            cl = new Color(206, 43, 46);
        }
        else if(currentPlayer==1) {
            cl = new Color(231, 178, 64);
        }
        else if(currentPlayer==2) {
            cl = new Color(63, 106, 208);
        }
        else if(currentPlayer==3) {
            cl = new Color(181, 57, 153);
        }
        deck1.setDisplaySize((int)(getWidth()/8.53));
        deck1.setDisplayLocation((int)(getWidth()/1.969), (int)(getHeight()/16.67));
        deck1.paint(cl, g);
        deck2.setDisplaySize((int)(getWidth()/5.818));
        deck2.setDisplayLocation((int)(getWidth()/1.939), (int)(getHeight()/3.28));
        deck2.paint(cl, g);
        deck3.setDisplaySize((int)(getWidth()/8.53));
        deck3.setDisplayLocation((int)(getWidth()/1.671), (int)(getHeight()/16.67));
        deck3.paint(cl, g);
    }

    public void hideAllCards() {
        for(Card c : deck1.getAllCards()) {
            c.setHidden(true);
            c.setHighlighted(false);
        }
        for(Card c : deck2.getAllCards()) {
            c.setHidden(true);
            c.setHighlighted(false);
        }
        for(Card c : deck3.getAllCards()) {
            c.setHidden(true);
            c.setHighlighted(false);
        }
        Card car = deck1.getDeckBack();
        car.setHidden(true);
        car.setHighlighted(false);
        car = deck2.getDeckBack();
        car.setHidden(true);
        car.setHighlighted(false);
        car = deck3.getDeckBack();
        car.setHidden(true);
        car.setHighlighted(false);
        for(int i = 0; i<4; i++) {
            HashMap<String, HashSet<Card>> cs = players.get(i).getCards();
            ArrayList<Card> f = players.get(i).getFiled();
            for(String s : cs.keySet()) {
                for(Card c : cs.get(s)) {
                    c.setHidden(true);
                    c.setHighlighted(false);
                }
            }
            for(Card c : f) {
                c.setHidden(true);
                c.setHighlighted(false);
            }
        }
    }

    public void mouseEntered(MouseEvent e)	{	}
    public void mouseExited(MouseEvent e)	{	}
    public void mousePressed(MouseEvent e)	{	}
    public void mouseReleased(MouseEvent e)	{	}
    public void mouseClicked(MouseEvent e) 	{
        int x = e.getX();
        int y = e.getY();
        if(e.getButton() == e.BUTTON1) {
            switch (gameState) {
                case 0 : {
                    int r = (int)(getWidth()/14.55)/2;
                    int h = (int)(getHeight()/14.73);
                    int w = (int)(getWidth()/9.91);
                    if(!gameManualPanel.getVisible() && x>=(getWidth()/2.69) && x<= (getWidth()/2.69)+(getWidth()/3.87) && y>=(getHeight()/1.59) && y<=(getHeight()/1.59)+(getHeight()/7.68)) {
                        gameState = 1;
                        repaint();
                    }
                    else if(!gameManualPanel.getVisible() && x>=(getWidth()/2.69) && x<= (getWidth()/2.69)+(getWidth()/3.87) && y>=(getHeight()/1.26) && y<=(getHeight()/1.26)+(getHeight()/7.68)) {
                        gameManualPanel.setVisible(true);
                        repaint();
                    }

                    else if(gameManualPanel.getVisible() && Math.pow(x-w-r,2)+Math.pow(y-h-r, 2) <= Math.pow(r, 2)) {
                        gameManualPanel.setVisible(false);
                        repaint();
                    }
                    gameManualPanel.mouseClicked(x, y);
                    break;
                }//end of case 0
                case 1 : {
                    if(x>=getWidth()/85 && x<=getWidth()/85+(int)(getWidth()/10.5) && y >=(int)(getHeight()/9.15) && y<=(int)(getHeight()/9.15)+(int)(getHeight()/14.73)) {
                        currentPlayer++;
                        if(currentPlayer>=4) {
                            currentPlayer = 0;
                        }
                        repaint();
                    }
                    else if(x>=getWidth()/85 && x<=getWidth()/85+(int)(getWidth()/10.5) && y >=(int)(getHeight()/41) && y<=(int)(getHeight()/41)+(int)(getHeight()/14.73)) {
                        gameManualPanel.setVisible(true);
                        gameState = 7;
                        repaint();
                    }
                    else if(x>=(int)(getWidth()/7.6) && x<=(int)(getWidth()/7.6)+(int)(getWidth()/5.97) && y>=getHeight()/24 && y<=getHeight()/24+(int)(getHeight()/7.13)) {
                        if(players.get(currentPlayer).getFiled().size()<players.get(currentPlayer).getFileAmt()) {
                            gameState = 3;
                            repaint();
                        }
                    }
                    else if(x>=(int)(getWidth()/3.25) && x<=(int)(getWidth()/3.25)+(int)(getWidth()/5.97) && y>=getHeight()/24 && y<=getHeight()/24+(int)(getHeight()/7.13)) {
                        if(players.get(currentPlayer).getFiled().size()<players.get(currentPlayer).getFileAmt() || players.get(currentPlayer).getAllMarbles().size() >0) {
                            gameState = 5;
                            repaint();
                        }
                    }
                    else if(x>=(int)(getWidth()/7.6) && x<=(int)(getWidth()/7.6)+(int)(getWidth()/5.97) && y>=(int)(getHeight()/2.04) && y<=(int)(getHeight()/2.04)+(int)(getHeight()/7.13)) {
                        if(players.get(currentPlayer).getAllMarbles().size() < players.get(currentPlayer).getMarbleAmt()) {
                            gameState=2;
                            repaint();
                        }
                    }
                    else if(x>=(int)(getWidth()/3.25) && x<=(int)(getWidth()/3.25)+(int)(getWidth()/5.97) && y>=(int)(getHeight()/2.04) && y<=(int)(getHeight()/2.04)+(int)(getHeight()/7.13)) {
                        if(players.get(currentPlayer).getAllMarbles().size()>0) {
                            gameState = 4;
                            buildPanel.setStateBefore(1);
                            repaint();
                        }
                    }
                    //player inventory and deck :/
                    else if(x>=(int)(getWidth()/1.45) && x<=(int)(getWidth()/1.45) + (int)(getWidth()/40.34) && y>=(int)(getHeight()/24.03)&& y<=(int)(getHeight()/24.03)+ (int)(getHeight()/22.86)) {
                        deckPanel.setVisible(true);
                        gameState = 8;
                        repaint();
                    }
                    else if(x>=(int)(getWidth()/1.053) && x<=(int)(getWidth()/1.053)+(int)(getWidth()/53.18)) {
                        if(y>=(int)(getHeight()/7.25) && y<=(int)(getHeight()/7.25)+(int)(getHeight()/30.14)) {
                            playerInventoryPanel.setVisible(true);
                            playerInventoryPanel.setPlayer(0);
                            gameState = 9;
                        }
                        else if(y>=(int)(getHeight()/3) && y<=(int)(getHeight()/3)+(int)(getHeight()/30.14)) {
                            playerInventoryPanel.setVisible(true);
                            playerInventoryPanel.setPlayer(1);
                            gameState = 9;
                        }
                        else if(y>=(int)(getHeight()/1.9) && y<=(int)(getHeight()/1.9)+(int)(getHeight()/30.14)) {
                            playerInventoryPanel.setVisible(true);
                            playerInventoryPanel.setPlayer(2);
                            gameState = 9;
                        }
                        else if(y>=(int)(getHeight()/1.39) && y<=(int)(getHeight()/1.39)+(int)(getHeight()/30.14)) {
                            playerInventoryPanel.setVisible(true);
                            playerInventoryPanel.setPlayer(3);
                            gameState = 9;
                        }
                    }
                    break;
                }
                case 2 : {
                    if(x>=getWidth()/85 && x<=getWidth()/85+(int)(getWidth()/10.5) && y >=(int)(getHeight()/9.15) && y<=(int)(getHeight()/9.15)+(int)(getHeight()/14.73)) {
                        gameState = 1;
                    }
                    for(int i = 0; i<6; i++) {
                        int r = marbles.get(i).getRadius();
                        int h = marbles.get(i).getY();
                        int w = marbles.get(i).getX();
                        if(Math.pow(x-w-r, 2)+Math.pow(y-h-r, 2)<=Math.pow(r, 2)) {
                            Marble m = marbles.remove(i);
                            players.get(currentPlayer).addMarble(m);
                            players.get(currentPlayer).setAction(1, null, "", m, m.getType());
                            gameState=6;
                            repaint();
                        }
                    }
                    break;
                }
                case 3 : {
                    if(!deckPanel.getVisible() && x>=getWidth()/85 && x<=getWidth()/85+(int)(getWidth()/10.5) && y >=(int)(getHeight()/9.15) && y<=(int)(getHeight()/9.15)+(int)(getHeight()/14.73)) {
                        gameState = 1;
                    }
                    else if(x>=(int)(getWidth()/1.45) && x<=(int)(getWidth()/1.45) + (int)(getWidth()/40.34) && y>=(int)(getHeight()/24.03)&& y<=(int)(getHeight()/24.03)+ (int)(getHeight()/22.86)) {
                        deckPanel.setVisible(true);
                        repaint();
                    }
                    else if(deckPanel.getVisible()) {
                        int r = (int)(getWidth()/20.63)/2;
                        int h = (int)(getHeight()/8.02);
                        int w = (int)(getWidth()/8.25);
                        if(Math.pow(x-w-r,2)+Math.pow(y-h-r, 2) <= Math.pow(r, 2)) {
                            deckPanel.setVisible(false);
                            repaint();
                        }
                    }
                    Card c = deck1.getCardClicked(x, y);
                    if(c!=null) {
                        int ind = deck1.getIndClicked(x, y);
                        deck1.removeDisplay(ind);
                        players.get(currentPlayer).file(c);
                        players.get(currentPlayer).setAction(2, c, null, null, null);
                        gameState = 6;
                        repaint();
                    }
                    else if(deck2.getCardClicked(x, y) != null) {
                        c = deck2.removeDisplay(deck2.getIndClicked(x, y));
                        players.get(currentPlayer).file(c);
                        players.get(currentPlayer).setAction(2, c, null, null, null);
                        gameState = 6;
                        repaint();
                    }
                    else if(deck3.getCardClicked(x, y) != null) {
                        c = deck2.removeDisplay(deck3.getIndClicked(x, y));
                        players.get(currentPlayer).file(c);
                        players.get(currentPlayer).setAction(2, c, null, null, null);
                        gameState = 6;
                        repaint();
                    }
                    break;
                }
                case 4 : {
                    if(buildPanel.getStateBefore()==1 && x>=getWidth()/85 && x<=getWidth()/85+(int)(getWidth()/10.5) && y >=(int)(getHeight()/9.15) && y<=(int)(getHeight()/9.15)+(int)(getHeight()/14.73)) {
                        gameState = 1;
                    }
                    else if(!buildPanel.getVisible() && x>=(int)(getWidth()/1.45) && x<=(int)(getWidth()/1.45) + (int)(getWidth()/40.34) && y>=(int)(getHeight()/24.03)&& y<=(int)(getHeight()/24.03)+ (int)(getHeight()/22.86)) {
                        deckPanel.setVisible(true);
                        repaint();
                    }
                    else if(!playerInventoryPanel.getVisible() && x>=(int)(getWidth()/1.053) && x<=(int)(getWidth()/1.053)+(int)(getWidth()/53.18)) {
                        if(y>=(int)(getHeight()/7.25) && y<=(int)(getHeight()/7.25)+(int)(getHeight()/30.14)) {
                            playerInventoryPanel.setVisible(true);
                            playerInventoryPanel.setPlayer(0);
                        }
                        else if(y>=(int)(getHeight()/3) && y<=(int)(getHeight()/3)+(int)(getHeight()/30.14)) {
                            playerInventoryPanel.setVisible(true);
                            playerInventoryPanel.setPlayer(1);
                        }
                        else if(y>=(int)(getHeight()/1.9) && y<=(int)(getHeight()/1.9)+(int)(getHeight()/30.14)) {
                            playerInventoryPanel.setVisible(true);
                            playerInventoryPanel.setPlayer(2);
                        }
                        else if(y>=(int)(getHeight()/1.39) && y<=(int)(getHeight()/1.39)+(int)(getHeight()/30.14)) {
                            playerInventoryPanel.setVisible(true);
                            playerInventoryPanel.setPlayer(3);
                        }
                    }
                    else if(playerInventoryPanel.getVisible()) {
                        int r = (int)(getWidth()/33.43)/2;
                        int h = (int)(getHeight()/33.25);
                        int w = (int)(getWidth()/29.25);
                        if(Math.pow(x-w-r, 2)+Math.pow(y-h-r, 2) <= Math.pow(r, 2)) {
                            playerInventoryPanel.setVisible(false);
                        }
                    }
                    else if(deckPanel.getVisible()) {
                        int r = (int)(getWidth()/20.63)/2;
                        int h = (int)(getHeight()/8.02);


                        int w = (int)(getWidth()/8.25);
                        if(Math.pow(x-w-r,2)+Math.pow(y-h-r, 2) <= Math.pow(r, 2)) {
                            deckPanel.setVisible(false);
                            repaint();
                        }
                    }
                    else if(!buildPanel.getVisible() && buildPanel.getCard() == null) {
                        Card c = deck1.getCardClicked(x, y);
                        if(c!=null) {
                            int ind = deck1.getIndClicked(x, y);
                            deck1.getDisplay(ind);
                            buildPanel.setCard(c);
                            buildPanel.setVisible(true);
                            repaint();
                        }
                        else if(deck2.getCardClicked(x, y) != null) {
                            buildPanel.setCard(deck2.getDisplay(deck2.getIndClicked(x, y)));
                            buildPanel.setVisible(true);
                            repaint();
                        }
                        else if(deck3.getCardClicked(x, y) != null) {
                            buildPanel.setCard(deck3.getDisplay(deck3.getIndClicked(x, y)));
                            buildPanel.setVisible(true);
                            repaint();
                        }
                        for(Card car : players.get(currentPlayer).getFiled()) {
                            if (car.isClicked(x, y)) {
                                buildPanel.setCard(car);
                                buildPanel.setVisible(true);
                                repaint();
                            }
                        }
                    }
                    else if(x>=(int)(getWidth()/8.73) && x<=(int)(getWidth()/8.73)+(int)(getWidth()/10.26) && y>=(int)(getHeight()/1.21) && y<=(int)(getHeight()/1.21)+(int)(getHeight()/13.28)) {
                        if(buildPanel.getStateBefore()==5 ) {
                            gameState = 5;
                            researchPanel.setVisible(true);
                            buildPanel.resetBuild();
                            buildPanel.setVisible(false);
                            repaint();
                        }
                        else if(buildPanel.getStateBefore()==1) {
                            gameState = 1;
                            buildPanel.resetBuild();
                            repaint();
                        }
                    }
                    else if(buildPanel.canBuild() && x>=(int)(getWidth()/12.06) && x<=(int)(getWidth()/12.06)+(int)(getWidth()/5.94) && y>=(int)(getHeight()/1.43) && y<=(int)(getHeight()/1.43)+(int)(getHeight()/9.91)) {
                        Player p = players.get(currentPlayer);
                        p.addCard(buildPanel.getCard());
                        String s = buildPanel.getCard().getCostEnergyType();
                        for(int i = 0; i<p.getFiled().size(); i++) {
                            if(p.getFiled().get(i).equals(buildPanel.getCard())) {
                                p.getFiled().remove(i);
                                s+= " archive";
                            }
                        }
                        if(buildPanel.getCard().getRank()==1) {
                            deck1.removeDisplay(buildPanel.getCard());
                        }
                        else if(buildPanel.getCard().getRank()==2) {
                            deck2.removeDisplay(buildPanel.getCard());
                        }
                        else if(buildPanel.getCard().getRank()==3) {
                            deck3.removeDisplay(buildPanel.getCard());
                        }
                        ArrayList<Marble> m = p.subMarbles(buildPanel.getBuild());
                        for(int i = 0; i<m.size(); i++) {
                            marbles.add(m.get(i));
                        }
                        players.get(currentPlayer).setAction(3, buildPanel.getCard(), s, null, null);
                        gameState = 6;
                        buildPanel.resetBuild();
                        researchPanel.resetPanel();
                        repaint();
                    }

                    buildPanel.clicked(x, y, players.get(currentPlayer));
                    break;
                }
                case 5 : {
                    if(!researchPanel.getVisible() && !deckPanel.getVisible() && x>=getWidth()/85 && x<=getWidth()/85+(int)(getWidth()/10.5) && y >=(int)(getHeight()/9.15) && y<=(int)(getHeight()/9.15)+(int)(getHeight()/14.73)) {
                        gameState = 1;
                    }
                    else if(!researchPanel.getVisible() && x>=(int)(getWidth()/1.45) && x<=(int)(getWidth()/1.45) + (int)(getWidth()/40.34) && y>=(int)(getHeight()/24.03)&& y<=(int)(getHeight()/24.03)+ (int)(getHeight()/22.86)) {
                        deckPanel.setVisible(true);
                        repaint();
                    }
                    else if(deckPanel.getVisible()) {
                        int r = (int)(getWidth()/20.63)/2;
                        int h = (int)(getHeight()/8.02);
                        int w = (int)(getWidth()/8.25);
                        if(Math.pow(x-w-r,2)+Math.pow(y-h-r, 2) <= Math.pow(r, 2)) {
                            deckPanel.setVisible(false);
                            repaint();
                        }
                    }
                    else if(!playerInventoryPanel.getVisible() && x>=(int)(getWidth()/1.053) && x<=(int)(getWidth()/1.053)+(int)(getWidth()/53.18)) {
                        if(y>=(int)(getHeight()/7.25) && y<=(int)(getHeight()/7.25)+(int)(getHeight()/30.14)) {
                            playerInventoryPanel.setVisible(true);
                            playerInventoryPanel.setPlayer(0);
                        }
                        else if(y>=(int)(getHeight()/3) && y<=(int)(getHeight()/3)+(int)(getHeight()/30.14)) {
                            playerInventoryPanel.setVisible(true);
                            playerInventoryPanel.setPlayer(1);
                        }
                        else if(y>=(int)(getHeight()/1.9) && y<=(int)(getHeight()/1.9)+(int)(getHeight()/30.14)) {
                            playerInventoryPanel.setVisible(true);
                            playerInventoryPanel.setPlayer(2);
                        }
                        else if(y>=(int)(getHeight()/1.39) && y<=(int)(getHeight()/1.39)+(int)(getHeight()/30.14)) {
                            playerInventoryPanel.setVisible(true);
                            playerInventoryPanel.setPlayer(3);
                        }
                    }
                    else if(playerInventoryPanel.getVisible()) {
                        int r = (int)(getWidth()/33.43)/2;
                        int h = (int)(getHeight()/33.25);
                        int w = (int)(getWidth()/29.25);
                        if(Math.pow(x-w-r, 2)+Math.pow(y-h-r, 2) <= Math.pow(r, 2)) {
                            playerInventoryPanel.setVisible(false);
                        }
                    }
                    else if(deck1.backClicked(x, y)) {
                        ArrayList<Card> temp = new ArrayList<>();
                        for(int i = 0; i<players.get(currentPlayer).getResearchAmt(); i++) {
                            temp.add(deck1.drawFromDeck());
                        }
                        researchPanel.setCards(temp);
                        researchPanel.setVisible(true);
                    }
                    else if(deck2.backClicked(x, y)) {
                        ArrayList<Card> temp = new ArrayList<>();
                        for(int i = 0; i<players.get(currentPlayer).getResearchAmt(); i++) {
                            temp.add(deck2.drawFromDeck());
                        }
                        researchPanel.setCards(temp);
                        researchPanel.setVisible(true);
                    }
                    else if(deck3.backClicked(x, y)) {
                        ArrayList<Card> temp = new ArrayList<>();
                        for(int i = 0; i<players.get(currentPlayer).getResearchAmt(); i++) {
                            temp.add(deck3.drawFromDeck());
                        }
                        researchPanel.setCards(temp);
                        researchPanel.setVisible(true);
                    }
                    else if(researchPanel.getVisible()) {
                        if(researchPanel.cardChosen()) {
                            if(researchPanel.click(x, y)) {
                                repaint();
                            }
                            else if(players.get(currentPlayer).getAllMarbles().size()>0 && x>=(int)(getWidth()/3.655) && x<=(int)(getWidth()/3.655)+(int)(getWidth()/4.875) && y>=(int)(getHeight()/1.68) && y<=(int)(getHeight()/1.68)+(int)(getHeight()/5.73)) {
                                gameState=4;
                                buildPanel.setVisible(true);
                                buildPanel.setCard(researchPanel.getCard());
                                buildPanel.setStateBefore(5);
                                ArrayList<Card> temp = researchPanel.getCards();
                                if(temp.get(0).getRank()==1) {
                                    deck1.addAllToBottom(temp);
                                }
                                else if(temp.get(0).getRank()==2) {
                                    deck2.addAllToBottom(temp);
                                }
                                else if(temp.get(0).getRank()==3) {
                                    deck3.addAllToBottom(temp);
                                }
                                repaint();
                            }
                            if(players.get(currentPlayer).getFiled().size()<players.get(currentPlayer).getFileAmt() && x>=(int)(getWidth()/2.05)
                                    && x<=(int)(getWidth()/2.05)+(int)(getWidth()/4.875) && y>=(int)(getHeight()/1.68) && y<=(int)(getHeight()/1.68)+(int)(getHeight()/5.73)) {
                                players.get(currentPlayer).file(researchPanel.getCard());
                                players.get(currentPlayer).setAction(2, researchPanel.getCard(), null, null, null);
                                gameState=6;
                                ArrayList<Card> temp = researchPanel.getCards();
                                if(temp.get(0).getRank()==1) {
                                    deck1.addAllToBottom(temp);
                                }
                                else if(temp.get(0).getRank()==2) {
                                    deck2.addAllToBottom(temp);
                                }
                                else if(temp.get(0).getRank()==3) {
                                    deck3.addAllToBottom(temp);
                                }
                                researchPanel.resetPanel();
                                repaint();
                            }
                        }
                        if(x>=(int)(getWidth()/2.45) && x<=(int)(getWidth()/2.45)+(int)(getWidth()/6.648) && y>=(int)(getHeight()/1.26)&& y<=(int)(getHeight()/1.26)+(int)(getHeight()/10.9)) {
                            currentPlayer++;
                            if(currentPlayer==4) {
                                currentPlayer = 0;
                            }
                            gameState=1;
                            ArrayList<Card> temp = researchPanel.getCards();
                            if(temp.get(0).getRank()==1) {
                                deck1.addAllToBottom(temp);
                            }
                            else if(temp.get(0).getRank()==2) {
                                deck2.addAllToBottom(temp);
                            }
                            else if(temp.get(0).getRank()==3) {
                                deck3.addAllToBottom(temp);
                            }
                            researchPanel.resetPanel();
                            repaint();
                        }
                        researchPanel.click(x, y);
                    }
                    repaint();
                    break;
                }
                case 6 : {
                    if(researchPanel.getVisible()) {

                    }
                    break;
                }
                case 7 : {
                    int r = (int)(getWidth()/14.55)/2;
                    int h = (int)(getHeight()/14.73);
                    int w = (int)(getWidth()/9.91);
                    if(gameManualPanel.getVisible() && Math.pow(x-w-r,2)+Math.pow(y-h-r, 2) <= Math.pow(r, 2)) {
                        gameManualPanel.setVisible(false);
                        gameState = 1;
                        repaint();
                    }//(int)(getWidth()/1.22), (int)(getHeight()/2.64), (int)(getWidth()/13.02), (int)(getHeight()/5.02)
                    gameManualPanel.mouseClicked(x, y);
                    break;
                }
                case 8 : {
                    int r = (int)(getWidth()/20.63)/2;
                    int h = (int)(getHeight()/8.02);
                    int w = (int)(getWidth()/8.25);
                    if(Math.pow(x-w-r,2)+Math.pow(y-h-r, 2) <= Math.pow(r, 2)) {
                        deckPanel.setVisible(false);
                        gameState = 1;
                        repaint();
                    }//(int)(getWidth()/8.25), (int)(getHeight()/8.02), (int)(getWidth()/20.63), (int)(getHeight()/11.77)
                    break;
                }
                case 9 : {
                    int r = (int)(getWidth()/33.43)/2;
                    int h = (int)(getHeight()/33.25);
                    int w = (int)(getWidth()/29.25);
                    if(Math.pow(x-w-r, 2)+Math.pow(y-h-r, 2) <= Math.pow(r, 2)) {
                        gameState = 1;
                        playerInventoryPanel.setVisible(false);
                        repaint();
                    }
                    break;//(int)(getWidth()/29.25), (int)(getHeight()/33.25), (int)(getWidth()/33.43), (int)(getHeight()/19)
                }
                case 10 : {
                    if(!playerInventoryPanel.getVisible()) {
                        if(x>=(int)(getWidth()/27.21) && x<=(int)(getWidth()/27.21)+(int)(getWidth()/4.64) && y>=(int)(getHeight()/1.37) && y<=(int)(getHeight()/1.37)+(int)(getHeight()/6.3)) {
                            resetPanel();
                            repaint();
                        }
                        else if(x>=(int)(getWidth()/3.714) && x<=(int)(getWidth()/3.714)+(int)(getWidth()/4.64) && y>=(int)(getHeight()/1.37) && y<=(int)(getHeight()/1.37)+(int)(getHeight()/6.3)) {
                            resetPanel();
                            gameState = 1;
                            repaint();
                        }
                        else if(x>=(int)(getWidth()/2.272) && x<=(int)(getWidth()/2.272)+(int)(getWidth()/36.56) && y>=(int)(getHeight()/2.96) && y<=(int)(getHeight()/2.96)+(int)(getHeight()/20.69)) {
                            TreeSet<Player> temp = leaderBoard();
                            Iterator<Player> it = temp.iterator();
                            Player p = it.next();
                            if(p.equals(players.get(0))) {
                                playerInventoryPanel.setVisible(true);
                                playerInventoryPanel.setPlayer(0);
                            }
                            else if(p.equals(players.get(1))) {
                                playerInventoryPanel.setVisible(true);
                                playerInventoryPanel.setPlayer(1);
                            }
                            else if(p.equals(players.get(2))) {
                                playerInventoryPanel.setVisible(true);
                                playerInventoryPanel.setPlayer(2);
                            }
                            else if(p.equals(players.get(3))) {
                                playerInventoryPanel.setVisible(true);
                                playerInventoryPanel.setPlayer(3);
                            }
                        }
                        else if(x>=(int)(getWidth()/1.093) && x<=(int)(getWidth()/1.093)+(int)(getWidth()/36.56)) {
                            if(y>=(int)(getHeight()/7.2) && y<=(int)(getHeight()/7.2)+(int)(getHeight()/20.69)) {
                                TreeSet<Player> temp = leaderBoard();
                                Iterator<Player> it = temp.iterator();
                                int count = 2;
                                Player p = null;
                                while(it.hasNext() && count >0) {
                                    p = it.next();
                                    count--;
                                }
                                if(p.equals(players.get(0))) {
                                    playerInventoryPanel.setVisible(true);
                                    playerInventoryPanel.setPlayer(0);
                                }
                                else if(p.equals(players.get(1))) {
                                    playerInventoryPanel.setVisible(true);
                                    playerInventoryPanel.setPlayer(1);
                                }
                                else if(p.equals(players.get(2))) {
                                    playerInventoryPanel.setVisible(true);
                                    playerInventoryPanel.setPlayer(2);
                                }
                                else if(p.equals(players.get(3))) {
                                    playerInventoryPanel.setVisible(true);
                                    playerInventoryPanel.setPlayer(3);
                                }
                            }
                            else if(y>=(int)(getHeight()/2.52) && y<=(int)(getHeight()/2.52)+(int)(getHeight()/20.69)) {
                                TreeSet<Player> temp = leaderBoard();
                                Iterator<Player> it = temp.iterator();
                                int count = 3;
                                Player p = null;
                                while(it.hasNext() && count >0) {
                                    p = it.next();
                                    count--;
                                }
                                if(p.equals(players.get(0))) {
                                    playerInventoryPanel.setVisible(true);
                                    playerInventoryPanel.setPlayer(0);
                                }
                                else if(p.equals(players.get(1))) {
                                    playerInventoryPanel.setVisible(true);
                                    playerInventoryPanel.setPlayer(1);
                                }
                                else if(p.equals(players.get(2))) {
                                    playerInventoryPanel.setVisible(true);
                                    playerInventoryPanel.setPlayer(2);
                                }
                                else if(p.equals(players.get(3))) {
                                    playerInventoryPanel.setVisible(true);
                                    playerInventoryPanel.setPlayer(3);
                                }
                            }
                            else if(y>=(int)(getHeight()/1.54) && y<=(int)(getHeight()/1.54)+(int)(getHeight()/20.69)) {
                                TreeSet<Player> temp = leaderBoard();
                                Iterator<Player> it = temp.iterator();
                                int count = 4;
                                Player p = null;
                                while(it.hasNext() && count >0) {
                                    p = it.next();
                                    count--;
                                }
                                if(p.equals(players.get(0))) {
                                    playerInventoryPanel.setVisible(true);
                                    playerInventoryPanel.setPlayer(0);
                                }
                                else if(p.equals(players.get(1))) {
                                    playerInventoryPanel.setVisible(true);
                                    playerInventoryPanel.setPlayer(1);
                                }
                                else if(p.equals(players.get(2))) {
                                    playerInventoryPanel.setVisible(true);
                                    playerInventoryPanel.setPlayer(2);
                                }
                                else if(p.equals(players.get(3))) {
                                    playerInventoryPanel.setVisible(true);
                                    playerInventoryPanel.setPlayer(3);
                                }
                            }
                            repaint();
                        }
                    }
                    if(playerInventoryPanel.getVisible()) {
                        int r = (int)(getWidth()/33.43)/2;
                        int h = (int)(getHeight()/33.25);
                        int w = (int)(getWidth()/29.25);
                        if(Math.pow(x-w-r, 2)+Math.pow(y-h-r, 2) <= Math.pow(r, 2)) {
                            playerInventoryPanel.setVisible(false);
                            repaint();
                        }
                    }
                    break;
                }

            }//end of switch case
        }
        repaint();
    }//end of method

    public void resetPanel() {
        gameState = 0;
        cards = new CardMasterList(0, 3);
        players = new ArrayList<>();
        for(int i =0; i<4; i++) {
            players.add(new Player(i+1));
            Iterator<Card> it = cards.getRank(0).iterator();
            players.get(i).addCard(it.next());
        }
        marbles = new ArrayList<>();
        deck1 = new Deck(cards.getRank(1), "-1:2,0 0:1,3 1:3,3 2:1,5 3:3,5", 150, 650, 60, "/cardImages/Rank I Back.png");
        deck1.shuffleAll(new Random());
        deck2 = new Deck(cards.getRank(2), "-1:0,0 0:3,0 1:5,0 2:7,0",220, 660, 220, "/cardImages/Rank II Back.png");
        deck2.shuffleAll(new Random());
        deck3 = new Deck(cards.getRank(3), "-1:2,0 0:2,3 1:2,5", 150, 780, 60, "/cardImages/Rank III Back.png");
        deck3.shuffleAll(new Random());
        gameManualPanel = new GameManualPanel();
        deckPanel = new DeckPanel();
        playerInventoryPanel = new PlayerInventoryPanel();
        researchPanel = new ResearchPanel();
        buildPanel = new BuildPanel();
        chainReactionPanel = new ChainReactionPanel();
        currentPlayer = 0;
        lastRound = false;
        for(int i = 1; i<=13; i++) {
            marbles.add(new Marble("Blue", 0, 0, 0, mImgs));
        }
        for(int i = 1; i<=13; i++) {
            marbles.add(new Marble("Gray", 0, 0, 0, mImgs));
        }
        for(int i = 1; i<=13; i++) {
            marbles.add(new Marble("Red", 0, 0, 0, mImgs));
        }
        for(int i = 1; i<=13; i++) {
            marbles.add(new Marble("Yellow", 0, 0, 0, mImgs));
        }
        for (int i = 1; i<marbles.size(); i++) {
            int randIndex = (int) (Math.random()*marbles.size());
            Marble temp = marbles.get(randIndex);
            marbles.set(randIndex, marbles.get(i));
            marbles.set(i, temp);
        }
    }
    public TreeSet<Player> leaderBoard() {
        TreeSet<Player> temp = new TreeSet<>();
        temp.add(players.get(0));
        temp.add(players.get(1));
        temp.add(players.get(2));
        temp.add(players.get(3));
        return temp;
    }
}*/
}
