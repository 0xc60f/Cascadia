package org.example;

import java.util.*;
import java.io.*;
import java.lang.*;


public abstract class Graph {
    private LinkedList<HabitatTile> hTiles;
    private LinkedList<Edge> edges;

    public abstract void addHabitatTile(HabitatTile node);
    public abstract void addEdge(Edge edge);
    public abstract void addEdge(HabitatTile a, HabitatTile b, boolean unidirectional);
    public abstract void removeHabitatTile(HabitatTile node);
    public abstract void removeEdge(Edge edge);
    public abstract boolean connected(HabitatTile a, HabitatTile b);
    public abstract boolean adjacent(HabitatTile a, HabitatTile b);
    public abstract boolean distance(HabitatTile a, HabitatTile b);

}
