package ChatRoom_server.Interface;

import ChatRoom_server.Implement.User;

import java.util.ArrayList;

public abstract class DataBase {
    private static ArrayList<User> clients = new ArrayList<>();
    private static ArrayList<Chatroom> chatrooms = new ArrayList<>();
    private static ArrayList<String> hisCl = new ArrayList<>();
    private static ArrayList<String> hisCr = new ArrayList<>();

    abstract public User Getuser(String name);
    abstract public Chatroom GetCr(String name);
    abstract public void Adduser(User user);
    abstract public void AddCr(Chatroom chatroom);
    abstract public void Save();
    abstract public void Load();
}
