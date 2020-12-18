package tp.server.structural;

/**
 * Represents states that game can be in.
 * Possibilities are:
 * UNSTARTABLE,
 * READY,
 * INPROGRESS,
 * UNKNOWN
 */
public enum GameState {
    UNSTARTABLE(0),
    READY(1),
    INPROGRESS(2),
    UNKNOWN(3);

    private int id;

    GameState(int i) {
        id = i;
    }

    public int getId() {
        return id;
    }

    public static GameState fromInt(final int n) {
        switch (n) {
            case 0:
                return GameState.UNSTARTABLE;
            case 1:
                return GameState.READY;
            case 2:
                return GameState.INPROGRESS;
            default:
                return GameState.UNKNOWN;
        }
    }
}
