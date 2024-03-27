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

public class CascadiaPanel extends JPanel implements MouseListener {
    private Polygon hexagon;
    public CascadiaPanel() {
        add(new JLabel("Hello world!"));
        addMouseListener(this);
    }
    public void paint(Graphics g) {
        super.paint(g);
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

    @Override
    public void mouseClicked(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        if (hexagon.contains(x, y)) {
            getGraphics().drawString("Clicked", 400, 200);
        }
        else {
            getGraphics().drawString("Not clicked", 400, 200);
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

    /**
     * Rotates an image by a specified number of degrees. Makes a copy of the original image, and then
     * uses an AffineTransform to rotate the copy. Returns the rotated copy.
     * @param img The image to be rotated as a BufferedImage
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
