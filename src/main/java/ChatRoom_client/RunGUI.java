package ChatRoom_client;

import ChatRoom_client.GUIChatRoom.ChatRoomGUI;
import ChatRoom_client.GUIChatRoom.GetIP_run;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class RunGUI {
    public static Socket socket;
    public static String name_user;
    public static String ip;
    public static Thread listenThread;
    public static void main(String[] args) {
        ChatRoomGUI.main(args);
    }

}
