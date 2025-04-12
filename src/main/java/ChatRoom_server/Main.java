package ChatRoom_server;

import java.io.IOException;
import java.net.ServerSocket;

public class Main {
    public static void main(String[] args) {
        try {
            ServerSocket s = new ServerSocket(9911);
            while (true){
                s.accept();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
