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

    public HabitatTile(Biome h1, Biome h2, ArrayList<WildlifeToken> availableAnimals){
        habitat1  = h1;
        habitat2 = h2;
        possibleAnimals = availableAnimals;
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
