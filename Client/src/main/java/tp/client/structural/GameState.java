package tp.client.structural;

public enum GameState {
    UNSTARTABLE,
    READY,
    INPROGRESS,
    UNKNOWN;
    
	public static GameState fromInt(int n) {
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
