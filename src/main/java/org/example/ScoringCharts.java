package org.example;
import java.util.*;
import java.util.stream.*;

public class ScoringCharts {
    private int totalScore;
    public int calcWildlifeScore(Player p){
        return 0;
    }
    public int calcTotalScore(Player p) {
        //return totalScore;
        return 0;
    }
    //SCORING ARRAYLIST GOES IN MOUNTAIN FOREST DESERT MARSH RIVER.

    private HashMap<HabitatTile, WildlifeToken> allPlacedTokens;
    private HashMap<Integer, Integer> bearScoringValues;
    private int confirmedBearPairs;
    private ArrayList<HabitatTile> potentialBears;
    private ArrayList<HabitatTile> usedTokenIDs;
    private HashMap<Integer, Integer> foxScoringValues;
    private Map<Biome, Integer> habitatMatches;
    public Player p1 = new Player(1);
    public Player p2 = new Player(2);
    public Player p3 = new Player(3);
    public Player p4 = new Player(4);
    public ArrayList<Integer> scoringVals;
    private int totalElkScore = 0;
    public ArrayList<Integer> bearscoringVals;
    public ArrayList<Integer> foxscoringVals;
    public ArrayList<Integer> hawkscoringVals;
    public ArrayList<Integer> salmonscoringVals;
    public ArrayList<Integer> elkscoringvals;

