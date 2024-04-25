package org.example;

import java.io.*;
import java.util.*;
import java.util.stream.IntStream;

public class Game {
    //holds the habitatTile and wildlifeToken that are currently displayed in relation to each other.
    private ArrayList<HabitatTile> displayedTiles;
    private ArrayList<WildlifeToken> displayedWildlife;
    private ArrayList<WildlifeToken> possibleWildlife;
    private ArrayList<HabitatTile> possibleHabitatTiles;
    private Boolean shuffleTokens = false;

    private TreeSet<WildlifeToken> check;

    private int numTurns;


    private String data;

    //make this the construcotr for the actual game so the 4 displayed and make another one for the player class. Add to graph as well
    public Game() {
        check = new TreeSet<>();
        numTurns = 1;
        displayedTiles = new ArrayList<>();
        possibleWildlife = new ArrayList<>();
        possibleHabitatTiles = new ArrayList<>();
        displayedWildlife = new ArrayList<>();
        displayedTiles = new ArrayList<>();
        try {
            InputStream is = Game.class.getResourceAsStream("/MainTiles.txt");
            assert is != null;
            Scanner reader = new Scanner(is);
            while (reader.hasNextLine()) {
                data = reader.nextLine();
                possibleHabitatTiles.add(new HabitatTile(data.substring(0, 1), data.substring(1, 2), Integer.parseInt(data.substring(2, 3)), Integer.parseInt(data.substring(3, 4)), Integer.parseInt(data.substring(4, 5))));
            }
            is.close();
            reader.close();
        } catch (NullPointerException | IOException e) {
            throw new RuntimeException(e);
        }

        IntStream.range(0, 4).forEach(i -> displayedTiles.add(possibleHabitatTiles.remove((int) (Math.random() * possibleHabitatTiles.size()))));
        IntStream.range(0, 13).forEach(i -> Collections.addAll(possibleWildlife, WildlifeToken.values()));
        IntStream.range(0, 4).forEach(i -> displayedWildlife.add(possibleWildlife.remove((int) (Math.random() * possibleWildlife.size()))));


    }

    public ArrayList<HabitatTile> getDisplayedTiles() {
        return displayedTiles;
    }

    public void setDisplayedTiles(ArrayList<HabitatTile> displayedTiles) {
        this.displayedTiles = displayedTiles;
    }

    public ArrayList<WildlifeToken> getDisplayedWildlife() {
        return displayedWildlife;
    }

    public void setDisplayedWildlife(ArrayList<WildlifeToken> displayedWildlife) {
        this.displayedWildlife = displayedWildlife;
    }

    public ArrayList<WildlifeToken> getPossibleWildlife() {
        return possibleWildlife;
    }

    public void setPossibleWildlife(ArrayList<WildlifeToken> possibleWildlife) {
        this.possibleWildlife = possibleWildlife;
    }

    public ArrayList<HabitatTile> getPossibleHabitatTiles() {
        return possibleHabitatTiles;
    }

    public void setPossibleHabitatTiles(ArrayList<HabitatTile> possibleHabitatTiles) {
        this.possibleHabitatTiles = possibleHabitatTiles;
    }

    //Call this method when repainting
    public void addTile() {
        displayedTiles.add(possibleHabitatTiles.remove((int) (Math.random() * possibleHabitatTiles.size())));
    }

    public boolean addWildlife(HabitatTile h, WildlifeToken wild) {
        if (h.canPlace(wild)) {
            h.w = wild;
            return true;
        }
        return false;
    }
    //true means there are at least 3
    public boolean checkTokens(){

        for(int i = 0; i < 4; i++){
            check.add(displayedTiles.get(i).getWildlifeToken());
        }
        if(check.size() <=2){
            return true;
        }
        check.clear();
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
