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
    public void calculateBearTokenScoring(Player p) {
        Map<HabitatTile, WildlifeToken> allPlacedTokens = p.getPlayerTiles();
        Map<Integer, Integer> bearScoringValues = new HashMap<>();
        bearScoringValues.put(1, 4);
        bearScoringValues.put(2, 11);
        bearScoringValues.put(3, 19);
        bearScoringValues.put(4, 27);

        int confirmedBearPairs = 0;
        List<HabitatTile> usedTokenIDs = new ArrayList<>();

        List<HabitatTile> tokenIDs = new ArrayList<>(allPlacedTokens.keySet());

        for (HabitatTile tokenID : tokenIDs) {
            if (allPlacedTokens.get(tokenID) != null && allPlacedTokens.get(tokenID).equals(WildlifeToken.BEAR) && !usedTokenIDs.contains(tokenID)) {

                List<HabitatTile> neighbourTiles = Graph.getNeighbors(tokenID);

                for (HabitatTile neighbourTile : neighbourTiles) {
                    if (allPlacedTokens.containsKey(neighbourTile)) {
                        if (allPlacedTokens.get(neighbourTile) != null && allPlacedTokens.get(neighbourTile).equals(WildlifeToken.BEAR) && !usedTokenIDs.contains(neighbourTile)) {
                            usedTokenIDs.add(neighbourTile);
                        }
                    }
                }

                if (usedTokenIDs.size() == 1) {
                    List<HabitatTile> potentialBearPairNeighborTiles = Graph.getNeighbors(usedTokenIDs.get(0));

                    for (HabitatTile potentialBearPairNeighborTile : potentialBearPairNeighborTiles) {
                        if (allPlacedTokens.containsKey(potentialBearPairNeighborTile)) {
                            if (allPlacedTokens.get(potentialBearPairNeighborTile) != null && allPlacedTokens.get(potentialBearPairNeighborTile).equals(WildlifeToken.BEAR) && !usedTokenIDs.contains(potentialBearPairNeighborTile)) {
                                usedTokenIDs.add(potentialBearPairNeighborTile);
                            }
                        }
                    }

                    if (usedTokenIDs.size() == 2) {
                        if (confirmedBearPairs <= 4) {
                            confirmedBearPairs++;
                        }
                    }
                }

            }
        }
        usedTokenIDs.clear();

        if (confirmedBearPairs != 0) {
            bearscoringVals.add(bearScoringValues.get(confirmedBearPairs));
        }

        System.out.println("confirmed bear pairs: " + confirmedBearPairs);

    }
    public void calculateFoxTokenScoring(Player p) {
        foxScoringValues = new HashMap<>();
        foxScoringValues.put(1, 1);
        foxScoringValues.put(2, 2);
        foxScoringValues.put(3, 3);
        foxScoringValues.put(4, 4);
        foxScoringValues.put(5, 5);

        allPlacedTokens = p.getPlayerTiles();
        HashSet<HabitatTile> possibleFoxTiles = new HashSet<>();

        for (Map.Entry<HabitatTile, WildlifeToken> entry : allPlacedTokens.entrySet()) {
            if (entry.getValue() == WildlifeToken.FOX) {
                possibleFoxTiles.add(entry.getKey());
            }
        }

        for (HabitatTile tokenID : possibleFoxTiles) {
            List<HabitatTile> neighbourTiles = Graph.getNeighbors(tokenID);

            ArrayList<WildlifeToken> allNeighbouringWildlife = new ArrayList<>();

            for (HabitatTile neighbourTile : neighbourTiles) {
                if (neighbourTile != null && allPlacedTokens.containsKey(neighbourTile)) {
                    WildlifeToken neighbourToken = allPlacedTokens.get(neighbourTile);
                    allNeighbouringWildlife.add(neighbourToken);
                }
            }

            if (!allNeighbouringWildlife.isEmpty()) {

                Set<WildlifeToken> uniqueWildlife = new HashSet<>(allNeighbouringWildlife);

                int numUniqueWildlife = uniqueWildlife.size();

                foxscoringVals.add(foxScoringValues.get(numUniqueWildlife));
            }
        }

        System.out.println("fox scores: " + foxscoringVals);
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


        hawkscoringVals.add(hawkScoringValues.get(numIsolatedHawks));
        System.out.println("hawk " + numIsolatedHawks + " Player" + p);
        System.out.println("hawkScore " + hawkscoringVals.get(0));
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
        int salmonInRunNum = 0;
        allPlacedTokens = p.getPlayerTiles();
        HashSet<HabitatTile> possibleSalmons = new HashSet<>(allPlacedTokens.keySet());

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

        usedSalmonTokenIDs.clear(); // Clear usedSalmonTokenIDs at the end of the method

        confirmedSalmonRuns.sort((a, b) -> b.size() - a.size());

        for (List<HabitatTile> confirmedSalmonRun : confirmedSalmonRuns) {
            List<HabitatTile> uniqueSalmonIDs = confirmedSalmonRun.stream().distinct().collect(Collectors.toList());
            int uniqueSalmonIDsSize = uniqueSalmonIDs.size();
            if (uniqueSalmonIDsSize > 7) {
                uniqueSalmonIDsSize = 7;
            }
            salmonscoringVals.add(salmonScoringValues.get(uniqueSalmonIDsSize));
            salmonInRunNum = uniqueSalmonIDsSize;
            System.out.println(salmonInRunNum + "SALMONDSNSDLFNSDFJ");
        }
    }

    public void calculateElkTokenScoring(Player p) {
        allPlacedTokens = p.getPlayerTiles();
        ArrayList<HabitatTile> tokenIDs = new ArrayList<>(allPlacedTokens.keySet());
        int elkScore = 0;
        HashMap<HabitatTile, Integer> elkScoring = new HashMap<>();
        ArrayList<HabitatTile> usedElkTokenIDs = new ArrayList<>();

        for (HabitatTile tokenID : tokenIDs) {
            WildlifeToken token = allPlacedTokens.get(tokenID);
            if (token == WildlifeToken.ELK && !usedElkTokenIDs.contains(tokenID)) {
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

            }
        }



        scoringVals.add(elkScore);
        elkscoringvals.add(elkScore);
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
    public int scoreHabitats(HashMap<HabitatTile, WildlifeToken> allPlacedTokens, Biome specificBiome) {
        int score = 0;
        System.out.println("SCORE HABITATS CALLED!!!!");

        // Check the contents of the allPlacedTokens parameter
        System.out.println("allPlacedTokens contains:");
        for (HabitatTile tile : allPlacedTokens.keySet()) {
            System.out.println(" - " + tile);
            TreeMap<Integer, Biome> biomes = tile.getBiomes();
            if (biomes.containsValue(specificBiome)) {
                System.out.println("  - Contains specificBiome: " + specificBiome);
            }
        }

        for (HabitatTile tile : allPlacedTokens.keySet()) {
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
                                score++;
                            }
                        }

                        // Check neighbouring tiles in all directions
                        for (int i = 0; i < 6; i++) {
                            HabitatTile neighbour = Graph.getNeighborWithSideBiome(tile, i, biomes.get(i));
                            if (neighbour != null) {
                                Biome neighbourBiome = neighbour.getBiome(Graph.getOppositeSide(i));
                                if (biomes.get(i) == neighbourBiome) {
                                    score++;
                                }
                            }
                        }
                    }
                }
            }
        }

        System.out.println("Final score: " + specificBiome + ": " + score);
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
