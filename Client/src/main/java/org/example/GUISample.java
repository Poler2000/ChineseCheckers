import graphical.GUIManager;

import structural.*;

public class GUISample {
    public static void main(String[] args){
        GUIManager mai = new GUIManager();
        Field[] map = new Field[3];
        Field tile = new Field();
        tile.coordinates = new int[]{0,0,0};
        Field tile2 = new Field();
        tile2.coordinates = new int[]{0,-1,1};
        Field tile3 = new Field();
        tile3.coordinates = new int[]{-1,-1,2};
        map[0] = tile;
        map[1] = tile2;
        map[2] = tile3;
        Pawn[] pieces = new Pawn[1];
        Pawn sample = new Pawn();
        sample.playerid = 1;
        sample.id = 1;
        sample.location = tile2;
        pieces[0] = sample;

        mai.setMap(map );
        mai.setPawns(pieces);
    }
}
