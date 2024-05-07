package org.example;


import java.util.*;
import java.util.stream.IntStream;

public class Player implements Comparable<Player>{
    private int pNum;
    private ArrayList<HabitatTile> initialThree;
    private int numNatureTokens;
    private String winner;
    private Boolean natureTokenUsed;
    private int numTurns;
    private HashMap<String, Integer> numBiomes;
    private HashMap<HabitatTile, WildlifeToken> playerTiles;
    private int total;
    private int totalScore;
    private ArrayList<Integer> playerHabitatScores;
    public ArrayList<String> Biomes;
    public int lakeBonusScore = 0;
    public int mountainBonusScore = 0;
    public int desertBonusScore = 0;
    public int swampBonusScore = 0;
    public int forestBonusScore = 0;


    public Player (int p, ArrayList<HabitatTile> firstThree){
        pNum = p;
        initialThree = firstThree;
        numBiomes = new HashMap<String, Integer>();
        numNatureTokens = 0; // Initialize to zero, assuming start of game, nature tokens need to be added.
        natureTokenUsed = false;
        playerTiles = new HashMap<>();
        IntStream.range(0, 3).forEach(i -> playerTiles.put(initialThree.get(i), initialThree.get(i).getWildlifeToken()));
        totalScore = 0;
        firstThree.getFirst().setNeighbors(new ArrayList<>(Arrays.asList(null, firstThree.get(1), firstThree.get(2), null, null, null)));
        firstThree.get(1).setNeighbors(new ArrayList<>(Arrays.asList(null, null, null, firstThree.getLast(), firstThree.getFirst(), null)));
        firstThree.getLast().setNeighbors(new ArrayList<>(Arrays.asList(firstThree.get(1), null, null, null, null, firstThree.getFirst())));
        //biomes

    }
    public boolean isPossibleToPlace(WildlifeToken w){
        for (HabitatTile h : playerTiles.keySet()){
            if(h.getWildlifeToken() == null && h.canPlace(w)){
                return true;
            }
        }
        return false;
    }

    public HashMap<HabitatTile, WildlifeToken> getPlayerTiles(){
        return playerTiles;
    }

    public int getpNum() {
        return pNum;
    }

    public void setpNum(int pNum) {
        this.pNum = pNum;
    }

    public ArrayList<HabitatTile> getInitialThree() {
        return initialThree;
    }

    public void setInitialThree(ArrayList<HabitatTile> initialThree) {
        this.initialThree = initialThree;
    }

    public int getNumNatureTokens() {
        return numNatureTokens;
    }

