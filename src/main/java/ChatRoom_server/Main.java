package ChatRoom_server;

import ChatRoom_server.Implement.Subclient_imp;
import ChatRoom_server.Implement.Database.txtDataBase;
import ChatRoom_server.Interface.DataBase;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    public static volatile ServerSocket server;
    public static void main(String[] args) {
        DataBase dataBase = new txtDataBase();
        dataBase.Load();
        Thread inputThread = new Thread(() -> {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
                System.out.println("Nhập 'exit' để thoát:");
                while (true) {
                    String input = reader.readLine(); // Đọc dòng từ bàn phím
                    if ("exit".equalsIgnoreCase(input)) {
                        server.close(); // Cập nhật giá trị biến stop
                        System.out.println("Đã thoát!");
                        break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        // Start thread
        inputThread.start();

        try {
            server = new ServerSocket(9911);
            while (true){
                Socket s = server.accept();
                new Subclient_imp(s).start();
            }
        } catch (IOException e) {
            dataBase.Save();
            System.out.println("exception");
            System.exit(1);
        }
    }
}
