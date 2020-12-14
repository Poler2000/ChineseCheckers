package tp.client.network;

import tp.client.game.NetworkEventsHandler;
import tp.client.structural.*;

public class NetworkManager {
    private NetworkEventsHandler upstream;

    public NetworkManager(NetworkEventsHandler handl){
        upstream = handl;
    }

    public void sendGameStart(){

    }
    public void sendMove(Step[] move){

    }
    public boolean connect(String addr){
        return false;
    }

}
