package ChatRoom_server.Interface;

public interface ChainCmd {
    void Hanlde(Object request);
    void setNext(ChainCmd chainCmd);
}
