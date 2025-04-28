package ChatRoom_client.Interface;

import java.net.Socket;

public interface InterfaceClient {
    boolean connect(String host, int port);
    Socket getSocket();
    void setSocket(Socket s);
    String getName();
    void setName(String name);
}
