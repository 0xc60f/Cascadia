package org.example;

import java.awt.image.BufferedImage;
import java.util.*;

public class HabitatTile {
    private WildlifeToken w = null;
    //Sides in relation to Biomes
    private ArrayList<WildlifeToken> possibleAnimals;
    private BufferedImage image;
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

    public HabitatTile(String h1, String h2, int animal1, int animal2, int animal3){
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

        switch(animal1){
            case 1 -> possibleAnimals.add(WildlifeToken.ELK);
            case 2 -> possibleAnimals.add(WildlifeToken.HAWK);
            case 3 -> possibleAnimals.add(WildlifeToken.BEAR);
            case 4 -> possibleAnimals.add(WildlifeToken.SALMON);
            case 5 -> possibleAnimals.add(WildlifeToken.FOX);
        }
        switch(animal2){
            case 1 -> possibleAnimals.add(WildlifeToken.ELK);
            case 2 -> possibleAnimals.add(WildlifeToken.HAWK);
            case 3 -> possibleAnimals.add(WildlifeToken.BEAR);
            case 4 -> possibleAnimals.add(WildlifeToken.SALMON);
            case 5 -> possibleAnimals.add(WildlifeToken.FOX);
        }
        switch(animal3){
            case 1 -> possibleAnimals.add(WildlifeToken.ELK);
            case 2 -> possibleAnimals.add(WildlifeToken.HAWK);
            case 3 -> possibleAnimals.add(WildlifeToken.BEAR);
            case 4 -> possibleAnimals.add(WildlifeToken.SALMON);
            case 5 -> possibleAnimals.add(WildlifeToken.FOX);
        }
        neighbors = new ArrayList<HabitatTile>();
    }
    //Instantiates the HabitatTile as the Node class of the Cascadia Graph
    public void setWildlifeToken(WildlifeToken w){
        this.w = w;
    }
    //Connects a HabitatTile to another
    public void addEdge(HabitatTile h){
        neighbors.add(h);
    }

    public ArrayList<HabitatTile> getNeighbors(){
        return neighbors;
    }
    public boolean canPlace(WildlifeToken w){
        return possibleAnimals.contains(w);
    }

    public WildlifeToken getWildlifeToken(){
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
