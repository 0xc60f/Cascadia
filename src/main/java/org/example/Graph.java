package org.example;

import java.util.*;
import java.io.*;
import java.lang.*;


public abstract class Graph {
    private LinkedList<HabitatTile> hTiles;
    private LinkedList<Edge> edges;

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

    public boolean adjacent(HabitatTile a, HabitatTile b) {
        for (HabitatTile neighbor : a.getNeighbors()) {
            if (neighbor == b) {
                return true;
            }
        }
        return false;
    }

}
