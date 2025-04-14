package ChatRoom_server.Interface;

import ChatRoom_server.Implement.User;

import java.net.Socket;

public interface DataBase {
    public User Getuser(String name);
    public Chatroom GetCr(String name);
    public void Adduser(User user);
    public void AddCr(Chatroom chatroom);
    public void Save();
    public void Load();
    public Socket GetSocketfUser(String name);
    public void AddUser2Socket(User user, Socket s);
    public  void RemoveUser2Socket(User user);
}
