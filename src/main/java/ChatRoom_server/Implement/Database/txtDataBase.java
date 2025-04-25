package ChatRoom_server.Implement.Database;

import ChatRoom_server.Interface.Chatroom;
import ChatRoom_server.Interface.DataBase;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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

    @Override
    public String[] GetAllUser() {
        return User2Socket.keySet()
                .stream()
                .map(User::getName)
                .toArray(String[]::new);
    }

    @Override
    public String[] GetAllCr() {
        return chatrooms.stream()
                .map(Chatroom::getName)
                .toArray(String[]::new);
    }

    @Override
    public void Broadcast(String sender, String mess) {
        for (Map.Entry<User, Socket> entry : User2Socket.entrySet()) {
            User user = entry.getKey();
            Socket socket = entry.getValue();

            if (!sender.equals(user.getName())) {
                try {
                    PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                    out.println(mess);
                } catch (Exception e) {
                    System.err.println("Lỗi khi gửi tin nhắn đến " + user.getName() + ": " + e.getMessage());
                }
            }
        }
    }

    @Override
    public String[] GetjoinedCr(String name) {
        return GetjoinedCr(Getuser(name));
    }

    @Override
    public String[] GetjoinedCr(User user) {
        return chatrooms.stream()
                .filter(chatroom -> chatroom.checkUser(user)) // Lọc những phòng có checkUser(user) == true
                .map(Chatroom::getName) // Lấy tên phòng chat
                .toArray(String[]::new); // Chuyển danh sách thành mảng String[]
    }
}
