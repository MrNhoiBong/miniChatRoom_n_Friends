package ChatRoom_server.Implement.Database;

import ChatRoom_server.Interface.Chatroom;
import ChatRoom_server.Interface.DataBase;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

public class txtDataBase implements DataBase {
    private static ArrayList<User> clients = new ArrayList<>();
    private static ArrayList<Chatroom> chatrooms = new ArrayList<>();
    private static HashMap<User, Socket> User2Socket = new HashMap<>();

    @Override
    public User Getuser(String name) {
        User target_user = null;
        for (User user: clients){
            if (name.equals(user.getName())){
                target_user = user;
                break;
            }
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
    public void AddCr(String nameChatroom) {
        chatrooms.add(new Chatroom_imp(nameChatroom));
    }

    @Override
    public void Save() {
        String fileName = "data";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (User user : clients) {
                writer.write(user.getName() + ":" + user.getPw());
                writer.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void Load() {
        String fileName = "data";
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 2) {
                    clients.add(new User(parts[0], parts[1]));
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
    public Socket GetSocketfUser(User user) {
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

    @Override
    public void RemoveUser2Socket(User user) {
        User2Socket.remove(user);
    }
}
