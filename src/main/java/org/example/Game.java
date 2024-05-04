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
    private ArrayList<Player> players;
    private Player currentPlayer;
    private Boolean shuffleTokens = false;

    private TreeSet<WildlifeToken> check;

    private int numTurns;


    private String data;

    //make this the construcotr for the actual game so the 4 displayed and make another one for the player class. Add to graph as well
    public Game() {
        players = new ArrayList<>();
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
        Collections.shuffle(possibleWildlife);
        IntStream.range(0, 4).forEach(i -> displayedWildlife.add(possibleWildlife.remove((int) (Math.random() * possibleWildlife.size()))));

        ArrayList<ArrayList<HabitatTile>> groupedTiles = new ArrayList<>();
        try {
            InputStream is = Game.class.getResourceAsStream("/StarterTiles.txt");
            assert is != null;
            Scanner reader = new Scanner(is);
            ArrayList<HabitatTile> tilesGroup = new ArrayList<>();
            while (reader.hasNextLine()) {
                String data = reader.nextLine();
                tilesGroup.add(new HabitatTile(data.substring(0, 1), data.substring(1, 2), Integer.parseInt(data.substring(2, 3)), Integer.parseInt(data.substring(3, 4)), Integer.parseInt(data.substring(4, 5))));
                if (tilesGroup.size() == 3) {
                    groupedTiles.add(tilesGroup);
                    tilesGroup = new ArrayList<>();
                }
            }
            reader.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        IntStream.range(0, 3).forEach(i -> players.add(new Player(i, groupedTiles.get((int) (Math.random() * groupedTiles.size())))));
        currentPlayer = players.getFirst();

    }


    public void swapWildLifeToken(int i) {
        possibleWildlife.add(displayedWildlife.remove(i));
        displayedWildlife.add(possibleWildlife.remove((int) (Math.random() * possibleWildlife.size())));
    }
    public void addNewTile(int i){
        displayedTiles.add(i, possibleHabitatTiles.remove((int) (Math.random() * possibleHabitatTiles.size())));
    }
    public void addNewWildlifeToken(int i){
        displayedWildlife.add(i, possibleWildlife.remove((int) (Math.random() * possibleWildlife.size())));
    }

    public void shuffleDisplayedWildLife(WildlifeToken x) {
        if (x == null) {
            displayedWildlife = new ArrayList<WildlifeToken>();
            IntStream.range(0, 4).forEach(i -> displayedWildlife.add(possibleWildlife.remove((int) (Math.random() * possibleWildlife.size()))));
            IntStream.range(0, 4).forEach(i -> possibleWildlife.add(WildlifeToken.values()[(int) (Math.random() * WildlifeToken.values().length)]));
        } else {
            displayedWildlife = new ArrayList<WildlifeToken>();
            for (int i = 0; i < displayedWildlife.size(); i++) {
                if (displayedWildlife.get(i).equals(x)) {
                    possibleWildlife.add(displayedWildlife.remove(i));
                    displayedWildlife.add(possibleWildlife.remove((int) (Math.random() * possibleWildlife.size())));
                }
            }
        }
    }

    public void set3OfTheSame() {
        displayedWildlife = new ArrayList<WildlifeToken>();
        IntStream.range(0, 3).forEach(i -> displayedWildlife.add(WildlifeToken.SALMON));
        displayedWildlife.add(WildlifeToken.values()[(int) (Math.random() * WildlifeToken.values().length)]);

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
    public void addTilePlayer(){

    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
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

    public void setNextPlayer(){
        currentPlayer = players.indexOf(currentPlayer) == players.size() - 1 ? players.getFirst() : players.get(players.indexOf(currentPlayer) + 1);
    }
    //need to add more to this


    public void updateTurn(Player p){
        cntTurns();
    }


}
