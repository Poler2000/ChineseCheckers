package tp.client.game;

import tp.client.structural.Step;

public interface UserEventsHandler {
    public boolean handleMovement(Step movement);
    public void handleGameStartReq();
    public void handleTurnEndReq();
    public void handleServerConnReq(String addr);
}