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

    private Map<String, String> allPlacedTokens;
    private Map<Integer, Integer> bearScoringValues;
    private int confirmedBearPairs;
    private ArrayList<String> potentialTokenIDs;
    private List<String> usedTokenIDs;

    public void BearScoring() {
        bearScoringValues = new HashMap<>();
        bearScoringValues.put(1, 4);
        bearScoringValues.put(2, 11);
        bearScoringValues.put(3, 19);
        bearScoringValues.put(4, 27);

        potentialTokenIDs = new ArrayList<>();
        usedTokenIDs = new ArrayList<>();
    }

    /*public void calculateBearTokenScoring() {
        allPlacedTokens = new HashMap<>(); // Initialize or assign the allPlacedTokens map

        confirmedBearPairs = 0;

        Set<String> tokenIDs = allPlacedTokens.keySet();

        for (String tokenID : tokenIDs) {
            potentialTokenIDs = new ArrayList<>();

            if (allPlacedTokens.get(tokenID).equals("bear") && !usedTokenIDs.contains(tokenID)) {

                List<HabitatTile> neighboringTiles = graph.getNeighboringTiles(allPlacedTokens.get(tokenID));

                for (HabitatTile neighborTile : neighboringTiles) {
                    if (allPlacedTokens.containsKey(neighborTile.getWildlifeToken())) {
                        // The neighboring tile exists and has a placed token on it!
                        // Continue with the specified scoring process for this wildlife'
                        if (allPlacedTokens.get(neighborTile.getID()).equals("bear") && neighborTile.getColor().equals(allPlacedTokens.get(tokenID).getColor())) {
                            potentialTokenIDs.add(neighborTile);
                        }
                    }
                }

                if (potentialTokenIDs.size() == 1) {
                    // Only one neighboring bear means it only has the pair and could qualify for scoring!
                    // Need to now make sure there's no bears touching the matched neighbor tile before locking it in for scoring

                    List<HabitatTile> potentialBearPairNeighbourTiles = graph.getNeighboringTiles(potentialTokenIDs.get(0));
                    for (HabitatTile potentialBearPairNeighbourTile : potentialBearPairNeighbourTiles) {
                        if (allPlacedTokens.containsKey(potentialBearPairNeighbourTile.getID())) {
                            // The neighboring tile exists and has a placed token on it!
                            // Continue with the specified scoring process for this wildlife'

                            if (allPlacedTokens.get(potentialBearPairNeighbourTile.getID()).equals("bear") && potentialBearPairNeighbourTile.getColor().equals(allPlacedTokens.get(tokenID).getColor())) {
                                potentialTokenIDs.add(potentialBearPairNeighbourTile);
                            }
                        }
                    }
                    if (potentialTokenIDs.size() == 2) {
                        if (confirmedBearPairs <= 4) {
                            confirmedBearPairs++;
                        }
                    }
                }
                usedTokenIDs.addAll(potentialTokenIDs);
            }
        }

        if (confirmedBearPairs != 0) {
            // Assuming tokenScoring is an object with a bear.totalScore property
            tokenScoring.bear.totalScore = bearScoringValues.get(confirmedBearPairs);
        }
    }

    // Assuming neighbourTileIDs is a method that returns a list of neighbouring tile IDs
    private List<String> neighbourTileIDs(String tokenID) {
        // Implement this method based on your requirements
        return new ArrayList<>();
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
*/
}
