package tp.client.network;

/**
 * A message to configure server-side
 * game parameters
 * Currently used to start a game by changing state
 * @author anon
 *
 */
public class SetupMsg {
	/**
	 * Get JSON String
	 */
    public String toString(){
        return "{ \"type\": \"setupMsg\", \"setState\": 2}";
    }
}
