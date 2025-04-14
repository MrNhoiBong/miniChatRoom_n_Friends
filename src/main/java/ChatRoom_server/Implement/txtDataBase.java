package ChatRoom_server.Implement;

import ChatRoom_server.Interface.Chatroom;
import ChatRoom_server.Interface.DataBase;

import java.util.ArrayList;

public class txtDataBase implements DataBase {
    private static ArrayList<User> clients = new ArrayList<>();
    private static ArrayList<Chatroom> chatrooms = new ArrayList<>();
    private static ArrayList<String> hisCl = new ArrayList<>();
    private static ArrayList<String> hisCr = new ArrayList<>();

    @Override
    public User Getuser(String name) {
        User target_user = null;
        for (User user: clients){
            if (!name.equals(user.getName())){continue;}
            target_user = user;
        }
        return target_user;
    }

    @Override
    public Chatroom GetCr(String name) {
        Chatroom targetCr = null;
        for (Chatroom chatroom: chatrooms){
            if(!name.equals(chatroom.getName())){continue;}
            targetCr = chatroom;
        }
        return targetCr;
    }

    @Override
    public void Adduser(User user) {
        clients.add(user);
    }

    @Override
    public void AddCr(Chatroom chatroom) {
        chatrooms.add(chatroom);
    }

    @Override
    public void Save() {

    }

    @Override
    public void Load() {

    }
}
