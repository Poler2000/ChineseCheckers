package tp.client.network;

import org.json.JSONObject;
import org.json.JSONArray;
import tp.client.structural.*;

/**
 * A class to covert move information into
 * a ready to send JSON string
 * @author anon
 *
 */
public class MoveMsg {
	
	private JSONObject message;
	
	/**
	 * Constructor
	 * @param move the move to represent
	 */
	public MoveMsg(Step[] move) {
		message = new JSONObject();
		message.put("type", "playerMove");
		JSONArray steps = new JSONArray();
		for (int i = 0; i < move.length; i++) {
			JSONObject step = new JSONObject();
			step.put("pawn", move[i].actor.id);
			step.put("newlocation", move[i].destination.id);
			steps.put(step);
		}
		message.put("steps", steps);
	}
	
	/**
	 * Get the JSON string representation
	 */
	public String toString() {
		return message.toString();
	}
    
}
