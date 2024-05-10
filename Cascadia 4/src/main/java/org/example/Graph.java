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
        ArrayList<HabitatTile> neighbourTiles = neighbourTiles(allPlacedTokens, tileID);
        ArrayList<HabitatTile> matchingTiles = new ArrayList<>();

        for (HabitatTile tile : neighbourTiles) {
            if (allPlacedTokens.get(tile) == type && !matchingTiles.contains(tile)) {
                matchingTiles.add(tile);
            }
        }

        return matchingTiles;
    }

    public static ArrayList<HabitatTile> neighbourTiles(HashMap<HabitatTile, WildlifeToken> allPlacedTokens, HabitatTile tileID) {
        ArrayList<HabitatTile> neighbourTiles = new ArrayList<>();
        for (HabitatTile tile : allPlacedTokens.keySet()) {
            if (tile.isNeighbour(tileID)) {
                neighbourTiles.add(tile);
            }
        }
        return neighbourTiles;
    }

    public static ArrayList<HabitatTile> forwardsAndBackwardsSalmonRun(HashMap<HabitatTile, WildlifeToken> allPlacedTokens, HabitatTile firstTile, List<HabitatTile> startingTiles) {
        ArrayList<HabitatTile> run = new ArrayList<>();
        run.add(firstTile);
        List<HabitatTile> queue = new ArrayList<>(startingTiles);
        while (!queue.isEmpty()) {
            HabitatTile currentTile = queue.remove(0);
            if (currentTile.equals(firstTile)) {
                continue;
            }
            List<HabitatTile> neighbours = Graph.getNeighbors(currentTile);
            for (HabitatTile neighbour : neighbours) {
                if (allPlacedTokens.get(neighbour) == WildlifeToken.SALMON && !run.contains(neighbour)) {
                    run.add(neighbour);
                    queue.add(neighbour);
                }
            }
        }
        Collections.reverse(run);
        return run;
    }

    public static ArrayList<HabitatTile> salmonTokensInRun(Map<HabitatTile, WildlifeToken> allPlacedTokens, HabitatTile startID, WildlifeToken thisWildlife) {
        ArrayList<HabitatTile> run = new ArrayList<>();
        run.add(startID);
        List<HabitatTile> queue = new ArrayList<>();
        queue.add(startID);
        while (!queue.isEmpty()) {
            HabitatTile currentTile = queue.remove(0);
            List<HabitatTile> neighbours = Graph.getNeighbors(currentTile);
            for (HabitatTile neighbour : neighbours) {
                if (allPlacedTokens.get(neighbour) == thisWildlife && !run.contains(neighbour)) {
                    run.add(neighbour);
                    queue.add(neighbour);
                }
            }
        }
        return run;
    }

    public static int getLargestConnectedComponentSize(HashMap<HabitatTile, WildlifeToken> allPlacedTokens, HabitatTile startTile, Biome targetBiome) {
        int size = 0;
        Set<HabitatTile> visited = new HashSet<>();

        Deque<HabitatTile> stack = new ArrayDeque<>();
        stack.push(startTile);

        while (!stack.isEmpty()) {
            HabitatTile currentTile = stack.pop();
            if (!visited.contains(currentTile)) {
                visited.add(currentTile);
                if (allPlacedTokens.get(currentTile).equals(targetBiome)) {
                    size++;
                }
                for (HabitatTile neighbor : Graph.getNeighbors(currentTile)) {
                    if (allPlacedTokens.containsKey(neighbor)) {
                        stack.push(neighbor);
                    }
                }
            }
        }
        return size;
    }
    public static HabitatTile getNeighborWithSideBiome(HabitatTile tile, int side, Biome biome) {
        for (HabitatTile neighbor : tile.getNeighbors()) {
            if (neighbor != null){
                if (neighbor.getBiome(side) == biome) {
                    return neighbor;
                }
            }
        }
        return null;
    }

    public static int getOppositeSide(int side) {
        return (side + 3) % 6;
    }


}
