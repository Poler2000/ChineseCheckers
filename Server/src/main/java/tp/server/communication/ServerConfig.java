package tp.server.communication;

import tp.server.logic.Game;
import tp.server.map.Map;
import tp.server.structural.Field;
import tp.server.structural.GameState;

import java.util.ArrayList;

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
