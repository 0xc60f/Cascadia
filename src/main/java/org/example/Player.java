package org.example;

import java.util.*;

public class Player implements Comparable<Player>{
    private int pNum;
    private ArrayList<HabitatTile> initialThree;
    private int numNatureTokens;
    private String winner;
    private Boolean natureTokenUsed;
    private HashMap<String, Integer> numBiomes;
    private HashMap<HabitatTile, WildlifeToken> playerTiles;
    private int total;
    private int totalScore;


    public Player(int p){
        pNum = p;
        numBiomes = new HashMap<String, Integer>();
        numNatureTokens = 0; // Initialize to zero, assuming nature tokens need to be added.
        natureTokenUsed = false ;
        playerTiles = new HashMap<>();
        for(int i = 0; i < 3; i++){
            playerTiles.put(initialThree.get(i), initialThree.get(i).getWildlifeToken());
        }
        totalScore = 0;
    }

    public HashMap<HabitatTile, WildlifeToken> getPlayerTiles(){
        return playerTiles;
    }
    //finish later
    public void calcNumBiomes(){ return;}

    public HashMap<String, Integer> getNumBiomes(){
        return numBiomes;
    }

    public void addTile(HabitatTile h){
        return;
    }
    public boolean natureTokenUsed(){
        natureTokenUsed = !natureTokenUsed;
        return natureTokenUsed;
    }
    public int numNatureTokens() {
        //if statement when a wildlife token is placed on a key stone (numNatureTokens ++;)
        if(natureTokenUsed()){
            numNatureTokens--;
        }
        return numNatureTokens;
    }

    @Override
    public int compareTo(Player otherPlayer) {
        // Compare based on total score
        //int result = Integer.compare(this.numNatureTokens, otherPlayer.numNatureTokens);
        //int result = compareTiebreakers(otherPlayer);
        int result = Integer.compare(this.totalScore, otherPlayer.totalScore);

        if (result == 0) {
            // If tied on total points, use natureTokens as tiebreakers
            result = Integer.compare(this.numNatureTokens, otherPlayer.numNatureTokens);
        }
        return result;
    }

    public int compareTiebreakers(Player otherPlayer) {
        // Implement additional tiebreakers as needed
        // Example: Compare based on biome score, wildlifeToken score, and natureToken amount

        int addpt =0;
        int result = Integer.compare(0, 1);//this.calcNumBiomes(), otherPlayer.calcNumBiomes());

        int t = 0;//getBiomeScore();
        int o = getBiomeScore(otherPlayer);

        if (result == 0) {
            addpt = 2;
            t += addpt;
            o += addpt;
        }else if(result > 0){ //check which one will get 3 points
            addpt = 3;
            t += addpt;
            addpt = 1;
            o += addpt;
        }else if(result < 0){
            addpt = 3;
            o += addpt;
            addpt = 1;
            t += addpt;
        }

        int Wresult = Integer.compare(t, o);

        return Wresult;
    }

   public int getBiomeScore(Player p) {
        int BS =0;
        return BS;
    }
    public int getwildlifeTokenScore(Player p) {
        //calculateBearTokenScoring(p) + calculateFoxTokenScoring(p) + calculateHawkTokenScoring(p);
        int WLTS = 0;
        return WLTS;}
   public int totalScore(Player p){
        total = getBiomeScore(p) + getwildlifeTokenScore(p) + numNatureTokens;
        int result = compareTiebreakers(p);
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
