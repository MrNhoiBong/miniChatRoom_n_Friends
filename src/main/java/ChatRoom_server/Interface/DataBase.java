package ChatRoom_server.Interface;

import java.util.ArrayList;

public interface DataBase {
    User Getuser(String name);
    Chatroom GetCr(String name);
    void Adduser(User user);
    void AddCr(Chatroom chatroom);
    void Save();
    void Load();
}
