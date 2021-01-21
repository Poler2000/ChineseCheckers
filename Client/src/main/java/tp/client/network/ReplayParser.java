package tp.client.network;

import tp.client.structural.*;
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;

/**
 * A parser for a list of replays
 * @author anon
 *
 */
public class ReplayParser {
	/**
	 * Parse replay list packet
	 * @param source the JSON packet
	 * @return replays/null on error
	 */
	public static Replay[] parse(JSONObject source){
		try {
			JSONArray games = source.getJSONArray("games");
			Replay[] ret = new Replay[games.length()];
			for (int i = 0; i < games.length(); i++) {
				Replay cur = new Replay();
				cur.id = games.getJSONObject(i).getInt("id");
				cur.players = games.getJSONObject(i).getInt("players");
				cur.date = games.getJSONObject(i).getString("date");
				ret[i] = cur;
			}
			return ret;
		}
		catch (JSONException ex) {
			return null;
		}
	}
}
