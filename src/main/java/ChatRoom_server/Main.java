package ChatRoom_server;

import ChatRoom_server.Implement.Subclient_imp;
import ChatRoom_server.Implement.txtDataBase;
import ChatRoom_server.Interface.DataBase;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    public static void main(String[] args) {
        DataBase dataBase = new txtDataBase();
        try {
            ServerSocket server = new ServerSocket(9911);
            while (true){
                Socket s = server.accept();
                new Subclient_imp(s).start();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