    public void setNumNatureTokens(int numNatureTokens) {
        this.numNatureTokens = numNatureTokens;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    public Boolean getNatureTokenUsed() {
        return natureTokenUsed;
    }

    public void setNatureTokenUsed(Boolean natureTokenUsed) {
        this.natureTokenUsed = natureTokenUsed;
    }

    public void setNumBiomes(HashMap<String, Integer> numBiomes) {
        this.numBiomes = numBiomes;
    }

    public void setPlayerTiles(HashMap<HabitatTile, WildlifeToken> playerTiles) {
        this.playerTiles = playerTiles;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    public ArrayList<Integer> getPlayerHabitatScores() {
        return playerHabitatScores;
    }

    public void setPlayerHabitatScores(ArrayList<Integer> playerHabitatScores) {
        this.playerHabitatScores = playerHabitatScores;
    }

    public ArrayList<String> getBiomes() {
        return Biomes;
    }

    public void setBiomes(ArrayList<String> biomes) {
        Biomes = biomes;
    }

    public int getLakeBonusScore() {
        return lakeBonusScore;
    }

    public void setLakeBonusScore(int lakeBonusScore) {
        this.lakeBonusScore = lakeBonusScore;
    }

    public int getMountainBonusScore() {
        return mountainBonusScore;
    }

    public void setMountainBonusScore(int mountainBonusScore) {
        this.mountainBonusScore = mountainBonusScore;
    }

    public int getDesertBonusScore() {
        return desertBonusScore;
    }

    public void setDesertBonusScore(int desertBonusScore) {
        this.desertBonusScore = desertBonusScore;
    }

    public int getSwampBonusScore() {
        return swampBonusScore;
    }

    public void setSwampBonusScore(int swampBonusScore) {
        this.swampBonusScore = swampBonusScore;
    }

    public int getForestBonusScore() {
        return forestBonusScore;
    }

    public int getNumTurns() {
        return numTurns;
    }

    public void setNumTurns(int numTurns) {
        this.numTurns = numTurns;
    }

    public void setForestBonusScore(int forestBonusScore) {
        this.forestBonusScore = forestBonusScore;
    }

    //finish later
    public void calcNumBiomes(){ return;}

    public HashMap<String, Integer> getNumBiomes(){
        return numBiomes;
    }

    public void addTile(HabitatTile h){
        playerTiles.put(h, h.getWildlifeToken());
    }

    public boolean natureTokenUsed(){
        natureTokenUsed = (Boolean) !natureTokenUsed;
        if(natureTokenUsed){
            numNatureTokens--;
        }
        return natureTokenUsed;
    }
    public int numNatureTokens() {
        return numNatureTokens;
    }

    public void addNatureTokens() {
        numNatureTokens++;
    }

    public void subtractNatureTokens() {
        numNatureTokens--;
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

    private int getBScore(Player player, Biome specificBiome) {
        int biomeScore = 0;
        ScoringCharts sc = new ScoringCharts();
        System.out.println("getBScore CALLED");
        HashMap<HabitatTile, WildlifeToken> allPlacedTokens = new HashMap<>();
        for (HabitatTile tile : player.getPlayerTiles().keySet()) {
            allPlacedTokens.put(tile, player.getPlayerTiles().get(tile));
        }
        biomeScore = sc.scoreHabitats(allPlacedTokens, specificBiome);
        return biomeScore;
    }

    public int getLakeScore(Player p){
        System.out.println("getLakeScore called");
        int ls = getBScore(p,Biome.LAKE);
        return ls;
    }
    public int getMountainScore(Player p){
        int ms = getBScore(p,Biome.MOUNTAIN);
        return ms;
    }
    public int getDesertScore(Player p){
        int ds = getBScore(p,Biome.DESERT);
        return ds;
    }
    public int getSwampScore(Player p){
        int ss = getBScore(p,Biome.SWAMP);
        return ss;
    }
    public int getForestScore(Player p){
        int fs = getBScore(p,Biome.FOREST);
        return fs;
    }

    public int compareBiomeScores(Player p1, Player p2, Player p3) { //check for all three player

        int t = 0;
        int o = 0;
        int p = 0;

        for(int i = 0; i<5; i++ ){
            Biome biome = Biome.valueOf(Biomes.get(i));
            t += getBScore(p1, biome);
            o += getBScore(p2, biome);
            p += getBScore(p3, biome);

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
            total+= t;

            switch (i) {
                case 0 -> lakeBonusScore += t;
                case 1 -> mountainBonusScore += t;
                case 2 -> desertBonusScore += t;
                case 3 -> swampBonusScore += t;
                case 4 -> forestBonusScore += t;
            }

        }
        total +=t;
        return t;
    }

    // Method to calculate the biome score using the stored ScoringCharts instance

    //FIX THIS FOR DEBUGGING
    public int getBiomeScore() {
        ScoringCharts scoringCharts = new ScoringCharts();

        int biomeScore = 0;

        return biomeScore;
        // fix for each biome, the method in scoringCharts only gives total

    }

    public int getWildlifeTokenScore() {
        ScoringCharts scoringCharts = new ScoringCharts();

        int wildlifeTokenScore = 0;

        // Calculate scores for each wildlife token type
        scoringCharts.calculateBearTokenScoring(this);
        scoringCharts.calculateFoxTokenScoring(this);
        scoringCharts.calculateHawkTokenScoring(this);
        scoringCharts.calculateSalmonTokenScoring(this);


        // Retrieve scores from ScoringCharts and sum them up
        for (Integer score : scoringCharts.scoringVals) {
            wildlifeTokenScore += score;
        }

        return wildlifeTokenScore;
    }
    public int getBearTokenScore() {
        ScoringCharts scoringCharts = new ScoringCharts();
        scoringCharts.calculateBearTokenScoring(this); // Calculate bear token scoring for the player
        // Sum up the scores
        int bScore = 0;
        for (Integer bearscoring : scoringCharts.bearscoringVals) {
            bScore += bearscoring;
        }
        return bScore;
    }

    public int getFoxTokenScore() {
        ScoringCharts scoringCharts = new ScoringCharts();
        scoringCharts.calculateFoxTokenScoring(this); // Calculate fox token scoring for the player
        int fScore = 0;
        for (Integer foxscoring : scoringCharts.foxscoringVals) {
            if(foxscoring != null){
                 fScore += foxscoring;
            }
        }
        return fScore;
    }

    public int getHawkTokenScore() {
        ScoringCharts scoringCharts = new ScoringCharts();
        scoringCharts.calculateHawkTokenScoring(this); // Calculate hawk token scoring for the player
        return scoringCharts.hawkscoringVals.stream().filter(Objects::nonNull).mapToInt(hawkscoring -> hawkscoring).sum();
    }

    public int getSalmonTokenScore() {
        ScoringCharts scoringCharts = new ScoringCharts();
        scoringCharts.calculateSalmonTokenScoring(this); // Calculate salmon token scoring for the player
        int sScore = 0;
        for (Integer salmonscoring : scoringCharts.salmonscoringVals) {
            if(salmonscoring !=null) {
                sScore += salmonscoring;
            }
        }
        return sScore;
    }

    public int getELkTokenScore() {
        ScoringCharts scoringCharts = new ScoringCharts();
        scoringCharts.calculateElkTokenScoring(this); // Calculate elk token scoring for the player
        int eScore = 0;
        for (Integer elkscoring : scoringCharts.elkscoringvals) {
            eScore += elkscoring;
        }
        return eScore;
    }

    public int totalScore(Player p){
        total += numNatureTokens + p.getWildlifeTokenScore() + p.getBiomeScore();
        return total;
    }
    public String calcWinner(Player p1, Player p2, Player p3) {
        compareBiomeScores(p1, p2, p3);
        int t1 = totalScore(p1);
        int t2 = totalScore(p2);
        int t3 = totalScore(p3);

        if (t1 > t2 && t1 > t3) {
            return "Player 1";
        } else if (t2 > t1 && t2 > t3) {
            return "Player 2";
        } else if (t3 > t1 && t3 > t2) {
            return "Player 3";
        }

        return "It's a tie!";
    }

    public int getTotalBonusPoints(Player p){
        int tb = lakeBonusScore + mountainBonusScore + desertBonusScore + swampBonusScore + forestBonusScore;
        return tb;
    }



}
