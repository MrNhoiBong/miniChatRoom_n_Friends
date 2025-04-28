package ChatRoom_client.Interface;

import java.net.Socket;

public interface InterfaceSendCmd {
    Socket getSocket();
    void setSocket(Socket s);
    void sendCmd(String command);
}
