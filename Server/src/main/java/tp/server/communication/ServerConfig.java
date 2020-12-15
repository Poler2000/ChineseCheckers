package tp.server.communication;

import tp.server.logic.Game;
import tp.server.Map;
import tp.server.structural.GameState;

public class ServerConfig extends ServerMsg {
    public int playersInGame;
    public GameState state;
    public Map map;

    public ServerConfig(int players, GameState state, Map map, int id) {
        super(id);
        this.playersInGame = players;
        this.state = state;
        this.map = map;
    }
}
