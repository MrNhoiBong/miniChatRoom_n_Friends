package ChatRoom_client;

import ChatRoom_client.GUIChatRoom.ChatRoomGUI;

import java.io.IOException;
import java.net.Socket;

public class RunGUI {
    public static Socket socket;
    public static void main(String[] args) {
//        try{
//            socket = new Socket("192.168.1.3", 9911);
//        } catch (IOException e) {
//            throw new RuntimeException("Failed to connect to server: " + e.getMessage(), e);
//        }
        ChatRoomGUI.main(args);
    }

}
