package tp.client.network;

/**
 * A message sent to the server at the beggining of the connection
 * @author anon
 *
 */
public class RegistrationMsg {
	/**
	 * Get JSON string
	 */
    public String toString(){
        return "{ \"type\": \"registerMsg\"}";
    }
}
