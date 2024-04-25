package org.example;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.stream.IntStream;

public class HabitatTile {
    public WildlifeToken w = null;
    //Sides in relation to Biomes
    private ArrayList<WildlifeToken> possibleAnimals;
    private BufferedImage image;
    private Polygon polygon;
    private int x;
    private int y;
    public Biome habitat1;
    public Biome habitat2;
    private TreeMap<Integer, Biome> biomes;
    //Constructor for the Biomes of the HabitatTile
    private ArrayList<HabitatTile> neighbors;
    private int value;
    private List<Edge> edges;

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public HabitatTile(String h1, String h2, int animal1, int animal2, int animal3) {
        possibleAnimals = new ArrayList<>();
        biomes = new TreeMap<>();
        edges = new LinkedList<>();
        neighbors = new ArrayList<>();
        switch (h1) {
            case "d" -> habitat1 = Biome.DESERT;
            case "l" -> habitat1 = Biome.LAKE;
            case "s" -> habitat1 = Biome.SWAMP;
            case "f" -> habitat1 = Biome.FOREST;
            case "m" -> habitat1 = Biome.MOUNTAIN;
        }
        switch (h2) {
            case "d" -> habitat2 = Biome.DESERT;
            case "l" -> habitat2 = Biome.LAKE;
            case "s" -> habitat2 = Biome.SWAMP;
            case "f" -> habitat2 = Biome.FOREST;
            case "m" -> habitat2 = Biome.MOUNTAIN;
        }

        switch (animal1) {
            case 1 -> possibleAnimals.add(WildlifeToken.ELK);
            case 2 -> possibleAnimals.add(WildlifeToken.HAWK);
            case 3 -> possibleAnimals.add(WildlifeToken.BEAR);
            case 4 -> possibleAnimals.add(WildlifeToken.SALMON);
            case 5 -> possibleAnimals.add(WildlifeToken.FOX);
        }
        switch (animal2) {
            case 1 -> possibleAnimals.add(WildlifeToken.ELK);
            case 2 -> possibleAnimals.add(WildlifeToken.HAWK);
            case 3 -> possibleAnimals.add(WildlifeToken.BEAR);
            case 4 -> possibleAnimals.add(WildlifeToken.SALMON);
            case 5 -> possibleAnimals.add(WildlifeToken.FOX);
        }
        switch (animal3) {
            case 1 -> possibleAnimals.add(WildlifeToken.ELK);
            case 2 -> possibleAnimals.add(WildlifeToken.HAWK);
            case 3 -> possibleAnimals.add(WildlifeToken.BEAR);
            case 4 -> possibleAnimals.add(WildlifeToken.SALMON);
            case 5 -> possibleAnimals.add(WildlifeToken.FOX);
        }
        try {
            image = initializeImage();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    //Instantiates the HabitatTile as the Node class of the Cascadia Graph
    // BufferedReader

    /**
     * Initializes the image of the habitat tile. Uses helper methods in the CascadiaPanel class to draw the image.
     * @return A {@code BufferedImage} object that represents the image of the habitat tile
     */
    private BufferedImage initializeImage() {
        try {
            String h1, h2;
            switch (this.habitat1) {
                case DESERT -> h1 = "d";
                case LAKE -> h1 = "l";
                case SWAMP -> h1 = "s";
                case FOREST -> h1 = "f";
                case MOUNTAIN -> h1 = "m";
                default -> throw new IllegalStateException("Unexpected value: " + this.habitat1);
            }
            switch (this.habitat2) {
                case DESERT -> h2 = "d";
                case LAKE -> h2 = "l";
                case SWAMP -> h2 = "s";
                case FOREST -> h2 = "f";
                case MOUNTAIN -> h2 = "m";
                default -> throw new IllegalStateException("Unexpected value: " + this.habitat2);
            }
            BufferedImage baseImage = ImageIO.read(Objects.requireNonNull(getClass().getResource("/Images/Tiles/" + h1 + h2 + ".png")));
            BufferedImage[] animals = new BufferedImage[possibleAnimals.size() + 1];
            for (int i = 0; i < possibleAnimals.size(); i++) {
                animals[i + 1] = ImageIO.read(Objects.requireNonNull(getClass().getResource("/Images/WildlifeTokens/" + possibleAnimals.get(i) + ".png")));
            }
            animals[0] = baseImage;
            return CascadiaPanel.drawTiles(animals);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public WildlifeToken getW() {
        return w;
    }

    public void setW(WildlifeToken w) {
        this.w = w;
    }

    public ArrayList<WildlifeToken> getPossibleAnimals() {
        return possibleAnimals;
    }

    public void setPossibleAnimals(ArrayList<WildlifeToken> possibleAnimals) {
        this.possibleAnimals = possibleAnimals;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public Polygon getPolygon() {
        return polygon;
    }

    public void setPolygon(Polygon polygon) {
        this.polygon = polygon;
    }

    public Biome getHabitat1() {
        return habitat1;
    }

    public void setHabitat1(Biome habitat1) {
        this.habitat1 = habitat1;
    }

    public Biome getHabitat2() {
        return habitat2;
    }

    public void setHabitat2(Biome habitat2) {
        this.habitat2 = habitat2;
    }

    public TreeMap<Integer, Biome> getBiomes() {
        return biomes;
    }

    public void setBiomes(TreeMap<Integer, Biome> biomes) {
        this.biomes = biomes;
    }

    public void setNeighbors(ArrayList<HabitatTile> neighbors) {
        this.neighbors = neighbors;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public void setEdges(List<Edge> edges) {
        this.edges = edges;
    }

    public void setWildlifeToken(WildlifeToken w) {
        this.w = w;
    }

    //Connects a HabitatTile to another
    public void addEdge(HabitatTile h) {
        neighbors.add(h);
    }

    public ArrayList<HabitatTile> getNeighbors() {
        return neighbors;
    }

    public boolean canPlace(WildlifeToken w) {
        return possibleAnimals.contains(w);
    }

    public WildlifeToken getWildlifeToken() {
        return w;
    }

    public Biome getBiome(int side) {
        return biomes.get(side);
    }

    /*function rotateTileClockwiseFunction() {
	// find the currently chosen tile that is currently being placed, and store it's current rotation value into a variable
	var currentRotation = parseInt($('.mapTileContainer .tileContainer.lockedIn').attr('tilerotation'));
	// add 60 degrees to the current rotation since it's being rotated clockwise
	var newRotation = currentRotation + 60;
	// update the new value on the tilerotation attribute that corresponds with chosen tile
	$('.mapTileContainer .tileContainer.lockedIn').attr('tilerotation', newRotation);
	// update the new value in the css to animate the tile moving to the new correct rotation
	$('.mapTileContainer .tileContainer.lockedIn .habitatTile').css('transform', 'rotate(' + newRotation + 'deg)');
}

function rotateTileCounterClockwiseFunction() {
	// find the currently chosen tile that is currently being placed, and store it's current rotation value into a variable
	var currentRotation = parseInt($('.mapTileContainer .tileContainer.lockedIn').attr('tilerotation'));
	// minus 60 degrees to the current rotation since it's being rotated counter-clockwise
	var newRotation = currentRotation - 60;
	// update the new value on the tilerotation attribute that corresponds with chosen tile
	$('.mapTileContainer .tileContainer.lockedIn').attr('tilerotation', newRotation);
	// update the new value in the css to animate the tile moving to the new correct <rotation></rotation>
	$('.mapTileContainer .tileContainer.lockedIn .habitatTile').css('transform', 'rotate(' + newRotation + 'deg)');
}*/


}
