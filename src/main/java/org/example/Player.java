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
    }
    //finish later
    public void calcNumBiomes(){

    }
    public HashMap<String, Integer> getNumBiomes(){
        return numBiomes;
    }




}
