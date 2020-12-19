package tp.server.communication;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import tp.server.map.Map;
import tp.server.structural.Move;
import tp.server.structural.Pawn;
import tp.server.structural.Step;

import java.util.ArrayList;

/**
 * Parses given message to move
 */
public class ClientMessageParser {
    public Move getMove(JsonNode node, Map map, ArrayList<Pawn> pawns) {
        ObjectMapper objectMapper = new ObjectMapper();
        Move move = new Move();
        if (node.isArray()) {
            for (final JsonNode objNode : node) {
                int dest = objNode.get("newlocation").asInt();
                int p = objNode.get("pawn").asInt();
                for (Pawn pawn : pawns) {
                    if (pawn.getId() == p) {
                        Step step = new Step(pawn, map.getById(dest));
                        move.addStep(step);
                        break;
                    }
                }

            }
        }
        return move;
    }
}
