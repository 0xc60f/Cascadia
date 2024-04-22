package org.example;

import java.util.*;

public class Player implements Comparable<Player> {
    private int pNum;
    private ArrayList<HabitatTile> initialThree;
    private int numNatureTokens;
    private String winner;
    private Boolean natureTokenUsed;
    private HashMap<String, Integer> numBiomes;
    private HashMap<HabitatTile, WildlifeToken> playerTiles;
    private int total;
    private int totalScore;
    private ArrayList<Integer> playerHabitatScores;


    public Player(int p) {
        pNum = p;
        numBiomes = new HashMap<String, Integer>();
        numNatureTokens = 0; // Initialize to zero, assuming start of game, nature tokens need to be added.
        natureTokenUsed = (Boolean) false;
        playerTiles = new HashMap<>();
        for (int i = 0; i < 3; i++) {
            playerTiles.put(initialThree.get(i), initialThree.get(i).getWildlifeToken());
        }
        totalScore = 0;
    }

    public HashMap<HabitatTile, WildlifeToken> getPlayerTiles() {
        return playerTiles;
    }

    //finish later
    public void calcNumBiomes() {
        return;
    }

    public HashMap<String, Integer> getNumBiomes() {
        return numBiomes;
    }

    public void addTile(HabitatTile h) {
        return;
    }

    public boolean natureTokenUsed() {
        natureTokenUsed = (Boolean) !natureTokenUsed;
        return natureTokenUsed;
    }

    public int numNatureTokens() {
        //if statement when a wildlife token is placed on a key stone (numNatureTokens ++;)
        if (natureTokenUsed()) {
            numNatureTokens--;
        }
        return numNatureTokens;
    }

    @Override
    public int compareTo(Player otherPlayer) {
        // Compare based on total score
        //int result = Integer.compare(this.numNatureTokens, otherPlayer.numNatureTokens);
        //int result = compareTiebreakers(otherPlayer, player2);
        int result = Integer.compare(this.totalScore, otherPlayer.totalScore);

        if (result == 0) {
            // If tied on total points, use natureTokens as tiebreakers
            result = Integer.compare(this.numNatureTokens, otherPlayer.numNatureTokens);
        }
        return result;
    }

    public int compareTiebreakers(Player otherPlayer, Player player2) { //check for all three player
        // Implement additional tiebreakers as needed
        // Example: Compare based on biome score, wildlifeToken score, and natureToken amount

        int t = this.getBiomeScore();
        int o = otherPlayer.getBiomeScore();
        int p = player2.getBiomeScore();

        int addpt = 0;

        if (p == o && o == t) {
            // All players have the same biome score, each gets 1 point
            addpt = 1;
            t += addpt;
            o += addpt;
            p += addpt;
        } else if (t > o && o > p) { // Player 1 has the highest biome score
            addpt = 3;
            t += addpt;
            addpt = 1;
            o += addpt;
        } else if (o > t && t > p) { // Other player has the highest biome score
            addpt = 3;
            o += addpt;
            addpt = 1;
            t += addpt;
        } else if (p > t && t > o) { // Player 2 has the highest biome score
            addpt = 3;
            p += addpt;
            addpt = 1;
            t += addpt;
            // No points for otherPlayer (o)
        } else if (t == o && t > p) { // Player 1 and otherPlayer tie for highest biome score
            addpt = 2;
            t += addpt;
            o += addpt;
            // No points for player2 (p)
        } else if (t == p && t > o) { // Player 1 and player2 tie for highest biome score
            addpt = 2;
            t += addpt;
            p += addpt;
            // No points for otherPlayer (o)
        } else if (o == p && o > t) { // Other player and player2 tie for highest biome score
            addpt = 2;
            o += addpt;
            p += addpt;
            // No points for player 1 (t)
        }
        total += t;

        return total;
    }

    /*public int getBiomeScore() {
        int BS =0;
        return BS;
    }*/
    // Method to calculate the biome score using the stored ScoringCharts instance
    public int getBiomeScore() {
        int biomeScore = 0;
        return biomeScore;
    }

    public int getWildlifeTokenScore() {
        ScoringCharts scoringCharts = new ScoringCharts();

        int wildlifeTokenScore = 0;

        // Calculate scores for each wildlife token type
        scoringCharts.calculateBearTokenScoring(pNum);
        scoringCharts.calculateFoxTokenScoring(this);
        scoringCharts.calculateHawkTokenScoring(this);
        scoringCharts.calculateSalmonTokenScoring(this);

        // Retrieve scores from ScoringCharts and sum them up
        for (Integer score : scoringCharts.scoringVals) {
            wildlifeTokenScore += score;
        }

        return wildlifeTokenScore;
    }

    public int totalScore(Player p) {
        total += numNatureTokens + p.getWildlifeTokenScore();
        return total;
    }
/*
   //public int getnatureTokenAmt() {return natureTokenAmt;}
    public void clearEffects(){
        effects.clear();
    }

    public int totalScore(){
        return getTotalScore();
    }

    */
}
