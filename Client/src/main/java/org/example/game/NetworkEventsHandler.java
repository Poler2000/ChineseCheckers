package game;

import structural.ServerConfig;
import structural.StateReport;

public interface NetworkEventsHandler {
    public void handleNewServerCfg(ServerConfig recv);
    public void handleNewGameState(StateReport recv);
    public void handleServerDisconnect();

}
