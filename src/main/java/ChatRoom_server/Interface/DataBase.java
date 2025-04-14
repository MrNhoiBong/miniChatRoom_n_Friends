package ChatRoom_server.Interface;

import ChatRoom_server.Implement.User;

import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

public abstract class DataBase {
    private static ArrayList<User> clients = new ArrayList<>();
    private static ArrayList<Chatroom> chatrooms = new ArrayList<>();
    private static ArrayList<String> hisCl = new ArrayList<>();
    private static ArrayList<String> hisCr = new ArrayList<>();
    private static HashMap<User, Socket> User2Socket = new HashMap<>();

    abstract public User Getuser(String name);
    abstract public Chatroom GetCr(String name);
    abstract public void Adduser(User user);
    abstract public void AddCr(Chatroom chatroom);
    abstract public void Save();
    abstract public void Load();
    abstract public Socket GetSocketfUser(String name);
    abstract public void AddUser2Socket(User user, Socket s);
}
