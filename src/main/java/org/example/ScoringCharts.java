package org.example;
import java.util.*;
public class ScoringCharts {
    private int totalScore;
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
    private HashMap<Integer, Integer> foxScoringValues;
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

        if (confirmedBearPairs != 0) {
            //tokenScoring.bear.totalScore = bearScoringValues.get(confirmedBearPairs);
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

                    //tokenScoring.fox.totalScore += foxScoringValues.get(numUniqueWildlife);
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

            //tokenScoring.hawk.totalScore = hawkScoringValues.get(numIsolatedHawks);
            //token scoring method needs to be created
        }



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
