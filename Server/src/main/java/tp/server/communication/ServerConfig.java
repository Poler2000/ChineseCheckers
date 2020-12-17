package tp.server.communication;

import tp.server.map.Map;
import tp.server.structural.GameState;

public class ServerConfig extends ServerMsg {
    public int players;
    public GameState gamestate;
    public Map map;

    public ServerConfig(int players, GameState state, Map map, int id) {
        super(id);
        type = "config";
        this.players = players;
        this.gamestate = state;
        this.map = map;
    }
}
