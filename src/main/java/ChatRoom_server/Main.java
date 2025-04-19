package ChatRoom_server;

import ChatRoom_server.Implement.Subclient_imp;
import ChatRoom_server.Implement.Database.txtDataBase;
import ChatRoom_server.Interface.DataBase;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class Main {
    public static volatile ServerSocket server;
    public static void main(String[] args) {
//        Load database
        DataBase dataBase = new txtDataBase();
        dataBase.Load();

//        Show information
        ASCIIart();

//        A thread wait to stop server
        Thread inputThread = new Thread(() -> {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
                System.out.println("Type \"exit\" for stop server");
                while (true) {
                    String input = reader.readLine(); // Đọc dòng từ bàn phím
                    if ("exit".equalsIgnoreCase(input)) {
                        server.close(); // Cập nhật giá trị biến stop
                        System.out.println("Saved data and stop server");
                        break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        inputThread.start();

        try {
            server = new ServerSocket(9911);
            while (true){
                Socket s = server.accept();
                new Subclient_imp(s).start();
            }
        } catch (IOException e) {
            dataBase.Save();
            System.exit(1);
        }
    }

    public static void ASCIIart(){
        System.out.println("███╗   ███╗██╗███╗   ██╗██╗ ██████╗██╗  ██╗ █████╗ ████████╗██████╗  ██████╗  ██████╗ ███╗   ███╗");
        System.out.println("████╗ ████║██║████╗  ██║██║██╔════╝██║  ██║██╔══██╗╚══██╔══╝██╔══██╗██╔═══██╗██╔═══██╗████╗ ████║");
        System.out.println("██╔████╔██║██║██╔██╗ ██║██║██║     ███████║███████║   ██║   ██████╔╝██║   ██║██║   ██║██╔████╔██║");
        System.out.println("██║╚██╔╝██║██║██║╚██╗██║██║██║     ██╔══██║██╔══██║   ██║   ██╔══██╗██║   ██║██║   ██║██║╚██╔╝██║");
        System.out.println("██║ ╚═╝ ██║██║██║ ╚████║██║╚██████╗██║  ██║██║  ██║   ██║   ██║  ██║╚██████╔╝╚██████╔╝██║ ╚═╝ ██║");
        System.out.println("╚═╝     ╚═╝╚═╝╚═╝  ╚═══╝╚═╝ ╚═════╝╚═╝  ╚═╝╚═╝  ╚═╝   ╚═╝   ╚═╝  ╚═╝ ╚═════╝  ╚═════╝ ╚═╝     ╚═╝");
        try {
            // Lấy địa chỉ IP của máy
            InetAddress inetAddress = InetAddress.getLocalHost();
            // In ra địa chỉ IP
            System.out.println("IP : " + inetAddress.getHostAddress());
            System.out.println("Port: 9911");
        } catch (UnknownHostException e) {
            // Xử lý ngoại lệ nếu không thể lấy địa chỉ IP
            System.out.println("Cant get IP");
            System.exit(1);
        }
    }
}
