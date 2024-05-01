package org.example;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;


public class MainBoardPanel extends JPanel implements MouseListener {
    private Polygon viewB1, viewB2, viewPage, downMove, upMove, leftMove, rightMove, endGame, set3, specialButton;
    private boolean viewVis = false;
    private BufferedImage backgroundImage, bearScoring, hawkScoring, salmonScoring, elkScoring, foxScoring, natureToken, testingTile1, testingTile2, testBaseTile, testingTile3;
    private BufferedImage arrUp, arrDown, arrLeft, arrRight;
    private BufferedImage dd, dl, ds, fd, ff, fl, fs, ll, lm, md, mf, mm, ms, sl, ss;
    private BufferedImage bear, elk, fox, hawk, salmon;
    private BufferedImage potentialPlacement;
    private BufferedImage arrowRight, arrowLeft;
    private int boardCenterx, boardCentery, offsetX, offsetY;
    private boolean isVisible = true;
    private final Polygon[] displayedTilesPolygons;
    private boolean displayedTilesClickable, displayedAnimalClickable;
    private String specialButtonString = "";
    private Boolean[] leftArrowClickable, rightArrowClickable;
    private final Polygon[] displayedAnimalPolygons;
    private final Polygon[] leftArrowPolygons;
    private final Polygon[] rightArrowPolygons;
    private Graphics graphics;
    private boolean uniqueToken;
    private GameState gameState;
    private boolean shuffleUsed;
    private int tileClicked, tokenClicked;
    private ArrayList<Polygon> potentialPlacements;
    private static ArrayList<Polygon> playerPlacedTiles;
    private int offsetx, offsety = 0;
    private Game game;


    private String turn = "Turn: 1", action = "Action Prompt";

    public MainBoardPanel() {
        displayedTilesClickable = displayedAnimalClickable = false;
        displayedTilesPolygons = new Polygon[4];
        displayedAnimalPolygons = new Polygon[4];
        leftArrowPolygons = new Polygon[4];
        leftArrowClickable = new Boolean[]{false, false, false, false};
        rightArrowClickable = new Boolean[]{false, false, false, false};
        rightArrowPolygons = new Polygon[4];
        potentialPlacements = new ArrayList<>();
        playerPlacedTiles = new ArrayList<>();
        shuffleUsed = false;
        tileClicked = tokenClicked = 0;
        game = new Game();
        addMouseListener(this);
        importImages();
        gameState = GameState.GAMESTART;
    }

