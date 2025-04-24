package ChatRoom_client;

import ChatRoom_client.GUIChatRoom.ChatRoomGUI;

import java.net.Socket;


public class RunGUI {
    public static Socket socket;
    public static String name_user;
    public static String ip;
    public static Thread listenThread;
    public static void main(String[] args) {
        ChatRoomGUI.main(args);
    }

}
