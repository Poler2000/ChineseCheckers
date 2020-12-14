package tp.client.structural;

public class Pawn implements Cloneable{
    public int id;
    public int playerid;
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
