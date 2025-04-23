package ChatRoom_server.Implement.Database;

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
            Socket s = dataBase.GetSocketfUser(user);
            String from = from_user.getName();
            try {
                if (user.getName().equals(from)){continue;}
                PrintWriter send_msg = new PrintWriter( new OutputStreamWriter(s.getOutputStream()));
                String mess2cr = from +":"+name+":" + msg;
                System.out.println(mess2cr);
                send_msg.println(mess2cr);
                send_msg.flush();
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

    @Override
    public boolean checkUser(String name) {
        for (User user:clients){
            if (user.getName().equals(name)){
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean checkUser(User sender) {
        for (User user:clients){
            if (user == sender){
                return true;
            }
        }
        return false;
    }
}
