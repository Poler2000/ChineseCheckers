package tp.client.structural;

/**
 * A server message with new game parameters
 * @author anon
 *
 */
public class ServerConfig extends ServerMsg{
	///Number of players connected to the server
    public int playersInGame;
    ///The current game state
    public GameState gamestate;
    ///The board currently being used
    public Field[] map;
}
