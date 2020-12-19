package tp.client.structural;

/**
 * A server message indicating a turn change
 * @author anon
 *
 */
public class StateReport extends ServerMsg{
	///Whose turn is it currently
    public int currentPlayer;
    ///The current pawns positions
    public Pawn[] deployment;
    ///Who won the game (or 0 if ongoing)
    public int wonPlayer = 0;
}
