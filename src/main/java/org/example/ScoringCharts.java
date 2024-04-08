package org.example;
import java.util.*;
public class ScoringCharts {
    //private Hashmap <String, Integer> wildLifescore;

    public int calcWildlifeScore(Player p){
        return 0;
    }
    public int calcTotalScore(Player p) {
        //return totalScore;
        return 0;
    }

    private HashMap<HabitatTile, WildlifeToken> allPlacedTokens;
    private Map<Integer, Integer> bearScoringValues;
    private int confirmedBearPairs;
    private ArrayList<HabitatTile> potentialBears;
    private ArrayList<HabitatTile> usedTokenIDs;

    public void BearScoring() {
        bearScoringValues = new HashMap<>();
        bearScoringValues.put(1, 4);
        bearScoringValues.put(2, 11);
        bearScoringValues.put(3, 19);
        bearScoringValues.put(4, 27);

        potentialBears = new ArrayList<HabitatTile>();
        usedTokenIDs = new ArrayList<>();
    }

    public void calculateBearTokenScoring(int player) {

        Player p = new Player(player);

        allPlacedTokens = p.getPlayerTiles(); // Initialize or assign the allPlacedTokens map

        confirmedBearPairs = 0;

        HashSet<HabitatTile> HabitatTiles = (HashSet<HabitatTile>) allPlacedTokens.keySet();

        for (HabitatTile tokenID : HabitatTiles) {
            if (allPlacedTokens.get(tokenID).equals(WildlifeToken.BEAR) && !usedTokenIDs.contains(tokenID)) {

                List<HabitatTile> neighboringTiles = Graph.getNeighbors(tokenID);

                for (HabitatTile neighborTile : neighboringTiles) {
                    if (allPlacedTokens.containsKey(neighborTile.getWildlifeToken())) {
                        // The neighboring tile exists and has a placed token on it
                        // Continue with the specified scoring process for this wildlife'
                        if (allPlacedTokens.get(neighborTile.getWildlifeToken()).equals(WildlifeToken.BEAR) && neighborTile.getWildlifeToken().equals(allPlacedTokens.get(tokenID))) {
                            potentialBears.add(neighborTile);
                        }
                    }
                }

                if (potentialBears.size() == 1) {
                    // Only one neighboring bear means it only has the pair and could qualify for scoring!
                    // Need to now make sure there's no bears touching the matched neighbor tile before locking it in for scoring

                    List<HabitatTile> potentialBearPairNeighbourTiles = Graph.getNeighbors(potentialBears.get(0));
                    for (HabitatTile potentialBearPairNeighbourTile : potentialBearPairNeighbourTiles) {
                        if (allPlacedTokens.containsKey(potentialBearPairNeighbourTile.getWildlifeToken())) {
                            // The neighboring tile exists and has a placed token on it!
                            // Continue with the specified scoring process for this wildlife'

                            if (allPlacedTokens.get(potentialBearPairNeighbourTile.getWildlifeToken()).equals(WildlifeToken.BEAR) && potentialBearPairNeighbourTile.getWildlifeToken().equals(allPlacedTokens.get(tokenID))) {
                                potentialBears.add(potentialBearPairNeighbourTile);
                            }
                        }
                    }
                    if (potentialBears.size() == 2) {
                        if (confirmedBearPairs <= 4) {
                            confirmedBearPairs++;
                        }
                    }
                }
                usedTokenIDs.addAll(potentialBears);
            }
        }

        if (confirmedBearPairs != 0) {
            //tokenScoring.bear.totalScore = bearScoringValues.get(confirmedBearPairs);
            //gotta make the token scoring method that will hold all scoring values
        }
    }

    // Assuming neighbourTileIDs is a method that returns a list of neighbouring tile IDs
    private List<String> neighbourTileIDs(String tokenID) {
        // Implement this method based on your requirements
        return new ArrayList<>();
    }

}
