package tp.server.communication;

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
