package tp.client.network;

import tp.client.structural.*;
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;
import java.util.Map;

/**
 * A class to parse game state updates
 * from JSON to StateReport (or null if invalid)
 * @author anon
 *
 */
public class StateParser {
	/**
	 * Parse state
	 * @param source the JSON to parse
	 * @param frefs mapping of field ids to Field objects
	 * @return parsed state or null on error
	 */
	public static StateReport parse(JSONObject source, Map<Integer, Field> frefs) {
		try {
			StateReport ret = new StateReport();
			ret.currentPlayer = source.getInt("turnOf");
			JSONArray pawns = source.getJSONArray("pawns");
			ret.deployment = new Pawn[pawns.length()];
			for (int i = 0; i < pawns.length(); i++) {
				Pawn cur = new Pawn();
				JSONObject cursrc = pawns.getJSONObject(i);
				cur.id = cursrc.getInt("id");
				cur.playerid = cursrc.getInt("owner");
				cur.location = frefs.get(cursrc.getInt("location"));
				if (cur.location == null) {
					throw new JSONException("No such field found");
				}
				ret.deployment[i] = cur;
			}
			if (source.has("won")) {
				ret.wonPlayer = source.getInt("won");
			}
			ret.toPlayerID = source.getInt("yourPlayerID");
			return ret;
		}
		catch (JSONException ex) {
			return null;
		}
	}
}
