package ChatRoom_server.Interface;

import ChatRoom_server.Implement.User;

import java.net.Socket;

public interface Chatroom {
    void Send2Cr(User user, String msg);
    void AddCl(User user);
    String getName();
    boolean checkUser(String name);
    boolean checkUser(User sender);
}
