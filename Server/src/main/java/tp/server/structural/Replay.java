package tp.server.structural;

import tp.server.db.GamesEntity;

public class Replay {
    ///ID of replay
    public int id;
    ///Number of players in recorded game
    public int players;
    ///Date when the game took place
    public String date;

    public Replay(GamesEntity gamesEntity) {
        id = gamesEntity.getId();
        players = gamesEntity.getPlayers();
        date = gamesEntity.getStart().toString();
    }
}
