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
    private ArrayList<Integer> playerHabitatScores;
    public ArrayList<String> Biomes;
    public int lakeScore = 0;
    public int mountainScore = 0;
    public int desertScore = 0;
    public int swampScore = 0;
    public int forestScore = 0;




    public Player(int p){
        pNum = p;
        numBiomes = new HashMap<String, Integer>();
        numNatureTokens = 0; // Initialize to zero, assuming start of game, nature tokens need to be added.
        natureTokenUsed = (Boolean) false;
        playerTiles = new HashMap<>();
        for(int i = 0; i < 3; i++){
            playerTiles.put(initialThree.get(i), initialThree.get(i).getWildlifeToken());
        }
        totalScore = 0;
        List<Biome> biomes = new ArrayList<>();
        biomes.addAll(Arrays.asList(Biome.values()));
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
        natureTokenUsed = (Boolean) !natureTokenUsed;
        if(natureTokenUsed){
            numNatureTokens--;
        }
        return natureTokenUsed;
    }
    /*public int numNatureTokens() {
        //if statement when a wildlife token is placed on a key stone (numNatureTokens ++;)
        if(natureTokenUsed()){
            numNatureTokens--;
        }
        return numNatureTokens;
    }*/

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

    public int getBiomeScore(Player player, Biome specificBiome) {
        int biomeScore = 0;

        for (HabitatTile tile : playerTiles.keySet()) {
            TreeMap<Integer, Biome> biomes = tile.getBiomes();
            if (biomes.containsValue(specificBiome)) {
                for (Map.Entry<Integer, Biome> entry : biomes.entrySet()) {
                    if (entry.getValue() == specificBiome) {
                        int side = entry.getKey();
                        HabitatTile neighbor = Graph.getNeighborWithSideBiome(tile, side, specificBiome);
                        if (neighbor != null) {
                            int neighborSide = Graph.getOppositeSide(side);
                            Biome neighborBiome = neighbor.getBiome(neighborSide);
                            if (specificBiome == neighborBiome) {
                                biomeScore++;
                            }
                        }
                    }
                }
            }
        }

        return biomeScore;
    }

    public int compareBiomeScores(Player p1, Player p2, Player p3) { //check for all three player

        int t = 0;
        int o = 0;
        int p = 0;

        for(int i = 0; i<5; i++ ){
            Biome biome = Biome.valueOf(Biomes.get(i));
            t += getBiomeScore(p1, biome);
            o += getBiomeScore(p2, biome);
            p += getBiomeScore(p3, biome);

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

            if (i == 0){
                lakeScore += t;
            } else if(i == 1){
                mountainScore += t;
            } else if (i == 2){
                desertScore += t;
            }else if (i == 3){
                swampScore += t;
            }else if (i == 4){
                forestScore += t;
            }

        }
        total +=t;
        return t;
    }

    // Method to calculate the biome score using the stored ScoringCharts instance
    public int getBiomeScore() {
        ScoringCharts scoringCharts = new ScoringCharts();

        int biomeScore = scoringCharts.scoreHabitats(playerTiles);

        return biomeScore;
        // fix for each biome, the method in scoringCharts only gives total

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

    public int getBonusPoints(Player p){

        int tb = lakeScore + mountainScore + desertScore + swampScore + forestScore;
        return tb;
    }



}
/*public int scoreSpecificBiome(HashMap<HabitatTile, WildlifeToken> allPlacedTokens, Biome specificBiome) {
        int score = 0;

        for (HabitatTile tile : allPlacedTokens.keySet()) {
            // Check if the tile belongs to the specific biome
            TreeMap<Integer, Biome> biomes = tile.getBiomes();
            if (biomes.containsValue(specificBiome)) {
                for (Map.Entry<Integer, Biome> entry : biomes.entrySet()) {
                    Biome biome = entry.getValue();
                    int side = entry.getKey();
                    HabitatTile neighbor = Graph.getNeighborWithSideBiome(tile, side, biome);
                    if (neighbor != null) {
                        int neighborSide = Graph.getOppositeSide(side);
                        Biome neighborBiome = neighbor.getBiome(neighborSide);
                        // Check if the neighbor tile also belongs to the same specific biome
                        if (biome == neighborBiome && biome == specificBiome) {
                            score++;
                        }
                    }
                }
            }
        }

        return score;
    }

 */
