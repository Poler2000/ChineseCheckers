package network;

import game.NetworkEventsHandler;
import structural.*;

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
