package org.example;

//import org.jetbrains.annotations.NotNull;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;
import java.util.TreeMap;

/**
 * The main panel for the Cascadia game. Currently, being used to test and debug tile setups, but will eventually be switched to orchestrate
 * the game, and the end and start screen.
 *
 * @author 0xc60f
 * @see JPanel
 */

public class CascadiaPanel extends JPanel implements MouseListener {
    private final StartPanel Menu;
    private final MainBoardPanel MainBoard;
    public CascadiaPanel() {
        addMouseListener(this);
        Menu = new StartPanel();
        MainBoard = new MainBoardPanel();
    }

    public void paint(Graphics g) {
        super.paint(g);

        if (Menu.getVisible()) {
            Menu.paint(g, getWidth(), getHeight());
        } else if (MainBoard.getVisible()) {
            MainBoard.paint(g, getWidth(), getHeight());
        }
    }


    @Override
    public void mouseClicked(MouseEvent e) {

        if (Menu.getVisible()) {
            Menu.mouseClicked(e);
        } else if (MainBoard.getVisible()) {
            MainBoard.mouseClicked(e);
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
     * Gets the coordinates of where the image for the adjacent hexagon should be drawn. The coordinates are based on the edge of the base hexagon that the adjacent hexagon is connected to.
     * @param baseHexagon The <code>Polygon</code> that represents the base hexagon
     * @param edge The edge of the base hexagon that the adjacent hexagon is connected to. 0 is the top edge, and then goes clockwise.
     * @return A <code>TreeMap</code> that contains the x and y coordinates of where the image for the adjacent hexagon should be drawn.
     * The x and y coordinates should be used as the top-left corner of the image, where java draws images.
     * The x coordinate is the key, and the y coordinate is the value.
     */
    public static Point getCoordsAdjacentHexagon(Polygon baseHexagon, int edge){
        Point center = getCenterOfHexagon(baseHexagon);
        int radius = baseHexagon.getBounds().height / 2;
        int height = 2 * radius;
        int width = (int) (Math.sqrt(3) * radius);
        Point coords = new Point();
        switch (edge){
            case 0:
                coords.setLocation(center.x - width / 2, center.y);
                break;
            case 1:
                coords.setLocation(center.x - width / 4, center.y - height / 2);
                break;
            case 2:
                coords.setLocation(center.x + width / 4, center.y - height / 2);
                break;
            case 3:
                coords.setLocation(center.x + width / 2, center.y);
                break;
            case 4:
                coords.setLocation(center.x + width / 4, center.y + height / 2);
                break;
            case 5:
                coords.setLocation(center.x - width / 4, center.y + height / 2);
                break;
        }
        return coords;
    }


    /**
     * Calls the appropriate drawTiles method based on the size of the list of buffered images.
     */
    public static BufferedImage drawTiles(BufferedImage[] bufferedList) {
        BufferedImage combined;

        if (bufferedList.length == 2) {
            combined = drawTiles(bufferedList[0], bufferedList[1]);
        } else if (bufferedList.length == 3) {
            combined = drawTiles(bufferedList[0], bufferedList[1], bufferedList[2]);
        } else {
            combined = drawTiles(bufferedList[0], bufferedList[1], bufferedList[2], bufferedList[3]);
        }
        return combined;
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
    protected static BufferedImage resizeImage(BufferedImage img, int width, int height) {
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
    protected static Polygon createHexagon(int x, int y, BufferedImage imageUsed) {
        //Get the center of the image based on the size of the image and the x and y coords
        int xCenter = x + imageUsed.getWidth() / 2;
        int yCenter = y + imageUsed.getHeight() / 2;
        Polygon hexagon = new Polygon();
        for (int i = 0; i < 6; i++) {
            hexagon.addPoint((int) (xCenter + 55 * Math.cos(i * 2 * Math.PI / 6)),
                    (int) (yCenter + 55 * Math.sin(i * 2 * Math.PI / 6)));
        }
        return hexagon;
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
}
