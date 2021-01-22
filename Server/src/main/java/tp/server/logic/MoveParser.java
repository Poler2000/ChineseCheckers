package tp.server.logic;

import tp.server.db.MovesEntity;
import tp.server.map.Map;
import tp.server.structural.Field;
import tp.server.structural.Move;
import tp.server.structural.Pawn;
import tp.server.structural.Step;

import java.util.ArrayList;
import java.util.List;

public class MoveParser {
    public static ArrayList<Move> parseMoves(final List<MovesEntity> entities, final Map map, ArrayList<AbstractPlayer> players) {
        ArrayList<Move> moves = new ArrayList<>();
        for (MovesEntity e : entities) {
            Step step = new Step(players.get(e.getPlayerId() - 1).getPawns().get((e.getPawnId() % 10)),
                    map.getField(e.getDestX(), e.getDestY(), e.getDestZ()));
            Move move = new Move();
            move.addStep(step);
            moves.add(move);
        }
        return moves;
    }
}
