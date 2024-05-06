package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

/**
 * The main panel for the Cascadia game. This manages all the interactions with mouse clicks and interactions with the game. However, it delegates the actual drawing of the game to the other panels.
 * This panel is responsible for drawing the three main panels in the game. It also manages switching between the three panels.
 * In addition, the panel also holds many utility static methods that are used to draw the hexagons and tiles in the game.
 * This is so that the functions can be called in multiple panels without the need to rewrite the logic.
 * @author 0xc60f
 * @see MainBoardPanel
 * @see StartPanel
 */

public class CascadiaPanel extends JPanel implements MouseListener {
    private final StartPanel Menu;
    private final MainBoardPanel MainBoard;
    private final WinnerPanel WinnerScreen;
    private final ScorePanel ScoreScreen;
    private boolean first = true;
    public CascadiaPanel() {
        addMouseListener(this);
        Menu = new StartPanel();
        MainBoard = new MainBoardPanel();
        WinnerScreen = new WinnerPanel();
        ScoreScreen = new ScorePanel();
    }

    public void paint(Graphics g) {
        super.paint(g);

        if (Menu.getVisible()) {
            Menu.paint(g, getWidth(), getHeight());
        } else if (MainBoard.getVisible()) {
            MainBoard.paint(g, getWidth(), getHeight());
        } else if (WinnerScreen.getVisible()) {
            WinnerScreen.paint(g, getWidth(), getHeight());
        } else if (ScoreScreen.getVisible()) {
            ScoreScreen.paint(g, getWidth(), getHeight());
        }
    }


