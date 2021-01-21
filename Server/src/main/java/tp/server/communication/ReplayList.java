package tp.server.communication;

import tp.server.db.GamesEntity;
import tp.server.structural.Replay;

import java.util.ArrayList;
import java.util.List;

public class ReplayList extends ServerMsg {
    public List<Replay> games = new ArrayList<>();

    public ReplayList(final List<Replay> games, final int id) {
        super(id);
        type = "replayList";
        this.games = games;
    }
}
