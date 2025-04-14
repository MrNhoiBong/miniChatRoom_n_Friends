package ChatRoom_server.Interface;

import ChatRoom_server.Implement.User;

public interface DataBase {
    User Getuser(String name);
    Chatroom GetCr(String name);
    void Adduser(User user);
    void AddCr(Chatroom chatroom);
    void Save();
    void Load();
}
