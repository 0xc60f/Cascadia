package org.example;

import java.util.*;

public class Game {
    //holds the habitatTile and wildlifeToken that are currently displayed in relation to each other.
    private HashMap<HabitatTile, WildlifeToken> displayed;
    private ArrayList<WildlifeToken> possibleWildlife;
    private ArrayList<HabitatTile> possibleHabitatTiles;
    //make this the construcotr for the actual game so the 4 displayed and make another one for the player class. Add to graph as well
    public Game(){
        int i = 0;
        displayed = new HashMap<HabitatTile, WildlifeToken>();
        possibleWildlife = new ArrayList<WildlifeToken>();
        possibleHabitatTiles = new ArrayList<HabitatTile>();
        while(i < 60){
     //instantiate all the pssible habitatTiles
            HabitatTile h = new HabitatTile(Biome.MOUNTAIN, Biome.RIVER, possibleWildlife);
            i++;
        }
        possibleWildlife.add(WildlifeToken.SALMON);
        possibleWildlife.add(WildlifeToken.HAWK);
        possibleWildlife.add(WildlifeToken.BEAR);
        possibleWildlife.add(WildlifeToken.ELK);
        possibleWildlife.add(WildlifeToken.FOX);
        //for(int j = 0; j < displayed.size(); j++){

        //}
    }
}
