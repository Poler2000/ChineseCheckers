package tp.server.communication;

import tp.server.structural.Pawn;

import java.util.ArrayList;

/**
 * Contains information specifying current state of play -
 * positions of pawns, current player and winners
 */
public class StateReport extends ServerMsg {
    public int turnOf;
    public ArrayList<MessagePawn> pawns = new ArrayList<>();
    public int won = 0;

    public StateReport(int currentPlayer, ArrayList<Pawn> pawns, int wonPlayer, int id) {
        super(id);
        type = "gameState";
        for (Pawn p : pawns) {
            this.pawns.add(new MessagePawn(p.getId(), p.getOwner(), p.getLocation().getId()));
        }
        this.turnOf = currentPlayer;
        this.won = wonPlayer;
    }

    public class MessagePawn {
        public int id;
        public int owner;
        public int location;

        public MessagePawn(int id, int owner, int location) {
            this.id = id;
            this.owner = owner;
            this.location = location;
        }
    }
}
