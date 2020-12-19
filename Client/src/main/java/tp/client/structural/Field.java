package tp.client.structural;

/**
 * A game board tile
 * @author anon
 *
 */
public class Field {
	//ID
    public int id;
    //Coordinates x,y,z in cubic system
    public int coordinates[];
    //Whose goal is this tile or 0 if noone's
    public int playerGoal;
}
