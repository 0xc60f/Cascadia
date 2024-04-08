package org.example;

import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.*;

public class Player implements Comparable<Player>{
    private int pNum;
    private ArrayList<HabitatTile> initialThree;
    private int numNatureTokens;
    private Boolean natureTokenUsed;
    private HashMap<String, Integer> numBiomes;
    private HashMap<HabitatTile, WildlifeToken> playerTiles;


    public Player(int p){
        pNum = p;
        numBiomes = new HashMap<String, Integer>();
        natureTokenUsed = false ;
        for(int i = 0; i < 3; i++){
            playerTiles.put(initialThree.get(i), initialThree.get(i).getWildlifeToken());
        }
    }

    public HashMap<HabitatTile, WildlifeToken> getPlayerTiles(){
        return playerTiles;
    }
    //finish later
    public void calcNumBiomes(){

    }
    public HashMap<String, Integer> getNumBiomes(){

        return numBiomes;
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

    public void addTile(HabitatTile h){

        return;
    }
    /*public int compareTo(Player p, Player p2){
        return Integer.compare(p.numNatureTokens, p2.numNatureTokens);
    }
    */


    @Override
    public int compareTo(Player otherPlayer) {
        // Compare based on victory points
        int result = Integer.compare(this.numNatureTokens, otherPlayer.numNatureTokens);

        if (result == 0) {
            // If tied on victory points, use additional tiebreakers
            //result = compareTiebreakers(otherPlayer);

        }

        return result;
    }

    /*private int compareTo(Player otherPlayer) {
        // Implement additional tiebreakers as needed
        // Example: Compare based on biome score, wildlifeToken score, and natureToken amount

        int result = Integer.compare(this.bScore, otherPlayer.bScore);

        if (result == 0) {
            result = Integer.compare(this.wScore, otherPlayer.wScore);
        }

        if (result == 0) {
            result = Integer.compare(this.nScore, otherPlayer.nScore);
        }


        return result;
    }
    public int getBiomeScore() {return BiomeScore;}
    public int getwildlifeTokenScore() {return wildlifeTokenScore;}
    public int getnatureTokenAmt() {return natureTokenAmt;}
    public int totalScore(){ int total = BiomeScore + wildlifeTokenScore + natureTokenAmt; return total;}

    public void clearEffects(){
        effects.clear();
    }

    */
}