    public void paint(Graphics g, int width, int height) {

        int div = 5;

        int playAreaWidth = (width - width / div);
        int playAreaHeight = (height - height / div);

        int boardCenterx = ((div - 1) / 2) * (width / div);
        int boardCentery = ((div - 1) / 2) * (height / div);

        Font defFont = new Font("Arial", Font.BOLD, width / 90);
        this.graphics = g;
        g.drawImage(backgroundImage, 0, 0, null);
        g.setFont(defFont);

        BufferedImage[] buffList = {ss, bear};
//        BufferedImage test = CascadiaPanel.drawTiles(buffList);
//        g.drawImage(test, boardCenterx + offsetx, boardCentery + offsety, null);

        Color beigeColor = new Color(255, 221, 122);
        g.setColor(beigeColor);
        g.fillRoundRect(width / 100, height / 100, width / 8, height / 14, 30, 30); // turn counter

        Rectangle turnAlign = new Rectangle(width / 100, height / 100, width / 8, height / 14);

        g.fillRoundRect(width / 100, playAreaHeight - height / 100 - height / 10, width / 8, height / 10, 30, 30); // action prompt

        Rectangle actionPromptAlign = new Rectangle(width / 100, playAreaHeight - height / 100 - height / 10, width / 8, height / 10);

        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, width / 90));

        drawCenteredString(g, turn, turnAlign, defFont);

        drawScoring(g, width, height, div);

        //Draw the Player 2 and Player 3 buttons
        g.fillRoundRect(width / 2 + width / 6, height / 100, width / 8, height / 14, 10, 10);
        g.fillRoundRect(width / 2 + width / 6, height / 100 + height / 14 + height / 100, width / 8, height / 14, 10, 10);

        Rectangle p2Align = new Rectangle(width / 2 + width / 6, height / 100, width / 8, height / 14);
        Rectangle p3Align = new Rectangle(width / 2 + width / 6, height / 100 + height / 14 + height / 100, width / 8, height / 14);

        g.setColor(beigeColor);
        g.fillRect(width - width / div, 0, width / div, height);
        //Draw a large rectangle covering the bottom of the screen
        g.fillRect(0, height - height / div, width, height / div);
        g.fillRoundRect(width / 2 + width / 6, height / 100, width / 8, height / 14, 30, 30);

        g.fillRoundRect(width / 2 + width / 6, height / 100 + height / 14 + height / 100, width / 8, height / 14, 30, 30);

        g.setColor(Color.BLACK);

        drawCenteredString(g, "Player 2", p2Align, defFont);
        drawCenteredString(g, "Player 3", p3Align, defFont);

        Rectangle specialButtonDef = new Rectangle(width / 100, playAreaHeight - height / 100 - height / 10 - height / 10 - height / 100, width / 8, height / 10);
        if (!specialButtonString.isEmpty()) {
            g.fillRoundRect(width / 100, playAreaHeight - height / 100 - height / 10 - height / 10 - height / 100, width / 8, height / 10, 30, 30);
            g.drawRect(width / 100, playAreaHeight - height / 100 - height / 10 - height / 10 - height / 100, width / 8, height / 10);
        }
        specialButton = new Polygon(new int[]{width / 100, width / 100, width / 100 + width / 8, width / 100 + width / 8}, new int[]{playAreaHeight - height / 100 - height / 10 - height / 10 - height / 100 + height / 10, playAreaHeight - height / 100 - height / 10 - height / 10 - height / 100, playAreaHeight - height / 100 - height / 10 - height / 10 - height / 100, playAreaHeight - height / 100 - height / 10 - height / 10 - height / 100 + height / 10}, 4);
        g.setColor(beigeColor);
        g.setColor(Color.BLACK);

        int debugRectWidth = width / 8;
        int debugRectHeight = height / 14;
        int debugXPos = width / 2 + width / 6;
        int debugYPos = height / 100 + height / 14 + height / 100;

        int[] xPoints = {debugXPos, debugXPos, debugXPos + debugRectWidth, debugXPos + debugRectWidth};
        int[] yPoints = {debugYPos + debugRectHeight, debugYPos, debugYPos, debugYPos + debugRectHeight};

        viewB1 = new Polygon(xPoints, yPoints, 4);

        //g.drawPolygon(viewB1);

        int debugRectWidth2 = width / 8;
        int debugRectHeight2 = height / 14;
        int debugXPos2 = width / 2 + width / 6;
        int debugYPos2 = height / 100;

        int[] xPoints2 = {debugXPos2, debugXPos2, debugXPos2 + debugRectWidth2, debugXPos2 + debugRectWidth2};
        int[] yPoints2 = {debugYPos2 + debugRectHeight2, debugYPos2, debugYPos2, debugYPos2 + debugRectHeight2};

        viewB2 = new Polygon(xPoints2, yPoints2, 4);


        int debugRectWidth3 = playAreaWidth / 2 + playAreaWidth / 3;
        int debugRectHeight3 = playAreaHeight / 2 + playAreaHeight / 3;
        int debugXPos3 = (playAreaWidth / 2 + playAreaWidth / 3) / 10;
        int debugYPos3 = (playAreaHeight / 2 + playAreaHeight / 3) / 10;

        int[] xPoints3 = {debugXPos3, debugXPos3, debugXPos3 + debugRectWidth3, debugXPos3 + debugRectWidth3};
        int[] yPoints3 = {debugYPos3 + debugRectHeight3, debugYPos3, debugYPos3, debugYPos3 + debugRectHeight3};

        viewPage = new Polygon(xPoints3, yPoints3, 4);

        if (viewVis) {
            g.setColor(beigeColor);
            g.fillPolygon(viewPage);
        }
        //Draw a vertical line 150 pixels dividing the bottom rectangle into a square and long rectangle
        g.drawLine(width - width / div, 0, width - width / div, height);
        g.drawLine(width / div - 75, height - height / div, width / div - 75, height);

        drawNatureTokenCount(g, 0, width, height, div);

        g.drawLine(width / div + 240, height - height / div, width / div + 240, height);
        g.drawLine(width / div + 555, height - height / div, width / div + 555, height);
        g.drawLine(width / div + 845, height - height / div, width / div + 845, height);
        drawScoring(g, width, height, div);

        int regSize = 3;

        int dpadCenterx = playAreaWidth - playAreaWidth / 16;
        int dpadCentery = playAreaHeight - playAreaHeight / 7;
        int xsync = -172;
        int ysync = 200;
        int ydis = 20;
        int xdis = 20;
        g.drawImage(arrUp, dpadCenterx, (dpadCentery - height / ydis), null);
        g.drawImage(arrDown, dpadCenterx, (dpadCentery + height / ydis), null);
        g.drawImage(arrLeft, (dpadCenterx - height / xdis) + height / xsync, dpadCentery + height / ysync, null);
        g.drawImage(arrRight, (dpadCenterx + height / xdis) + height / xsync, dpadCentery + height / ysync, null);

        int debugRectWidth4 = xdis * 2;
        int debugRectHeight4 = xdis * 4 + ydis * 3;
        int debugXPos4 = (dpadCenterx + height / xdis) + height / xsync;
        int debugYPos4 = (dpadCentery - height / ydis);

        int[] xPoints4 = {debugXPos4, debugXPos4, debugXPos4 + debugRectWidth4, debugXPos4 + debugRectWidth4};
        int[] yPoints4 = {debugYPos4 + debugRectHeight4, debugYPos4, debugYPos4, debugYPos4 + debugRectHeight4};

        debugRectWidth4 = xdis * 2;
        debugRectHeight4 = xdis * 4 + ydis * 3;
        debugXPos4 = (dpadCenterx - height / xdis) + height / xsync;
        debugYPos4 = (dpadCentery - height / ydis);

        int[] xPoints5 = {debugXPos4, debugXPos4, debugXPos4 + debugRectWidth4, debugXPos4 + debugRectWidth4};
        int[] yPoints5 = {debugYPos4 + debugRectHeight4, debugYPos4, debugYPos4, debugYPos4 + debugRectHeight4};

        debugRectWidth4 = xdis * 4 + ydis * 3;
        debugRectHeight4 = xdis * 2;
        debugXPos4 = (dpadCenterx - height / xdis) + height / xsync;
        debugYPos4 = (dpadCentery - height / ydis);

        int[] xPoints6 = {debugXPos4, debugXPos4, debugXPos4 + debugRectWidth4, debugXPos4 + debugRectWidth4};
        int[] yPoints6 = {debugYPos4 + debugRectHeight4, debugYPos4, debugYPos4, debugYPos4 + debugRectHeight4};

        debugRectWidth4 = xdis * 4 + ydis * 3;
        debugRectHeight4 = xdis * 2;
        debugXPos4 = (dpadCenterx - height / xdis) + height / xsync;
        debugYPos4 = (dpadCentery + height / ydis);

        int[] xPoints7 = {debugXPos4, debugXPos4, debugXPos4 + debugRectWidth4, debugXPos4 + debugRectWidth4};
        int[] yPoints7 = {debugYPos4 + debugRectHeight4, debugYPos4, debugYPos4, debugYPos4 + debugRectHeight4};

        rightMove = new Polygon(xPoints4, yPoints4, 4);
        leftMove = new Polygon(xPoints5, yPoints5, 4);
        upMove = new Polygon(xPoints6, yPoints6, 4);
        downMove = new Polygon(xPoints7, yPoints7, 4);

        Color darkBeigeColor = new Color(0, 0, 0, 129);

        g.setColor(darkBeigeColor);

        for (int i = 0; i < 4; i++) {
            Point[] arrows = drawArrows(width, height, div, i, g);
            clearArrows(g, width, height, div, i);
            rightArrowPolygons[i] = new Polygon(new int[]{arrows[0].x, arrows[0].x, arrows[0].x + 50, arrows[0].x + 50}, new int[]{arrows[0].y + 50, arrows[0].y, arrows[0].y, arrows[0].y + 50}, 4);
            leftArrowPolygons[i] = new Polygon(new int[]{arrows[1].x, arrows[1].x, arrows[1].x + 50, arrows[1].x + 50}, new int[]{arrows[1].y + 50, arrows[1].y, arrows[1].y, arrows[1].y + 50}, 4);
        }

        this.boardCenterx = boardCenterx;
        this.boardCentery = boardCentery;
        // Following Polygon is for debugging - clicking it ends the game automatically

        g.setColor(Color.green);
        int debugRectWidth6 = 100;
        int debugRectHeight6 = 100;
        int debugXPos6 = 900;
        int debugYPos6 = 200;

        int[] xPoints9 = {debugXPos6, debugXPos6, debugXPos6 + debugRectWidth6, debugXPos6 + debugRectWidth6};
        int[] yPoints9 = {debugYPos6 + debugRectHeight6, debugYPos6, debugYPos6, debugYPos6 + debugRectHeight6};

        set3 = new Polygon(xPoints9, yPoints9, 4);

        g.drawPolygon(set3);


        g.setColor(Color.red);
        int debugRectWidth5 = 100;
        int debugRectHeight5 = 100;
        int debugXPos5 = 800;
        int debugYPos5 = 200;

        int[] xPoints8 = {debugXPos5, debugXPos5, debugXPos5 + debugRectWidth5, debugXPos5 + debugRectWidth5};
        int[] yPoints8 = {debugYPos5 + debugRectHeight5, debugYPos5, debugYPos5, debugYPos5 + debugRectHeight5};

        endGame = new Polygon(xPoints8, yPoints8, 4);

        g.drawPolygon(endGame);
        ArrayList<HabitatTile> displayedTiles = game.getDisplayedTiles();
        for (int i = 0; i < 4; i++) {
            Point tempPoint = drawTilesDownbar(g, width, height, div, i, displayedTiles.get(i).getImage());
            //Associate a polygon with each tile
            Polygon tempPoly = CascadiaPanel.createHexagon(tempPoint.x, tempPoint.y, displayedTiles.get(i).getImage());
            displayedTilesPolygons[i] = tempPoly;
        }

        ArrayList<WildlifeToken> displayedWildlife = game.getDisplayedWildlife();
        int bcount = 0;
        int ecount = 0;
        int fcount = 0;
        int hcount = 0;
        int scount = 0;
        for (int i = 0; i < 4; i++) {
            switch (displayedWildlife.get(i)) {
                case BEAR -> bcount++;
                case ELK -> ecount++;
                case FOX -> fcount++;
                case HAWK -> hcount++;
                case SALMON -> scount++;
            }
            ;
        }
        if (bcount == 4) {
            game.shuffleDisplayedWildLife(WildlifeToken.BEAR);
        } else if (ecount == 4) {
            game.shuffleDisplayedWildLife(WildlifeToken.ELK);
        } else if (fcount == 4) {
            game.shuffleDisplayedWildLife(WildlifeToken.FOX);
        } else if (hcount == 4) {
            game.shuffleDisplayedWildLife(WildlifeToken.HAWK);
        } else if (scount == 4) {
            game.shuffleDisplayedWildLife(WildlifeToken.SALMON);
        }
        if (bcount == 3 || ecount == 3 || fcount == 3 || hcount == 3 || scount == 3) {
            specialButtonString = "Shuffle";
        }
        displayedWildlife = game.getDisplayedWildlife();
        for (int i = 0; i < 4; i++) {
            Point drawPoint = switch (displayedWildlife.get(i)) {
                case BEAR -> drawAnimalTiles(g, width, height, div, i, bear);
                case ELK -> drawAnimalTiles(g, width, height, div, i, elk);
                case FOX -> drawAnimalTiles(g, width, height, div, i, fox);
                case HAWK -> drawAnimalTiles(g, width, height, div, i, hawk);
                case SALMON -> drawAnimalTiles(g, width, height, div, i, salmon);
            };
            //Create a square polygon that starts at drawPoint and is 60 pixels
            Polygon tempPoly = new Polygon(new int[]{drawPoint.x, drawPoint.x, drawPoint.x + 40, drawPoint.x + 40}, new int[]{drawPoint.y + 40, drawPoint.y, drawPoint.y, drawPoint.y + 40}, 4);
            displayedAnimalPolygons[i] = tempPoly;
        }


        g.setColor(Color.BLACK);
        drawCenteredString(g, specialButtonString, specialButtonDef, defFont);

        drawMainPlayerTiles(g, boardCenterx, boardCentery, offsetx, offsety, game.getCurrentPlayer());
        //drawPotentialPlacement(g, boardCenterx, boardCentery, offsetx, offsety, game.getCurrentPlayer());
        displayedTilesClickable = true;

        if (gameState.equals(GameState.GAMESTART)) {
            action = "Pick a tile";
            drawCenteredString(g, action, actionPromptAlign, defFont);
        }


        if (gameState.equals(GameState.TILECLICKED)) {
            Font smallFont = new Font("Arial", Font.BOLD, width / 180);
            clearActionPrompt(g, width, height, div);
            action = "Rotate your tile, then place it on the yellow tile.";
            drawCenteredString(g, action, actionPromptAlign, smallFont);
            drawArrows(width, height, div, tileClicked, g);
            drawPotentialPlacement(g, boardCenterx, boardCentery, offsetX, offsetY, game.getCurrentPlayer());

        }
        if (gameState.equals(GameState.TILEPLACE)) {
            clearTilesDownbar(g, width, height, div, tileClicked);
            leftArrowClickable[tileClicked] = false;
            rightArrowClickable[tileClicked] = false;
            displayedTilesClickable = false;
            clearArrows(g, width, height, div, tileClicked);

            HabitatTile ht = game.getDisplayedTiles().remove(tileClicked);
            ArrayList<HabitatTile> neighbors = findNeighborsFromPotentialPolygon(ht.getPolygon(), game.getCurrentPlayer());
            ht.setNeighbors(neighbors);
            System.out.println(neighbors);
            //ArrayList<HabitatTile> existingTiles = new ArrayList<>(game.getCurrentPlayer().getPlayerTiles().keySet());
            game.getCurrentPlayer().addTile(ht);
//            for (HabitatTile h : existingTiles) {
//                //Check if h's polygon borders ht's polygon
//                if (h.getPolygon().intersects(ht.getPolygon().getBounds2D())) {
//                    //Find which edge of h borders ht based on geometry
//                    if (h.getPolygon().getBounds2D().getMaxY() == ht.getPolygon().getBounds2D().getMinY()) {
//                        htNeighbors[0] = h;
//                    } else if (h.getPolygon().getBounds2D().getMinY() == ht.getPolygon().getBounds2D().getMaxY()) {
//                        htNeighbors[1] = h;
//                    } else if (h.getPolygon().getBounds2D().getMaxX() == ht.getPolygon().getBounds2D().getMinX()) {
//                        htNeighbors[2] = h;
//                    } else if (h.getPolygon().getBounds2D().getMinX() == ht.getPolygon().getBounds2D().getMaxX()) {
//                        htNeighbors[3] = h;
//                    } else if (h.getPolygon().getBounds2D().getMaxX() == ht.getPolygon().getBounds2D().getMaxX()) {
//                        htNeighbors[4] = h;
//                    } else if (h.getPolygon().getBounds2D().getMinX() == ht.getPolygon().getBounds2D().getMinX()) {
//                        htNeighbors[5] = h;
//                    }
//                }
//
//            }
//            ArrayList<HabitatTile> allNewTiles = new ArrayList<>(game.getCurrentPlayer().getPlayerTiles().keySet());
//            allNewTiles.stream().map(HabitatTile::getNeighbors).forEach(System.out::println);
//            System.out.println(game.getCurrentPlayer().getPlayerTiles().keySet());
            drawMainPlayerTiles(g, boardCenterx, boardCentery, offsetx, offsety, game.getCurrentPlayer());

        }


    }

    /**
     * Imports the images that are used in the game. The images are stored in the resources folder, and are imported using the ImageIO class.
     */
    private void importImages() {
        try {
            backgroundImage = ImageIO.read(Objects.requireNonNull(CascadiaPanel.class.getResource("/Menu/Background.jpg")));
            bearScoring = ImageIO.read(Objects.requireNonNull(CascadiaPanel.class.getResource("/Images/WildlifeTokens/bear-small.jpg")));
            hawkScoring = ImageIO.read(Objects.requireNonNull(CascadiaPanel.class.getResource("/Images/WildlifeTokens/hawk-small.jpg")));
            salmonScoring = ImageIO.read(Objects.requireNonNull(CascadiaPanel.class.getResource("/Images/WildlifeTokens/salmon-small.jpg")));
            elkScoring = ImageIO.read(Objects.requireNonNull(CascadiaPanel.class.getResource("/Images/WildlifeTokens/elk-small.jpg")));
            foxScoring = ImageIO.read(Objects.requireNonNull(CascadiaPanel.class.getResource("/Images/WildlifeTokens/fox-small.jpg")));
            natureToken = ImageIO.read(Objects.requireNonNull(CascadiaPanel.class.getResource("/Images/nature-token.png")));
            dd = ImageIO.read(Objects.requireNonNull(CascadiaPanel.class.getResource("/Images/Tiles/dd.png")));
            dl = ImageIO.read(Objects.requireNonNull(CascadiaPanel.class.getResource("/Images/Tiles/dl.png")));
            ds = ImageIO.read(Objects.requireNonNull(CascadiaPanel.class.getResource("/Images/Tiles/ds.png")));
            fd = ImageIO.read(Objects.requireNonNull(CascadiaPanel.class.getResource("/Images/Tiles/fd.png")));
            ff = ImageIO.read(Objects.requireNonNull(CascadiaPanel.class.getResource("/Images/Tiles/ff.png")));
            fl = ImageIO.read(Objects.requireNonNull(CascadiaPanel.class.getResource("/Images/Tiles/fl.png")));
            fs = ImageIO.read(Objects.requireNonNull(CascadiaPanel.class.getResource("/Images/Tiles/fs.png")));
            ll = ImageIO.read(Objects.requireNonNull(CascadiaPanel.class.getResource("/Images/Tiles/ll.png")));
            lm = ImageIO.read(Objects.requireNonNull(CascadiaPanel.class.getResource("/Images/Tiles/lm.png")));
            md = ImageIO.read(Objects.requireNonNull(CascadiaPanel.class.getResource("/Images/Tiles/md.png")));
            mf = ImageIO.read(Objects.requireNonNull(CascadiaPanel.class.getResource("/Images/Tiles/mf.png")));
            mm = ImageIO.read(Objects.requireNonNull(CascadiaPanel.class.getResource("/Images/Tiles/mm.png")));
            ms = ImageIO.read(Objects.requireNonNull(CascadiaPanel.class.getResource("/Images/Tiles/ms.png")));
            sl = ImageIO.read(Objects.requireNonNull(CascadiaPanel.class.getResource("/Images/Tiles/sl.png")));
            ss = ImageIO.read(Objects.requireNonNull(CascadiaPanel.class.getResource("/Images/Tiles/ss.png")));
            bear = ImageIO.read(Objects.requireNonNull(CascadiaPanel.class.getResource("/Images/WildlifeTokens/BEAR.png")));
            elk = ImageIO.read(Objects.requireNonNull(CascadiaPanel.class.getResource("/Images/WildlifeTokens/ELK.png")));
            fox = ImageIO.read(Objects.requireNonNull(CascadiaPanel.class.getResource("/Images/WildlifeTokens/FOX.png")));
            hawk = ImageIO.read(Objects.requireNonNull(CascadiaPanel.class.getResource("/Images/WildlifeTokens/HAWK.png")));
            salmon = ImageIO.read(Objects.requireNonNull(CascadiaPanel.class.getResource("/Images/WildlifeTokens/SALMON.png")));

            arrUp = ImageIO.read(Objects.requireNonNull(CascadiaPanel.class.getResource("/Images/arrowup.png")));
            arrDown = ImageIO.read(Objects.requireNonNull(CascadiaPanel.class.getResource("/Images/arrowdown.png")));
            arrLeft = ImageIO.read(Objects.requireNonNull(CascadiaPanel.class.getResource("/Images/arrowleft.png")));
            arrRight = ImageIO.read(Objects.requireNonNull(CascadiaPanel.class.getResource("/Images/arrowright.png")));

            testBaseTile = ImageIO.read(Objects.requireNonNull(CascadiaPanel.class.getResource("/Images/Tiles/dd.png")));
            testingTile1 = ImageIO.read(Objects.requireNonNull(CascadiaPanel.class.getResource("/Images/Tiles/fd.png")));
            testingTile2 = ImageIO.read(Objects.requireNonNull(CascadiaPanel.class.getResource("/Images/Tiles/ff.png")));
            testingTile3 = ImageIO.read(Objects.requireNonNull(CascadiaPanel.class.getResource("/Images/Tiles/ss.png")));

            arrowRight = ImageIO.read(Objects.requireNonNull(CascadiaPanel.class.getResource("/Images/arrow.png")));
            arrowRight = CascadiaPanel.resizeImage(arrowRight, 50, 50);
            arrowRight = CascadiaPanel.rotateImage(arrowRight, 90);
            arrowLeft = CascadiaPanel.rotateImage(arrowRight, 180);

            potentialPlacement = ImageIO.read(Objects.requireNonNull(CascadiaPanel.class.getResource("/Images/potentialPlacement.png")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        bearScoring = CascadiaPanel.resizeImage(bearScoring, 190, 190);
        hawkScoring = CascadiaPanel.resizeImage(hawkScoring, 190, 190);
        salmonScoring = CascadiaPanel.resizeImage(salmonScoring, 190, 190);
        elkScoring = CascadiaPanel.resizeImage(elkScoring, 190, 190);
        foxScoring = CascadiaPanel.resizeImage(foxScoring, 190, 190);
    }

    /**
     * Draws the scoring on the right side of the screen. The scoring is drawn in the order of bear, hawk, salmon, elk, and fox.
     * @param g The {@link Graphics} object that is used to draw the scoring.
     * @param width The width of the screen.
     * @param height The height of the screen.
     * @param div The number of divisions that the screen is divided into.
     */
    private void drawScoring(Graphics g, int width, int height, int div) {
        g.drawRect(width - width / div, 0, width / div, height);
        int x = width - width / div + width / div / 2 - bearScoring.getWidth() / 2;
        int y = height / div / 2 - bearScoring.getHeight() / 2;
        g.drawImage(bearScoring, x, y, null);
        y += height / div;
        g.drawImage(hawkScoring, x, y, null);
        y += height / div;
        g.drawImage(salmonScoring, x, y, null);
        y += height / div;
        g.drawImage(elkScoring, x, y, null);
        y += height / div;
        g.drawImage(foxScoring, x, y, null);
    }

    public static void drawMainPlayerTiles(Graphics g, int boardCenterX, int boardCenterY, int offsetx, int offsety, Player p) {
        playerPlacedTiles.clear();
        HashMap<HabitatTile, WildlifeToken> playerDraw = p.getPlayerTiles();
        // Get all the HabitatTiles in playerDraw
        ArrayList<HabitatTile> playerTiles = new ArrayList<>(playerDraw.keySet());

        // Create a set to store drawn tiles
        Set<HabitatTile> drawnTiles = new HashSet<>();

        for (HabitatTile ht : playerTiles) {
            // Draw ht if it hasn't been drawn yet
            Polygon base = null;
            if (!drawnTiles.contains(ht)) {
                base = CascadiaPanel.createAndDrawHexagon(boardCenterX + offsetx, boardCenterY + offsety, ht.getImage(), g);
                drawnTiles.add(ht); // Add ht to the set of drawn tiles
                ht.setPolygon(base);
                playerPlacedTiles.add(base);

            }
            ArrayList<HabitatTile> adjTiles = ht.getNeighbors();
            for (HabitatTile adjTile : adjTiles) {
                if (adjTile != null && !drawnTiles.contains(adjTile) && base != null) {
                    // Draw adjTile if it hasn't been drawn yet
                    Point drawPoint = CascadiaPanel.getCoordsAdjacentHexagon(base, adjTiles.indexOf(adjTile), adjTile.getImage());
                    drawnTiles.add(adjTile); // Add adjTile to the set of drawn tiles
                    Polygon tempPoly = CascadiaPanel.createAndDrawHexagon(drawPoint.x, drawPoint.y, adjTile.getImage(), g);
                    playerPlacedTiles.add(tempPoly);
                    adjTile.setPolygon(tempPoly);
                }
            }
        }
    }

    private void drawPotentialPlacement(Graphics g, int boardCenterX, int boardCenterY, int offsetx, int offsety, Player p) {
        potentialPlacements.clear();
        HashMap<HabitatTile, WildlifeToken> playerDraw = p.getPlayerTiles();
        ArrayList<HabitatTile> playerTiles = new ArrayList<>(playerDraw.keySet());
        Set<HabitatTile> drawnTiles = new HashSet<>();
        Set<Polygon> potentialPlacementSet = new HashSet<>();

        for (HabitatTile ht : playerTiles) {
            Polygon base = ht.getPolygon();
            ArrayList<HabitatTile> adjTiles = ht.getNeighbors();
            for (int i = 0; i < adjTiles.size(); i++) {
                HabitatTile adjTile = adjTiles.get(i);
                if (adjTile == null && base != null) {
                    Point drawPoint = CascadiaPanel.getCoordsAdjacentHexagon(base, i, potentialPlacement);
                    Polygon potentialPlacementPolygon = CascadiaPanel.createHexagon(drawPoint.x, drawPoint.y, potentialPlacement);
                    potentialPlacementSet.add(potentialPlacementPolygon);
                }
            }
        }

        for (Polygon potentialPlacementPolygon : potentialPlacementSet) {
            Point potPolyCenter = new Point((int) potentialPlacementPolygon.getBounds2D().getCenterX(), (int) potentialPlacementPolygon.getBounds2D().getCenterY());
            boolean tooClose = false;

            for (Polygon existingPolygon : potentialPlacements) {
                Point existingCenter = new Point((int) existingPolygon.getBounds2D().getCenterX(), (int) existingPolygon.getBounds2D().getCenterY());
                if (potPolyCenter.distance(existingCenter) <= 5.00) {
                    tooClose = true;
                    break;
                }
            }

            if (!tooClose) {
                // Adjust the coordinates to center the image
                int imageX = potPolyCenter.x - potentialPlacement.getWidth() / 2;
                int imageY = potPolyCenter.y - potentialPlacement.getHeight() / 2;
                g.drawImage(potentialPlacement, imageX, imageY, null);
                potentialPlacements.add(potentialPlacementPolygon);
            }
        }

    }

    /**
     * Draws the nature token count in the bottom left corner of the screen.
     * Drawn with 4 nature token images in each corner of the square, and the player's nature token count in the center.
     * @param g The <code>Graphics</code> object that is used to draw the nature token count.
     * @param numTokens The number of nature tokens that the player has.
     * @param width The width of the screen.
     * @param height The height of the screen.
     * @param div The number of divisions that the screen is divided into.
     */
    private void drawNatureTokenCount(Graphics g, int numTokens, int width, int height, int div) {
        //Draw 4 nature tokens in each corner of the square in the bottom left corner
        g.drawImage(natureToken, 0, height - height / div, null);
        g.drawImage(natureToken, 0, height - 50, null);
        g.drawImage(natureToken, width / div - 140, height - 50, null);
        g.drawImage(natureToken, width / div - 140, height - height / div, null);

        //Draw the player's nature tokens in the bottom right corner in the center
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 25));
        g.drawString(String.valueOf(numTokens), width / div - 230, height - height / div + height / div / 2 + 5);
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

    public void waitForSeconds(double seconds) {
        try {
            Thread.sleep((int) (seconds * 1000));
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        for (int i = 0; i < 4; i++) {
            if (displayedTilesPolygons[i].contains(e.getPoint()) && displayedTilesClickable) {
                action = "Rotate your tile, then click to place the tile.";
                gameState = GameState.TILECLICKED;
                leftArrowClickable[i] = true;
                rightArrowClickable[i] = true;
                tileClicked = i;
                repaint();
            }
        }
        for (int i = 0; i < 4; i++) {
            if (displayedAnimalPolygons[i].contains(e.getPoint())) {
                System.out.println("Animal " + i + " clicked");
            }
        }
        for (int i = 0; i < 4; i++) {
            if (leftArrowPolygons[i].contains(e.getPoint()) && leftArrowClickable[i]) {
                HabitatTile ht = game.getDisplayedTiles().get(i);
                //Rotate the tile 60 degrees counterclockwise
                clearTilesDownbar(graphics, this.getWidth(), this.getHeight(), 5, i);
                drawTilesDownbar(graphics, this.getWidth(), this.getHeight(), 5, i, ht.getImage());
                ht.rotateCounterClockwise();
            }
            if (rightArrowPolygons[i].contains(e.getPoint()) && rightArrowClickable[i]) {
                HabitatTile ht = game.getDisplayedTiles().get(i);
                //Rotate the tile 60 degrees clockwise
                clearTilesDownbar(graphics, this.getWidth(), this.getHeight(), 5, i);
                drawTilesDownbar(graphics, this.getWidth(), this.getHeight(), 5, i, ht.getImage());
                ht.rotateClockwise();
            }
        }
        //Check which polygon in potentialPlacements contains e.getPoint()
        if (gameState.equals(GameState.TILECLICKED)){
            boolean found = false;
            for (Polygon p : potentialPlacements) {
                if (p.contains(e.getPoint())) {
                    System.out.println(new Point((int) p.getBounds().getCenterX(), (int) p.getBounds().getCenterY()));
                    gameState = GameState.TILEPLACE;
                    game.getDisplayedTiles().get(tileClicked).setPolygon(p);
                    found = true;
                    break;
                }
            }
            if (found){
                potentialPlacements.clear();
                repaint();
            }
        }
        if (viewB1.contains(x, y)) {
            viewVis = true;
        } else if (viewB2.contains(x, y)) {
            viewVis = true;
        } else if (viewPage.contains(x, y) && viewVis) {
            viewVis = false;
        } else if (endGame.contains(x, y)) {
            setVisible(false);
        } else if (specialButton.contains(x, y)) {
            if (specialButtonString.equals("Shuffle") && !shuffleUsed) {
                game.shuffleDisplayedWildLife(null);
                shuffleUsed = true;
            }
        } else {
            if (upMove.contains(x, y)) {
                offsety = offsety + 10;
                this.offsetY = offsety;
            }
            if (downMove.contains(x, y)) {
                offsety = offsety - 10;
                this.offsetY = offsety;
            }
            if (leftMove.contains(x, y)) {
                offsetx = offsetx + 10;
                this.offsetX = offsetx;
            }
            if (rightMove.contains(x, y)) {
                offsetx = offsetx - 10;
                this.offsetX = offsetx;
            }
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

    private void writeActionPrompt(Graphics g, String text, Rectangle rect) {
        Color tempColor = g.getColor();
        g.setColor(Color.BLACK);
        drawCenteredString(g, text, rect, new Font("Arial", Font.BOLD, this.getWidth() / 90));
        g.setColor(tempColor);

    }

    /**
     * Draws the arrows on the screen. The arrows are drawn in the center of the boxes holding the tiles and tokes, and
     * are used to rotate the tiles before locking their position,
     * @param width The width of the screen.
     * @param height The height of the screen.
     * @param div The number of divisions that the screen is divided into.
     * @param numBox Which box the arrows are being drawn in. 0 is the first box, 1 is the second box, 2 is the third box, and 3 is the fourth box.
     * @return A {@code Point} array that contains the x and y coordinates of the arrows. The first element is the right arrow, and the second element is the left arrow.
     * @throws IllegalArgumentException If numBox is not between 0 and 3.
     */
    private Point[] drawArrows(int width, int height, int div, int numBox, Graphics g) {
        Point[] arrowPoints = new Point[2];
        switch (numBox) {
            case 0:
                g.drawImage(arrowRight, width / div + 190, height - height / div + height / div / 2 - 25, null);
                g.drawImage(arrowLeft, width / div - 75, height - height / div + height / div / 2 - 25, null);
                arrowPoints[0] = new Point(width / div + 190, height - height / div + height / div / 2 - 25);
                arrowPoints[1] = new Point(width / div - 75, height - height / div + height / div / 2 - 25);
                break;
            case 1:
                g.drawImage(arrowRight, width / div + 505, height - height / div + height / div / 2 - 25, null);
                g.drawImage(arrowLeft, width / div + 240, height - height / div + height / div / 2 - 25, null);
                arrowPoints[0] = new Point(width / div + 505, height - height / div + height / div / 2 - 25);
                arrowPoints[1] = new Point(width / div + 240, height - height / div + height / div / 2 - 25);
                break;
            case 2:
                g.drawImage(arrowRight, width / div + 795, height - height / div + height / div / 2 - 25, null);
                g.drawImage(arrowLeft, width / div + 555, height - height / div + height / div / 2 - 25, null);
                arrowPoints[0] = new Point(width / div + 795, height - height / div + height / div / 2 - 25);
                arrowPoints[1] = new Point(width / div + 555, height - height / div + height / div / 2 - 25);
                break;
            case 3:
                g.drawImage(arrowRight, width / div + 1100, height - height / div + height / div / 2 - 25, null);
                g.drawImage(arrowLeft, width / div + 845, height - height / div + height / div / 2 - 25, null);
                arrowPoints[0] = new Point(width / div + 1100, height - height / div + height / div / 2 - 25);
                arrowPoints[1] = new Point(width / div + 845, height - height / div + height / div / 2 - 25);
                break;
            default:
                throw new IllegalArgumentException("numBox must be between 0 and 3.");
        }
        return arrowPoints;
    }

    /**
     * Draws the corresponding animal tile that goes along with the habitat tile.
     * @param g The {@code Graphics} object that is used to draw the animal tile.
     * @param width The width of the screen.
     * @param height The height of the screen.
     * @param div The number of divisions that the screen is divided into.
     * @param pos The position of the animal tile. 0 is the first animal tile, 1 is the second animal tile, 2 is the third animal tile, and 3 is the fourth animal tile.
     * @param tile The {@code BufferedImage} object that is used to draw the animal tile.
     * @return A {@code Point} object that represents the x and y coordinates of the animal tile.
     * @throws IllegalArgumentException If pos is not between 0 and 3.
     */
    private Point drawAnimalTiles(Graphics g, int width, int height, int div, int pos, BufferedImage tile) {
        int x = 0;
        switch (pos) {
            case 0 -> x = width / div - 75 + 5;
            case 1 -> x = width / div + 240 + 5;
            case 2 -> x = width / div + 555 + 5;
            case 3 -> x = width / div + 845 + 5;
            default -> throw new IllegalArgumentException("pos must be between 0 and 3.");
        }
        int y = height - height / div + height / div / 2 - 25 - 50;
        g.drawImage(CascadiaPanel.resizeImage(tile, 40, 40), x, y, null);
        return new Point(x, y);
    }

    /**
     * Clears the animal tiles that are drawn on the screen. This is used when the animal tiles are cleared or replaced
     * by other animal tiles.
     * @param g The {@code Graphics} object that is used to draw the animal tiles.
     * @param width The width of the screen.
     * @param height The height of the screen.
     * @param div The number of divisions that the screen is divided into.
     * @param pos The position of the animal tile. 0 is the first animal tile, 1 is the second animal tile, 2 is the third animal tile, and 3 is the fourth animal tile.
     * @throws IllegalArgumentException If pos is not between 0 and 3.
     */
    private void clearAnimalTiles(Graphics g, int width, int height, int div, int pos) {
        int x = 0;
        switch (pos) {
            case 0 -> x = width / div - 75 + 5;
            case 1 -> x = width / div + 240 + 5;
            case 2 -> x = width / div + 555 + 5;
            case 3 -> x = width / div + 845 + 5;
            default -> throw new IllegalArgumentException("pos must be between 0 and 3.");
        }
        int y = height - height / div + height / div / 2 - 25 - 50;
        Color tempColor = g.getColor();
        g.setColor(new Color(255, 221, 122));
        g.fillRoundRect(x, y, 40, 40, 10, 10);
        g.setColor(tempColor);
    }

    private ArrayList<HabitatTile> findNeighborsFromPotentialPolygon(Polygon poly, Player p){
        HabitatTile[] neighbors = new HabitatTile[6];
        ArrayList<HabitatTile> existingTiles = new ArrayList<>(p.getPlayerTiles().keySet());

        // Calculate the center of the bounding box of the original polygon
        Point polyCenter = new Point((int) poly.getBounds2D().getCenterX(), (int) poly.getBounds2D().getCenterY());

        for (HabitatTile existingTile : existingTiles) {
            Polygon existingPolygon = existingTile.getPolygon();

            // Check if the bounding boxes intersect
            if (poly.getBounds2D().intersects(existingPolygon.getBounds2D())) {
                // Calculate the center of the bounding box of the existing tile
                Point existingCenter = new Point((int) existingPolygon.getBounds2D().getCenterX(), (int) existingPolygon.getBounds2D().getCenterY());

                // Calculate the relative position
                double relativeX = polyCenter.getX() - existingCenter.getX();
                double relativeY = polyCenter.getY() - existingCenter.getY();

                // Define the leeway
                double leeway = 10.0;

                // Check each edge case
                if (Math.abs(relativeX) <= leeway && relativeY < -leeway) {
                    neighbors[0] = existingTile;
                } else if (relativeX > leeway && relativeY < -leeway) {
                    neighbors[1] = existingTile;
                } else if (relativeX > leeway && relativeY > leeway) {
                    neighbors[2] = existingTile;
                } else if (Math.abs(relativeX) <= leeway && relativeY > leeway) {
                    neighbors[3] = existingTile;
                } else if (relativeX < -leeway && relativeY > leeway) {
                    neighbors[4] = existingTile;
                } else if (relativeX < -leeway && relativeY < -leeway) {
                    neighbors[5] = existingTile;
                }
            }
        }

        // Convert the array to an ArrayList and return it
        return new ArrayList<>(Arrays.asList(neighbors));
    }

    /**
     * Clears the arrows that are drawn on the screen. This is used when the arrows are no longer needed.
     * For example, a player is done with their turn and is no longer rotating the tile.
     * @param g The {@code Graphics} object that is used to draw the arrows.
     * @param width The width of the screen.
     * @param height The height of the screen.
     * @param div The number of divisions that the screen is divided into.
     * @param numBox The box that the arrows are being cleared from. 0 is the first box, 1 is the second box, 2 is the third box, and 3 is the fourth box.
     * @throws IllegalArgumentException If numBox is not between 0 and 3.
     */
    private void clearArrows(Graphics g, int width, int height, int div, int numBox) {
        Color tempColor = g.getColor();
        g.setColor(new Color(255, 221, 122));
        switch (numBox) {
            case 0:
                g.fillRect(width / div - 70, height - height / div + height / div / 2 - 25, 50, 50);
                g.fillRect(width / div + 190, height - height / div + height / div / 2 - 25, 50, 50);
                break;
            case 1:
                g.fillRect(width / div + 245, height - height / div + height / div / 2 - 25, 50, 50);
                g.fillRect(width / div + 505, height - height / div + height / div / 2 - 25, 50, 50);
                break;
            case 2:
                g.fillRect(width / div + 560, height - height / div + height / div / 2 - 25, 50, 50);
                g.fillRect(width / div + 795, height - height / div + height / div / 2 - 25, 50, 50);
                break;
            case 3:
                g.fillRect(width / div + 848, height - height / div + height / div / 2 - 25, 50, 50);
                g.fillRect(width / div + 1100, height - height / div + height / div / 2 - 25, 50, 50);
                break;
            default:
                throw new IllegalArgumentException("numBox must be between 0 and 3.");
        }
        g.setColor(tempColor);
    }

    /**
     * Draws the tiles in the downbar. These are the habitat tiles that the player can choose from to place on the
     * screen.
     * @param g The {@code Graphics} object that is used to draw the tiles.
     * @param width The width of the screen.
     * @param height The height of the screen.
     * @param div The number of divisions that the screen is divided into.
     * @param numPos The position of the tile. 0 is the first tile, 1 is the second tile, 2 is the third tile, and 3 is the fourth tile.
     * @param tile The {@code BufferedImage} object that is used to draw the tile.
     * @return A {@code Point} object that contains the x and y coordinates of where the tile was drawn.
     * @throws IllegalArgumentException If numPos is not between 0 and 3.
     */
    private Point drawTilesDownbar(Graphics g, int width, int height, int div, int numPos, BufferedImage tile) {
        int x = 0;
        switch (numPos) {
            case 0 -> x = width / div + 20;
            case 1 -> x = width / div + 338;
            case 2 -> x = width / div + 642;
            case 3 -> x = width / div + 937;
            default -> throw new IllegalArgumentException("pos must be between 0 and 3.");
        }
        int y = height - height / div + height / div / 2 - 55;
        g.drawImage(tile, x, y, null);
        //Return the coordinates of where the tile was drawn
        return new Point(x, y);
    }

    /**
     * Clears the tiles in the downbar. This is used when the tiles are no longer needed or need to be switched.
     * @param g The {@code Graphics} object that is used to draw the tiles. Should be called with {@code getGraphics()}.
     * @param width  The width of the screen.
     * @param height The height of the screen.
     * @param div The number of divisions that the screen is divided into.
     * @param numPos The position of the tile. 0 is the first tile, 1 is the second tile, 2 is the third tile, and 3 is the fourth tile.
     * @throws IllegalArgumentException If numPos is not between 0 and 3.
     */
    private void clearTilesDownbar(Graphics g, int width, int height, int div, int numPos) {
        int x = 0;
        switch (numPos) {
            case 0 -> x = width / div + 15;
            case 1 -> x = width / div + 325;
            case 2 -> x = width / div + 635;
            case 3 -> x = width / div + 928;
            default -> throw new IllegalArgumentException("pos must be between 0 and 3.");
        }
        int y = height - height / div + height / div / 2 - 60;
        Color tempColor = g.getColor();
        g.setColor(new Color(255, 221, 122));
        g.fillRect(x, y, 140, 120);
        g.setColor(tempColor);
    }

    /**
     * Clears the nature token count in the bottom left corner of the screen. This is used when the nature token count needs to be
     * updated, so make sure to call {@code drawNatureTokenCount()} after calling this method.
     * @param g The {@code Graphics} object that is used to draw the nature token count.
     * @param width The width of the screen.
     * @param height The height of the screen.
     * @param div The number of divisions that the screen is divided into.
     */
    private void clearNatureTokenCount(Graphics g, int width, int height, int div) {
        Color tempColor = g.getColor();
        g.setColor(new Color(255, 221, 122));
        g.fillRect(0, height - height / div, width / div - 80, height / div);
        g.setColor(tempColor);
    }

    /**
     * Clears the turn counter in the top left corner of the screen. This is used when the turn counter needs to be updated, so make
     * sure to draw the turn number after calling this method.
     * @param g The {@code Graphics} object that is used to draw the turn counter.
     * @param width The width of the screen.
     * @param height The height of the screen.
     * @param div The number of divisions that the screen is divided into.
     */
    private void clearTurnCounter(Graphics g, int width, int height, int div) {
        Color tempColor = g.getColor();
        g.setColor(new Color(255, 221, 122));
        g.fillRoundRect(width / 100, height / 100, width / 8, height / 14, 30, 30);
        g.setColor(tempColor);
    }

    /**
     * Clears the action prompt in the bottom left corner of the screen. This is used when the action prompt needs to be updated, so make
     * sure to draw the action prompt after calling this method.
     * @param g The {@code Graphics} object that is used to draw the action prompt.
     * @param width The width of the screen.
     * @param height The height of the screen.
     * @param div The number of divisions that the screen is divided into.
     */
    private void clearActionPrompt(Graphics g, int width, int height, int div) {
        Color tempColor = g.getColor();
        g.setColor(new Color(255, 221, 122));
        g.fillRoundRect(width / 100, (height - height / div) - height / 100 - height / 10, width / 8, height / 10, 30, 30);
        g.setColor(tempColor);
    }

    /**
     * Clears the board view button in the top right corner of the screen. This is used when the board view button needs to be updated, so make
     * sure to draw the board view button after calling this method.
     * @param g The {@code Graphics} object that is used to draw the board view button.
     * @param width The width of the screen.
     * @param height The height of the screen.
     * @param div The number of divisions that the screen is divided into.
     * @param numButton The button that is being cleared. Zero is the first button, 1 is the second button.
     * @throws IllegalArgumentException If numButton is not 0 or 1.
     */
    private void clearBoardViewButton(Graphics g, int width, int height, int div, int numButton) {
        Color tempColor = g.getColor();
        g.setColor(new Color(255, 221, 122));
        switch (numButton) {
            case 0 -> g.fillRoundRect(width / 2 + width / 6, height / 100, width / 8, height / 14, 30, 30);
            case 1 ->
                    g.fillRoundRect(width / 2 + width / 6, height / 100 + height / 14 + height / 100, width / 8, height / 14, 30, 30);
            default -> throw new IllegalArgumentException("numButton must be 0 or 1.");
        }

        g.setColor(tempColor);
    }

    /**
     * Clears the special button that is used for overpopulation.
     * @param g The {@link Graphics} object that is used to draw the special button.
     * @param width The width of the screen.
     * @param height The height of the screen.
     * @param div The number of divisions that the screen is divided into.
     */
    private void clearSpecialButton(Graphics g, int width, int height, int div) {
        Color tempColor = g.getColor();
        g.setColor(new Color(255, 221, 122));
        g.fillRoundRect(width / 100, (height - height / div) - height / 100 - height / 10 - height / 10 - height / 100, width / 8, height / 10, 30, 30);
        g.setColor(tempColor);
    }


}
