package org.example;

public class Edge {
    private int weight;
    private HabitatTile a, b;

    public Edge(HabitatTile a, HabitatTile b, int weight) {
        this.a = a;
        this.b = b;
        this.weight = weight;
        a.addEdge(b);
        b.addEdge(a);
    }

    public Edge(HabitatTile a, HabitatTile b, boolean unidirectional) {
        this.a = a;
        this.b = b;
        this.weight = 1;
        if (!unidirectional) {
            a.addEdge(b);
            b.addEdge(a);
        } else {
            a.addEdge(b);
        }
    }

    public HabitatTile getA() {
        return a;
    }

    public HabitatTile getB() {
        return b;
    }

    public int getWeight() {
        return weight;
    }

    public boolean contains(HabitatTile node) {
        return node == a || node == b;
    }
}
