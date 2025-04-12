package ChatRoom_server.Interface;

import java.net.Socket;

public interface Chatroom {
    void Send2Cr(String msg);
    void AddCl(User user);
}
