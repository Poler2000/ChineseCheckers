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
    UNSTARTABLE,
    READY,
    INPROGRESS,
    UNKNOWN;

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
