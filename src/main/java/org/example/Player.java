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
    public Player(int p){
        pNum = p;
        numBiomes = new HashMap<String, Integer>();
    }
    //finish later
    public void calcNumBiomes(){

    }
    public HashMap<String, Integer> getNumBiomes(){
        return numBiomes;
    }

    public boolean natureTokenUsed(){
        return natureTokenUsed;
    }
    public int numNatureTokens() {
        return numNatureTokens;
    }



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

    /*private int compareTiebreakers(Player otherPlayer) {
        // Implement additional tiebreakers as needed
        // Example: Compare based on research amount, marble amount, and file amount

        int result = Integer.compare(this.researchAmt, otherPlayer.researchAmt);

        if (result == 0) {
            result = Integer.compare(this.marbleAmt, otherPlayer.marbleAmt);
        }

        if (result == 0) {
            result = Integer.compare(this.fileAmt, otherPlayer.fileAmt);
        }

        return result;
    }
    public int getFileAmt() {return fileAmt;}
    public int getResearchAmt() {return researchAmt;}
    public int getMarbleAmt() {return marbleAmt;}

    public void clearEffects(){
        effects.clear();
    }

    */
}
