package ChatRoom_server.Interface;

import ChatRoom_server.Implement.User;

public interface Chatroom {
    void Send2Cr(User user, String msg);
    void AddCl(User user);
    String getName();
}
