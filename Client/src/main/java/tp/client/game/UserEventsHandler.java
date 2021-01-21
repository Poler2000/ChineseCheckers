package tp.client.game;

import tp.client.structural.Step;

/**
 * A simple interface describing a client
 * using GUIManager, and it's callbacks
 * @author anon
 *
 */
public interface UserEventsHandler {
	public Object getMapLock();
    public boolean handleMovement(Step movement);
    public void handleGameStartReq();
    public void handleTurnEndReq();
    public void handleServerConnReq(String addr);
    public void handleReplayRequest();
    public void handleReplayRequest(Integer id);
}
