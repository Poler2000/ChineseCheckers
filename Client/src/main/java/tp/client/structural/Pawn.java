package tp.client.structural;

/**
 * A single pawn
 * @author anon
 *
 */
public class Pawn implements Cloneable{
	///ID
    public int id;
    ///Owner's ID
    public int playerid;
    ///The Field where this pawn is currently
    public Field location;

    public Pawn clone(){
        try{
            return (Pawn)super.clone();
        }
        catch (CloneNotSupportedException ex){
            return null;
        }
    }
}
