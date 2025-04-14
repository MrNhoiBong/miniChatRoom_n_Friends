package ChatRoom_server.Implement;

import ChatRoom_server.Interface.Chatroom;
import ChatRoom_server.Interface.DataBase;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class Chatroom_imp implements Chatroom {
    private String name;
    private ArrayList<User> clients = new ArrayList<>();

    public Chatroom_imp(String name) {
        this.name = name;
    }

    @Override
    public void Send2Cr(User from_user, String msg) {
        DataBase dataBase = new txtDataBase();
        for (User user: clients) {
            Socket s = dataBase.GetSocketfUser(user.getName());
            String from = from_user.getName();
            try {
                if (user.getName().equals(from)){continue;}
                PrintWriter send_msg = new PrintWriter( new OutputStreamWriter(s.getOutputStream()));
                send_msg.println(from + ": " + msg);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
    }

    @Override
    public void AddCl(User user) {
        clients.add(user);
    }

    public String getName() {
        return name;
    }
}
