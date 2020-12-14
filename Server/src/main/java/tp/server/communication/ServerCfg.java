package tp.server.communication;

import tp.server.logic.Game;
import tp.server.Map;

public class ServerCfg extends ServerMsg {
    private int players;
    private Game.gameStates state;
    private Map map;
    private int yourPlayerID;

    public ServerCfg(int players, Game.gameStates state, Map map, int id) {
        type = "ServerCfg";
        this.players = players;
        this.state = state;
        this.map = map;
        yourPlayerID = id;
    }

    public int getPlayers() {
        return players;
    }

    public Map getMap() {
        return map;
    }

    public Game.gameStates getState() {
        return state;
    }

    public int getYourPlayerID() {
        return yourPlayerID;
    }
}
