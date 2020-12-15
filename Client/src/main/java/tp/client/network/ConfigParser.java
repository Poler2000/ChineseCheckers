package tp.client.network;

import tp.client.structural.*;
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;

public class ConfigParser{
	public static ServerConfig parse(JSONObject source) {
		try {
			ServerConfig ret = new ServerConfig();
			ret.playersInGame = source.getInt("players");
			ret.gamestate = GameState.fromInt(source.getInt("gamestate"));
			JSONArray fields = source.getJSONArray("map");
			ret.map = new Field[fields.length()];
			for (int i = 0; i < fields.length(); i++) {
				JSONObject representation = fields.getJSONObject(i);
				Field cur = new Field();
				cur.id = representation.getInt("id");
				int[] coords = new int[3];
				JSONArray coordjson = representation.getJSONArray("coords");
				if (coordjson.length() != 3) {
					throw new JSONException("Field with wrong num of coordinates");
				}
				else {
					coords[0] = coordjson.getInt(0);
					coords[1] = coordjson.getInt(1);
					coords[2] = coordjson.getInt(2);
				}
				cur.coordinates = coords;
				cur.playerGoal = representation.getInt("goalof");
				ret.map[i] = cur;
			}
			ret.toPlayerID = source.getInt("yourPlayerID");
			return ret;
		}
		catch (JSONException ex) {
			return null;
		}
	}
}
