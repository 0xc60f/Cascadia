package org.example;

import java.awt.*;
import java.awt.image.BufferedImage;
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
    private Biome habitat1;
    private Biome habitat2;
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
        switch (h1 + h2){
            case "dd" -> IntStream.range(0, 6).forEach(i -> biomes.put(i, Biome.DESERT));
            case "dl" -> {
                biomes.put(0, Biome.DESERT);
                biomes.put(1, Biome.DESERT);
                biomes.put(2, Biome.LAKE);
                biomes.put(3, Biome.LAKE);
                biomes.put(4, Biome.LAKE);
                biomes.put(5, Biome.DESERT);
            }
            case "ds" -> {
                biomes.put(0, Biome.DESERT);
                biomes.put(1, Biome.DESERT);
                biomes.put(2, Biome.SWAMP);
                biomes.put(3, Biome.SWAMP);
                biomes.put(4, Biome.SWAMP);
                biomes.put(5, Biome.DESERT);
            }
            case "fd" -> {
                biomes.put(0, Biome.FOREST);
                biomes.put(1, Biome.FOREST);
                biomes.put(2, Biome.DESERT);
                biomes.put(3, Biome.DESERT);
                biomes.put(4, Biome.DESERT);
                biomes.put(5, Biome.FOREST);
            }
            case "ff" -> IntStream.range(0, 6).forEach(i -> biomes.put(i, Biome.FOREST));
            case "fl" -> {
                biomes.put(0, Biome.FOREST);
                biomes.put(1, Biome.FOREST);
                biomes.put(2, Biome.LAKE);
                biomes.put(3, Biome.LAKE);
                biomes.put(4, Biome.LAKE);
                biomes.put(5, Biome.FOREST);
            }
            case "fs" -> {
                biomes.put(0, Biome.FOREST);
                biomes.put(1, Biome.FOREST);
                biomes.put(2, Biome.SWAMP);
                biomes.put(3, Biome.SWAMP);
                biomes.put(4, Biome.SWAMP);
                biomes.put(5, Biome.FOREST);
            }
            case "ll" -> IntStream.range(0, 6).forEach(i -> biomes.put(i, Biome.LAKE));
            case "lm" -> {
                biomes.put(0, Biome.LAKE);
                biomes.put(1, Biome.LAKE);
                biomes.put(2, Biome.MOUNTAIN);
                biomes.put(3, Biome.MOUNTAIN);
                biomes.put(4, Biome.MOUNTAIN);
                biomes.put(5, Biome.LAKE);
            }
            case "md" -> {
                biomes.put(0, Biome.MOUNTAIN);
                biomes.put(1, Biome.MOUNTAIN);
                biomes.put(2, Biome.DESERT);
                biomes.put(3, Biome.DESERT);
                biomes.put(4, Biome.DESERT);
                biomes.put(5, Biome.MOUNTAIN);
            }
            case "mf" -> {
                biomes.put(0, Biome.MOUNTAIN);
                biomes.put(1, Biome.MOUNTAIN);
                biomes.put(2, Biome.FOREST);
                biomes.put(3, Biome.FOREST);
                biomes.put(4, Biome.FOREST);
                biomes.put(5, Biome.MOUNTAIN);
            }
            case "mm" -> IntStream.range(0, 6).forEach(i -> biomes.put(i, Biome.MOUNTAIN));
            case "ms" -> {
                biomes.put(0, Biome.MOUNTAIN);
                biomes.put(1, Biome.MOUNTAIN);
                biomes.put(2, Biome.SWAMP);
                biomes.put(3, Biome.SWAMP);
                biomes.put(4, Biome.SWAMP);
                biomes.put(5, Biome.MOUNTAIN);
            }
            case "sl" -> {
                biomes.put(0, Biome.SWAMP);
                biomes.put(1, Biome.SWAMP);
                biomes.put(2, Biome.LAKE);
                biomes.put(3, Biome.LAKE);
                biomes.put(4, Biome.LAKE);
                biomes.put(5, Biome.SWAMP);
            }
            case "ss" -> IntStream.range(0, 6).forEach(i -> biomes.put(i, Biome.SWAMP));
        }
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
    }
    //Instantiates the HabitatTile as the Node class of the Cascadia Graph
    // BufferedReader

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
