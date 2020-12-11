package tp.server;

import java.util.ArrayList;

public class ServerCfg extends ServerMsg {
    private int players;
    private Game.gameStates state;
    private ArrayList<Field> fields = new ArrayList<Field>();

    public ServerCfg(int players, Game.gameStates state, ArrayList<Field> fields) {
        type = "ServerCfg";
        this.players = players;
        this.state = state;
        this.fields = fields;
    }

    public int getPlayers() {
        return players;
    }

    public ArrayList<Field> getFields() {
        return fields;
    }

    public Game.gameStates getState() {
        return state;
    }
}
