package tp.client;

import tp.client.graphical.GUIManager;
import tp.client.structural.*;

public class GUISample {
    public static void main(String[] args){
    	int[][] tiles = {{0,0,0},{-1,1,0},{-1,0,1},{0,-1,1},{1,-1,0},{1,0,-1},{0,1,-1},{-1,-1,2},{1,1,-2}};
    	
        GUIManager mai = new GUIManager();
        Field[] map = new Field[tiles.length];
        
        for (int i = 0; i < tiles.length; i++) {
        	Field tile = new Field();
        	tile.coordinates = tiles[i];
        	map[i] = tile;
        }

        Pawn[] pieces = new Pawn[2];
        Pawn sample = new Pawn();
        sample.playerid = 1;
        sample.id = 1;
        sample.location = map[2];
        
        Pawn sample2 = new Pawn();
        sample2.playerid = 2;
        sample2.id = 2;
        sample2.location = map[5];
        
        pieces[0] = sample;
        pieces[1] = sample2;

        mai.setMap(map );
        mai.setPawns(pieces, 1);
    }
}
