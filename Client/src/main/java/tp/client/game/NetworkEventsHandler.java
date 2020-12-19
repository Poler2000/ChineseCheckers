package tp.client.game;

import tp.client.structural.ServerConfig;
import tp.client.structural.StateReport;

/**
 * A simple interface describing a client
 * using NetworkManager, and it's callbacks
 * @author anon
 *
 */
public interface NetworkEventsHandler {
    public void handleNewServerCfg(ServerConfig recv);
    public void handleNewGameState(StateReport recv);
    public void handleServerDisconnect();
    public void handleServerConnect();

}
