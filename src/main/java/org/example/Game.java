package org.example;

import java.io.*;
import java.util.*;

public class Game {
    //holds the habitatTile and wildlifeToken that are currently displayed in relation to each other.
    private ArrayList<HabitatTile> displayed;
    private ArrayList<WildlifeToken> possibleWildlife;
    private ArrayList<HabitatTile> possibleHabitatTiles;

    private int numTurns;


    private String data;

    //make this the construcotr for the actual game so the 4 displayed and make another one for the player class. Add to graph as well
    public Game() {
        displayed = new ArrayList<HabitatTile>();
        possibleWildlife = new ArrayList<WildlifeToken>();
        possibleHabitatTiles = new ArrayList<HabitatTile>();
        try {
            Scanner reader = new Scanner(new File("MainTiles.txt"));
            while (reader.hasNextLine()) {
                data = reader.nextLine();
                possibleHabitatTiles.add(new HabitatTile(data.substring(0, 1), data.substring(1, 2), Integer.parseInt(data.substring(2, 3)), Integer.parseInt(data.substring(3, 4)), Integer.parseInt(data.substring(4, 5))));
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        displayed = new ArrayList<>();
        for (int i = 4; i < 0; i++) {
            displayed.add(possibleHabitatTiles.remove((int) (Math.random() * possibleHabitatTiles.size())));
        }

    }

    //Call this method when repainting
    public void addTile() {
        displayed.add(possibleHabitatTiles.remove((int) (Math.random() * possibleHabitatTiles.size())));
    }

    public boolean addWildlife(HabitatTile h, WildlifeToken wild) {
        if (h.canPlace(wild)) {
            h.w = wild;
            return true;
        }
        return false;
    }

    public void cntTurns() {
        if (numTurns <= 19) {
            numTurns++;
        } else {
            endGame();
        }
    }

    //GOTTA FIX THIS TO MAKE IT COMPARE
    public void endGame() {
        ScoringCharts sc = new ScoringCharts();

    }


}
