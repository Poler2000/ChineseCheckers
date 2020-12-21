package tp.server.communication;

/**
 * Spine of all messages sent by server
 */
public abstract class ServerMsg {
    protected String type;
    public int yourPlayerID = 0;

    public ServerMsg(int id) {
        yourPlayerID = id;
    }

    public String getType() {
        return type;
    }

}
