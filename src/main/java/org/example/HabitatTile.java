package org.example;

import java.util.*;

public class HabitatTile {
    private WildlifeToken w = null;
    //Sides in relation to Biomes
    private TreeMap<Integer, Biome> biomes;
    //Constructor for the Biomes of the HabitatTile
    private ArrayList<HabitatTile> neighbors;
    private int value;
    private List<Edge> edges;
    public HabitatTile(String h1, String h2){

    }
    //Instantiates the HabitatTile as the Node class of the Cascadia Graph
    public HabitatTile(WildlifeToken w, int index){
        this.w = w;
        neighbors = new ArrayList<HabitatTile>();
    }
    //Connects a HabitatTile to another
    public void addEdge(HabitatTile h){
        neighbors.add(h);
    }

    public ArrayList<HabitatTile> getNeighbors(){
        return neighbors;
    }
}