    @Override
    public void mouseClicked(MouseEvent e) {

        if (Menu.getVisible()) {
            Menu.mouseClicked(e);
        } else if (MainBoard.getVisible()) {
            MainBoard.mouseClicked(e);
        } else if (WinnerScreen.getVisible()) {
            WinnerScreen.mouseClicked(e);
            if (first) {
                first = false;
                ScoreScreen.sendScore(MainBoard.getGame());
            }
        } else if (ScoreScreen.getVisible()) {
            ScoreScreen.mouseClicked(e);
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

    /**
     * Draws a single animal tile on top of a habitat tile. The animal tile is drawn so that it covers up the possible options for the habitat tile.
     * This is used when a player places an animal tile on a habitat tile.
     * @param habitatTile The <code>BufferedImage</code> of the habitat tile
     * @param animalTile The <code>BufferedImage</code> of the animal tile that will be placed on top of the habitat tile
     * @return A <code>BufferedImage</code> that is the result of drawing the animal tile on top of the habitat tile.
     */
    public static BufferedImage drawOccupiedTile(BufferedImage habitatTile, BufferedImage animalTile) {
        BufferedImage combined = new BufferedImage(habitatTile.getWidth(), habitatTile.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = combined.createGraphics();
        g2d.drawImage(habitatTile, 0, 0, null);
        //Draw the animal tile on top of the habitat tile in the center
        //Resize animalTile so that it is half of the original size
        animalTile = resizeImage(animalTile, (int) (animalTile.getWidth() / 1.1), (int) (animalTile.getHeight() / 1.1));
        g2d.drawImage(animalTile, (habitatTile.getWidth() - animalTile.getWidth()) / 2, (habitatTile.getHeight() - animalTile.getHeight()) / 2, null);
        g2d.dispose();
        return combined;
    }

    /**
     * Gets the coordinates of where the image for the adjacent hexagon should be drawn. The coordinates are based on the edge of the base hexagon that the adjacent hexagon is connected to.
     * @param baseHexagon The <code>Polygon</code> that represents the base hexagon
     * @param edge The edge of the base hexagon that the adjacent hexagon is connected to. 0 is the top edge, and then goes clockwise.
     * @param newImage The <code>BufferedImage</code> that is used to create the hexagon. The size is used to determine where the vertices are.
     * @throws IllegalArgumentException if the edge is not between 0 and 5
     * @return A <code>Point</code> that contains the x and y coordinates of where the image for the adjacent hexagon should be drawn.
     * The x and y coordinates should be used as the top-left corner of the image, where java draws images.
     * The x coordinate is the key, and the y coordinate is the value.
     */
    public static Point getCoordsAdjacentHexagon(Polygon baseHexagon, int edge, BufferedImage newImage){
        int boundingBoxWidth = newImage.getWidth();
        int boundingBoxHeight = newImage.getHeight();
        int radius = boundingBoxHeight / 2;
        int height = (int) (Math.sqrt(3) * radius);
        int horizontalDistance = (int) (boundingBoxWidth * 0.75);
        Point center = getCenterOfHexagon(baseHexagon);

        int xCoord = center.x;
        int yCoord = center.y;

        switch (edge) {
            case 0 -> yCoord -= height + 20; // Adjusted for better alignment
            case 1 -> {
                xCoord += horizontalDistance; // Adjusted for better alignment
                yCoord -= (height / 2) + 13;
            }
            case 2 -> {
                xCoord += horizontalDistance + 1; // Adjusted for better alignment
                yCoord += (height / 2) + 2;
            }
            case 3 -> yCoord += height + 8; // Adjusted for better alignment
            case 4 -> {
                xCoord -= horizontalDistance -1; // Adjusted for better alignment
                yCoord += (height / 2) + 2;
            }
            case 5 -> {
                xCoord -= horizontalDistance; // Adjusted for better alignment
                yCoord -= (height / 2) + 12;
            }
            default -> throw new IllegalArgumentException("Edge must be between 0 and 5");
        }
        return new Point(xCoord - boundingBoxWidth / 2, yCoord - height / 2);
    }



    /**
     * Creates a hexagon with the specified x and y coordinates, and then draws the image on top of the hexagon.
     * Also creates a silent hexagon that is used to determine where the adjacent hexagon should be drawn.
     * @param x The x coordinate of the top-left corner of the image
     * @param y The y coordinate of the top-left corner of the image
     * @param imageUsed The <code>BufferedImage</code> that is used to create the hexagon
     * @param g The <code>Graphics</code> object that is used to draw the hexagon. Should be called with <code>getGraphics()</code>
     *          from the panel class that is using it.
     */
    public static Polygon createAndDrawHexagon(int x, int y, BufferedImage imageUsed, Graphics g) {
        Polygon hexagon = createHexagon(x, y, imageUsed);
        g.drawImage(imageUsed, x, y, null);
        return hexagon;
    }






    /**
     * Calls the appropriate drawTiles method based on the size of the list of buffered images.
     * @param bufferedList An array of <code>BufferedImage</code> objects that will be drawn on top of each other. The size of the array determines which drawTiles method is called.
     * @return A <code>BufferedImage</code> that is the result of drawing the possible animal tiles on top of the habitat tile.
     * @throws IllegalStateException if the size of the list is not 2, 3, or 4
     */
    public static BufferedImage drawTiles(BufferedImage[] bufferedList) {
        return switch (bufferedList.length) {
            case 2 -> drawTiles(bufferedList[0], bufferedList[1]);
            case 3 -> drawTiles(bufferedList[0], bufferedList[1], bufferedList[2]);
            case 4 -> drawTiles(bufferedList[0], bufferedList[1], bufferedList[2], bufferedList[3]);
            default -> throw new IllegalStateException("Unexpected value: " + bufferedList.length + " tiles\n Must be 2, 3, or 4 tiles");
        };
    }

    /**
     * Draws a single animal tile on top of a habitat tile. The animal tile is resized to half of its original size, and then drawn in the center of the habitat tile.
     * @param habitatTile A <code>BufferedImage</code> of the habitat tile
     * @param animalTile A <code>BufferedImage</code> of the animal tile
     * @return A <code>BufferedImage</code> that is the result of drawing the animal tile on top of the habitat tile.
     */


    private static BufferedImage drawTiles(BufferedImage habitatTile, BufferedImage animalTile) {
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
    private static Point getCenterOfHexagon(Polygon hexagon) {
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
    private static BufferedImage drawTiles(BufferedImage habitatTile, BufferedImage animalTile1, BufferedImage animalTile2) {
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
    private static BufferedImage drawTiles(BufferedImage habitatTile, BufferedImage animalTile1, BufferedImage animalTile2, BufferedImage animalTile3) {
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
    //@NotNull
    public static BufferedImage resizeImage(BufferedImage img, int width, int height) {
        Image tmp = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = resized.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();
        return resized;
    }

    /**
     * Makes a new polygon based on the x and y coordinates of the top-left corner of the image (not the hexagon), and the size of the hexagon.
     * @param x The x coordinate of the top-left corner of the image being drawn
     * @param y The y coordinate of the top-left corner of the image being drawn
     * @param imageUsed The <code>BufferedImage</code> that is used to create the hexagon. The size is used to determine where the vertices are.
     * @return A <code>Polygon</code> that is the result of creating a hexagon with the specified x and y coordinates.
     */
    public static Polygon createHexagon(int x, int y, BufferedImage imageUsed) {
        //Get the center of the image based on the size of the image and the x and y coords
        int xCenter = x + imageUsed.getWidth() / 2;
        int yCenter = y + imageUsed.getHeight() / 2;
        Polygon hexagon = new Polygon();
        for (int i = 0; i < 6; i++) {
            hexagon.addPoint((int) (xCenter + 59 * Math.cos(i * 2 * Math.PI / 6)),
                    (int) (yCenter + 59 * Math.sin(i * 2 * Math.PI / 6)));
        }
        return hexagon;
    }

    /**
     * Rotates an image by a specified number of degrees. Makes a copy of the original image, and then
     * uses an AffineTransform to rotate the copy. Returns the rotated copy.
     * The point of rotation is the center of the image.
     * @param img   The image to be rotated as a BufferedImage
     * @param angle The angle that the image should be rotated, in degrees
     * @return A BufferedImage that is the result of rotating the original image
     * @see AffineTransform
     */
    public static BufferedImage rotateImage(BufferedImage img, double angle) {
        double rads = Math.toRadians(angle);
        int w = img.getWidth();
        int h = img.getHeight();
        BufferedImage rotatedImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = rotatedImage.createGraphics();

        graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        // Translate to the center of the image
        graphics.translate(w / 2, h / 2);
        // Rotate the image
        graphics.rotate(rads);
        // Translate back from the center
        graphics.translate(-w / 2, -h / 2);
        graphics.drawImage(img, 0, 0, null);
        graphics.dispose();

        return rotatedImage;
    }

}
