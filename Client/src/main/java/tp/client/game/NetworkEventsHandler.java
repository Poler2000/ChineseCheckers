package tp.client.game;

import tp.client.structural.ServerConfig;
import tp.client.structural.StateReport;

public interface NetworkEventsHandler {
    public void handleNewServerCfg(ServerConfig recv);
    public void handleNewGameState(StateReport recv);
    public void handleServerDisconnect();

}
