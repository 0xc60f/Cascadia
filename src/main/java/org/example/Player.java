package org.example;

import java.util.*;

public class Player {
    private int pNum;
    private ArrayList<HabitatTile> initialThree;
    private int numNatureTokens;
    private Boolean natureTokenUsed;
    private HashMap<String, Integer> numBiomes;

    public Player(int p){
        pNum = p;
        numBiomes = new HashMap<String, Integer>();
        natureTokenUsed = false ;
    }
    //finish later
    public void calcNumBiomes(){

    }
    public HashMap<String, Integer> getNumBiomes(){

        return numBiomes;
    }

    public boolean natureTokenUsed(){
        if(!natureTokenUsed){
            natureTokenUsed = true;
        }else{
            natureTokenUsed = false;
        }
        return natureTokenUsed;
    }
    public int numNatureTokens() {
        //if statement when a wildlife token is placed on a key stone (numNatureTokens ++;)
        if(natureTokenUsed() == true){
            numNatureTokens--;
        }
        return numNatureTokens;
    }

    public void addTile(HabitatTile h){

        return;
    }
    public int compareTo(Player p, Player p2){
        return Integer.compare(p.numNatureTokens, p2.numNatureTokens);
    }





}
