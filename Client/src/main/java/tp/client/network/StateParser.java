package tp.client.network;

import tp.client.structural.*;
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;
import java.util.Map;

public class StateParser {
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
