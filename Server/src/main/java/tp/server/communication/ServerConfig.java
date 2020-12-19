package tp.server.communication;

import tp.server.structural.Field;
import tp.server.structural.GameState;

import java.util.ArrayList;

/**
 * Sent in process of registration
 * Specify number of players, current game state and map
 */
public class ServerConfig extends ServerMsg {
    public int players;
    public int gamestate;
    public ArrayList<Field> map = new ArrayList<>();

    public ServerConfig(int players, GameState state, ArrayList<Field> map, int id) {
        super(id);
        type = "config";
        this.players = players;
        this.gamestate = state.getId();
        this.map = map;
    }
}
