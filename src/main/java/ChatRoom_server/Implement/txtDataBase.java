package ChatRoom_server.Implement;

import ChatRoom_server.Interface.Chatroom;
import ChatRoom_server.Interface.DataBase;

import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

public class txtDataBase extends DataBase {
    private static ArrayList<User> clients = new ArrayList<>();
    private static ArrayList<Chatroom> chatrooms = new ArrayList<>();
    private static ArrayList<String> hisCl = new ArrayList<>();
    private static ArrayList<String> hisCr = new ArrayList<>();
    private static HashMap<User, Socket> User2Socket = new HashMap<>();

    public User Getuser(String name) {
        User target_user = null;
        for (User user: clients){
            if (!name.equals(user.getName())){continue;}
            target_user = user;
        }
        return target_user;
    }

    public Chatroom GetCr(String name) {
        Chatroom targetCr = null;
        for (Chatroom chatroom: chatrooms){
            if(!name.equals(chatroom.getName())){continue;}
            targetCr = chatroom;
        }
        return targetCr;
    }

    public void Adduser(User user) {
        clients.add(user);
    }

    public void AddCr(Chatroom chatroom) {
        chatrooms.add(chatroom);
    }

    public void Save() {

    }

    public void Load() {

    }

    @Override
    public Socket GetSocketfUser(String name) {
        User user = Getuser(name);
        if (user != null){
            return User2Socket.get(user);
        }
        else {
            return null;
        }
    }

    @Override
    public void AddUser2Socket(User user, Socket s) {
        User2Socket.put(user, s);
    }
}
