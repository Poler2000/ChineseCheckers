package tp.server.communication;

public abstract class ServerMsg {
    protected String type;
    public int toPlayerID = 0;

    public ServerMsg(int id) {
        toPlayerID = id;
    }

    public String getType() {
        return type;
    }

}
