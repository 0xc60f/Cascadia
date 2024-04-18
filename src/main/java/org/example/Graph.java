package org.example;

import java.util.*;
import java.io.*;
import java.lang.*;


public abstract class Graph {
    private LinkedList<HabitatTile> hTiles;
    private LinkedList<Edge> edges;
    private ArrayList<HabitatTile> searchNeighbourTilesForWildlife, neighbourTiles, forwardsAndBackwardsSalmonRun, salmonTokensInRun;
    public Graph() {
        hTiles = new LinkedList<>();
        edges = new LinkedList<>();
    }
    public static List<HabitatTile> getNeighbors(HabitatTile tile) {
        return tile.getNeighbors();
    }

    public void addHabitatTile(HabitatTile node) {
        hTiles.add(node);
    }

    public void addEdge(Edge edge) {
        edges.add(edge);
        edge.getA().addEdge(edge.getB());
        edge.getB().addEdge(edge.getA());
    }

    public void addEdge(HabitatTile a, HabitatTile b, boolean unidirectional) {
        Edge edge = new Edge(a, b, unidirectional);
        addEdge(edge);
    }

    public void removeHabitatTile(HabitatTile node) {
        hTiles.remove(node);
    }


    public boolean connected(HabitatTile a, HabitatTile b) {
        for (Edge edge : edges) {
            if (edge.contains(a) && edge.contains(b)) {
                return true;
            }
        }
        return false;
    }

    public static boolean adjacent(HabitatTile a, HabitatTile b) {
        for (HabitatTile neighbor : a.getNeighbors()) {
            if (neighbor == b) {
                return true;
            }
        }
        return false;
    }

    public static ArrayList<HabitatTile> searchNeighbourTilesForWildlife(HashMap<HabitatTile, WildlifeToken> allPlacedTokens, HabitatTile tileID, WildlifeToken type) {
        return new ArrayList<>();
    }

    public static ArrayList<HabitatTile> neighbourTiles(HashMap<HabitatTile, WildlifeToken> allPlacedTokens, HabitatTile tileID) {
        // Implement this method based on  reqs
        return new ArrayList<>();
    }

    // Implement the forwardsAndBackwardsSalmonRun and salmonTokensInRun methods in the SalmonScoring class
    public static ArrayList<HabitatTile> forwardsAndBackwardsSalmonRun(HashMap<HabitatTile, WildlifeToken> allPlacedTokens, HabitatTile firstTile, List<HabitatTile> startingTiles) {
        return new ArrayList<>();
    }

    public static ArrayList<HabitatTile> salmonTokensInRun(Map<HabitatTile, WildlifeToken> allPlacedTokens, HabitatTile startID, WildlifeToken thisWildlife) {
        return new ArrayList<>();
    }


}