    public ScoringCharts() {
        habitatMatches = new HashMap<>();
        bearScoringValues = new HashMap<>();
        foxScoringValues = new HashMap<>();
        hawkScoringValues = new HashMap<>();
        salmonScoringValues = new HashMap<>();
        scoringVals = new ArrayList<>();
        bearscoringVals = new ArrayList<Integer>();
        foxscoringVals = new ArrayList<Integer>();
        hawkscoringVals = new ArrayList<Integer>();
        salmonscoringVals = new ArrayList<Integer>();
        elkscoringvals = new ArrayList<Integer>();
    }
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
                    if(neighborTile != null) {
                        if (allPlacedTokens.containsKey(neighborTile.getWildlifeToken())) {
                            // The neighboring tile exists and has a placed token on it
                            // Continue with the specified scoring process for this wildlife'
                            if (allPlacedTokens.get(neighborTile.getWildlifeToken()).equals(WildlifeToken.BEAR) && neighborTile.getWildlifeToken().equals(allPlacedTokens.get(tokenID))) {
                                potentialBears.add(neighborTile);
                            }
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
        usedTokenIDs.clear();

        if (confirmedBearPairs != 0) {
            scoringVals.add(bearScoringValues.get(confirmedBearPairs));
            bearscoringVals.add(bearScoringValues.get(confirmedBearPairs));

            //gotta make the token scoring method that will hold all scoring values
        }
    }
    public void CalculateFoxScoring() {
        foxScoringValues = new HashMap<>();
        foxScoringValues.put(1, 1);
        foxScoringValues.put(2, 2);
        foxScoringValues.put(3, 3);
        foxScoringValues.put(4, 4);
        foxScoringValues.put(5, 5);
    }

    public void calculateFoxTokenScoring(Player p) {

        allPlacedTokens = p.getPlayerTiles();
        HashSet<HabitatTile> possibleFoxTiles = (HashSet<HabitatTile>) allPlacedTokens.keySet();

        for (HabitatTile tokenID : possibleFoxTiles) {
            WildlifeToken token = allPlacedTokens.get(tokenID);
            if (token == WildlifeToken.FOX) {

                List<HabitatTile> neighbourTiles = Graph.getNeighbors(tokenID);

                ArrayList<WildlifeToken> allNeighbouringWildlife = new ArrayList<WildlifeToken>();

                for (HabitatTile neighbourTile : neighbourTiles) {
                    WildlifeToken neighbourToken = allPlacedTokens.get(neighbourTile.getWildlifeToken());
                    if (neighbourToken != null) {
                        allNeighbouringWildlife.add(neighbourToken);
                    }
                }

                if (!allNeighbouringWildlife.isEmpty()) {

                    List<WildlifeToken> uniqueWildlife = new ArrayList<>(allNeighbouringWildlife);

                    int numUniqueWildlife = uniqueWildlife.size();

                    scoringVals.add(foxScoringValues.get(numUniqueWildlife));
                    foxscoringVals.add(foxScoringValues.get(numUniqueWildlife));
                    //gotta make token scoring class to add stuff together
                }
            }
        }
    }

        private Map<Integer, Integer> hawkScoringValues;

        public void HawkScoring() {
            hawkScoringValues = new HashMap<>();
            hawkScoringValues.put(0, 0);
            hawkScoringValues.put(1, 2);
            hawkScoringValues.put(2, 5);
            hawkScoringValues.put(3, 8);
            hawkScoringValues.put(4, 11);
            hawkScoringValues.put(5, 14);
            hawkScoringValues.put(6, 18);
            hawkScoringValues.put(7, 22);
            hawkScoringValues.put(8, 26);
        }

        public void calculateHawkTokenScoring(Player p) {
            allPlacedTokens = p.getPlayerTiles();
            ArrayList<HabitatTile> tokenIDs = new ArrayList<>(allPlacedTokens.keySet());

            int numIsolatedHawks = 0;

            for (HabitatTile tokenID : tokenIDs) {
                WildlifeToken token = allPlacedTokens.get(tokenID);
                if (token == WildlifeToken.HAWK) {

                    List<HabitatTile> neighbourTiles = Graph.getNeighbors(tokenID);

                    boolean neighbouringHawks = false;

                    for (HabitatTile neighbourTile : neighbourTiles) {
                        WildlifeToken neighbourToken = allPlacedTokens.get(neighbourTile);
                        if (neighbourToken != null && neighbourToken == WildlifeToken.HAWK) {
                            neighbouringHawks = true;
                        }
                    }
                    if (!neighbouringHawks) {
                        numIsolatedHawks++;
                    }
                }
            }

            if (numIsolatedHawks > 8) {
                numIsolatedHawks = 8;
            }

            scoringVals.add(hawkScoringValues.get(numIsolatedHawks));
            hawkscoringVals.add(hawkScoringValues.get(numIsolatedHawks));
            //token scoring method needs to be created
        }

        private Map<Integer, Integer> salmonScoringValues;

        public void SalmonScoring() {
            salmonScoringValues = new HashMap<>();
            salmonScoringValues.put(1, 2);
            salmonScoringValues.put(2, 4);
            salmonScoringValues.put(3, 7);
            salmonScoringValues.put(4, 11);
            salmonScoringValues.put(5, 15);
            salmonScoringValues.put(6, 20);
            salmonScoringValues.put(7, 26);
        }
        //make sure u use used salmon tokens and also make sure to finish helper mehtods that accutalyy work
        public void calculateSalmonTokenScoring(Player p) {
            allPlacedTokens = p.getPlayerTiles();
            HashSet<HabitatTile> possibleSalmons = (HashSet<HabitatTile>) allPlacedTokens.keySet();

            ArrayList<HabitatTile> allSalmonTiles = new ArrayList<>();

            for (HabitatTile hToken : possibleSalmons) {
                WildlifeToken token = allPlacedTokens.get(hToken);
                if (token == WildlifeToken.SALMON) {
                    allSalmonTiles.add(hToken);
                }
            }

            List<HabitatTile> validSalmonTiles = new ArrayList<>();

            for (HabitatTile salmonTile : allSalmonTiles) {
                ArrayList<HabitatTile> neighbouringSalmon = Graph.searchNeighbourTilesForWildlife(allPlacedTokens, salmonTile, WildlifeToken.SALMON);
                if (neighbouringSalmon.size() <= 2) {
                    validSalmonTiles.add(salmonTile);
                }
            }

            List<List<HabitatTile>> confirmedSalmonRuns = new ArrayList<>();
            List<HabitatTile> usedSalmonTokenIDs = new ArrayList<>();
            for (HabitatTile validSalmonTile : validSalmonTiles) {

                ArrayList<HabitatTile> potentialSalmonTokenIDs = new ArrayList<>();

                if (!usedSalmonTokenIDs.contains(validSalmonTile)) {

                    List<HabitatTile> potentialNeighbourSalmon = Graph.searchNeighbourTilesForWildlife(allPlacedTokens, validSalmonTile, WildlifeToken.SALMON);
                    List<HabitatTile> confirmedNeighbourSalmon = new ArrayList<>();

                    for (HabitatTile potentialSalmon : potentialNeighbourSalmon) {
                        if (!usedSalmonTokenIDs.contains(potentialSalmon)) {
                            confirmedNeighbourSalmon.add(potentialSalmon);
                            usedSalmonTokenIDs.add(potentialSalmon);
                        }
                    }

                    if (confirmedNeighbourSalmon.size() == 2) {
                        List<HabitatTile> tilesToCheck = new ArrayList<HabitatTile>();
                        tilesToCheck.add(validSalmonTile);
                        tilesToCheck.addAll(confirmedNeighbourSalmon);

                        ArrayList<HabitatTile> firstNeighbourTiles = Graph.neighbourTiles(allPlacedTokens, confirmedNeighbourSalmon.get(0));
                        ArrayList<HabitatTile> secondNeighbourTiles = Graph.neighbourTiles(allPlacedTokens, confirmedNeighbourSalmon.get(1));

                        if (!firstNeighbourTiles.contains(confirmedNeighbourSalmon.get(1)) && !secondNeighbourTiles.contains(confirmedNeighbourSalmon.get(0))) {
                            ArrayList<HabitatTile> forwardsAndBackwardsSalmonRunIDs = Graph.forwardsAndBackwardsSalmonRun(allPlacedTokens, validSalmonTile, confirmedNeighbourSalmon);
                            potentialSalmonTokenIDs.addAll(forwardsAndBackwardsSalmonRunIDs);
                        } else {
                            potentialSalmonTokenIDs.addAll(tilesToCheck);
                            usedSalmonTokenIDs.addAll(tilesToCheck);
                        }
                    } else if (confirmedNeighbourSalmon.size() < 2) {
                        potentialSalmonTokenIDs.add(validSalmonTile);
                        ArrayList<HabitatTile> salmonRunIDs = Graph.salmonTokensInRun(allPlacedTokens, validSalmonTile, WildlifeToken.SALMON);
                        potentialSalmonTokenIDs.addAll(salmonRunIDs);
                    }
                    confirmedSalmonRuns.add(potentialSalmonTokenIDs);
                }
            }

            confirmedSalmonRuns.sort((a, b) -> b.size() - a.size());

            for (List<HabitatTile> confirmedSalmonRun : confirmedSalmonRuns) {
                List<HabitatTile> uniqueSalmonIDs = confirmedSalmonRun.stream().distinct().collect(Collectors.toList());
                int salmonInRunNum = uniqueSalmonIDs.size();
                if (salmonInRunNum > 7) {
                    salmonInRunNum = 7;
                }
                scoringVals.add(salmonScoringValues.get(salmonInRunNum));
                salmonscoringVals.add(salmonScoringValues.get(salmonInRunNum));

                //gotta fix
            }
        }

    public void calculateElkTokenScoring(Player p) {
        allPlacedTokens = p.getPlayerTiles();
        ArrayList<HabitatTile> tokenIDs = new ArrayList<>(allPlacedTokens.keySet());

        HashMap<HabitatTile, Integer> elkScoring = new HashMap<>();
        ArrayList<HabitatTile> usedElkTokenIDs = new ArrayList<>();

        for (HabitatTile tokenID : tokenIDs) {
            WildlifeToken token = allPlacedTokens.get(tokenID);
            if (token == WildlifeToken.ELK && !usedElkTokenIDs.contains(tokenID)) {
                int elkScore = 0;
                ArrayList<HabitatTile> elkGroup = new ArrayList<>();
                elkGroup.add(tokenID);
                usedElkTokenIDs.add(tokenID);

                ArrayList<HabitatTile> neighboringElk = Graph.searchNeighbourTilesForWildlife(allPlacedTokens, tokenID, WildlifeToken.ELK);
                for (HabitatTile neighboringElkTile : neighboringElk) {
                    if (!usedElkTokenIDs.contains(neighboringElkTile)) {
                        elkGroup.add(neighboringElkTile);
                        usedElkTokenIDs.add(neighboringElkTile);

                        ArrayList<HabitatTile> neighboringElk2 = Graph.searchNeighbourTilesForWildlife(allPlacedTokens, neighboringElkTile, WildlifeToken.ELK);
                        for (HabitatTile neighboringElkTile2 : neighboringElk2) {
                            if (!usedElkTokenIDs.contains(neighboringElkTile2)) {
                                elkGroup.add(neighboringElkTile2);
                                usedElkTokenIDs.add(neighboringElkTile2);
                            }
                        }
                    }
                }

                elkScore = calculateElkScore(elkGroup);
                elkScoring.put(tokenID, elkScore);
            }
        }

        for (Map.Entry<HabitatTile, Integer> entry : elkScoring.entrySet()) {
            totalElkScore += entry.getValue();
        }

        scoringVals.add(totalElkScore);
        elkscoringvals.add(totalElkScore);
    }

    private int calculateElkScore(ArrayList<HabitatTile> elkGroup) {
        int elkScore = 0;
        int elkGroupSize = elkGroup.size();

        if (elkGroupSize == 1) {
            elkScore = 2;
        } else if (elkGroupSize == 2) {
            elkScore = 4;
        } else if (elkGroupSize == 3) {
            elkScore = 7;
        } else if (elkGroupSize == 4) {
            elkScore = 10;
        } else if (elkGroupSize == 5) {
            elkScore = 14;
        } else if (elkGroupSize == 6) {
            elkScore = 18;
        }else if (elkGroupSize == 7) {
            elkScore = 23;
        }else if (elkGroupSize >= 8) {
            elkScore = 28;
        }

        return elkScore;
    }
    public int scoreHabitats(HashMap<HabitatTile, WildlifeToken> allPlacedTokens) {
        int score = 0;

        for (HabitatTile tile : allPlacedTokens.keySet()) {
            TreeMap<Integer, Biome> biomes = tile.getBiomes();
            for (Map.Entry<Integer, Biome> entry : biomes.entrySet()) {
                Biome biome = entry.getValue();
                int side = entry.getKey();
                HabitatTile neighbor = Graph.getNeighborWithSideBiome(tile, side, biome);
                if (neighbor != null) {
                    int neighborSide = Graph.getOppositeSide(side);
                    Biome neighborBiome = neighbor.getBiome(neighborSide);
                    if (biome == neighborBiome) {
                        score++;
                    }
                }
            }
        }

        return score;
    }


    // Helper method to find the largest connected component size of a specific biome









    // Assuming neighbourTileIDs is a method that returns a list of neighbouring tile IDs
    private List<String> neighbourTileIDs(String tokenID) {
        // Implement this method based on your requirements
        return new ArrayList<>();
    }

    public int getTotalScore(){
        return totalScore;
    }
    /*function setupFinalScoring() {

	$('#natureTokensScoringInput').html(natureCubesNum);

	$('#mapHiddenOverlay .mapTileContainer .placedWildlifeToken').each(function(){
		let tokenWildlife = $(this).attr('wildlife');
		$(this).attr('src', `img/tokens/${tokenWildlife}.png`);
	})

	checkForBlanks();
	processPlacedTilesAndTokens();

	calculateBearTokenScoring();
	calculateElkTokenScoring();
	calculateFoxTokenScoring();
	calculateHawkTokenScoring();
	calculateSalmonTokenScoring();

	const allWildlife = Object.keys(tokenScoring);

	for (const currentWildlife of allWildlife) {
		$('#wildlifeScoringTable.finalScoringTable .scoreCell #' + currentWildlife + '-wildlifeScoringInput').html(tokenScoring[currentWildlife].totalScore);

		$('#tileTokenContainer.finalScoring .finalScoringItem #' + currentWildlife + '-individualWildlifeScoringInput .individualPointsNum').html(tokenScoring[currentWildlife].totalScore);
		if(tokenScoring[currentWildlife].totalScore == 1) {
			$('#tileTokenContainer.finalScoring .finalScoringItem #' + currentWildlife + '-individualWildlifeScoringInput .pluralPoints').hide();
		} else {
			$('#tileTokenContainer.finalScoring .finalScoringItem #' + currentWildlife + '-individualWildlifeScoringInput .pluralPoints').show();
		}

	}

	updateAllSubtotals();

}


import java.util.*;

public class FoxScoring {
    private Map<Integer, Integer> foxScoringValues;


}





}

*/
}
