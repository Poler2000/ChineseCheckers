package tp.server.communication;

public abstract class ServerMsg {
    protected String msgType;
    public int toPlayerID = 0;

    public ServerMsg(int id) {
        toPlayerID = id;
    }

    public String getMsgType() {
        return msgType;
    }

}
