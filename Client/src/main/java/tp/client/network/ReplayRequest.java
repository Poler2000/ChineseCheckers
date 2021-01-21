package tp.client.network;

/**
 * Requests for handling replay functionality
 * @author anon
 *
 */
public class ReplayRequest {
	private String content;
	
	/**
	 * Create request to get available replay list
	 */
	public ReplayRequest() {
		content = "{ \"type\": \"replayRequest\"}";
	}
	
	/**
	 * Create request to load a replay
	 * @param id to load
	 */
	public ReplayRequest(Integer id) {
		content = "{ \"type\": \"loadReplay\", \"id\": " + id.toString() + "}";
	}
	
	/**
	 * Get JSON string of this packet
	 */
	public String toString() {
		return content;
	}
}
