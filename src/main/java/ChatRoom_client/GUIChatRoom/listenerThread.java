package ChatRoom_client.GUIChatRoom;

import ChatRoom_client.RunGUI;
import javafx.application.Platform;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class listenerThread extends Thread {
    @Override
    public void run() {
        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(RunGUI.socket.getInputStream()))) {
            String message;
            while ((message = in.readLine()) != null) {
                String[] messSplit = message.split(":");
                switch (messSplit[0].toLowerCase()){
                    case "alluser":
                        ChatControls.users_string = messSplit[1];
                        break;
                    case "sendcr":
                        break;
                    case "send":
                        System.out.println(message);
                        String sender = messSplit[1];
                        String mess = messSplit[2];
                        Container_mess_controller chatWindow = Container_mess_controller.name2controller.get(sender);
                        Platform.runLater(() ->{
                            chatWindow.addMess(false, mess);
                        });
                        break;
                    default:
                        System.out.println("Received:"+message);
                }
            }
        } catch (IOException e) {
            System.err.println("Lỗi khi đọc dữ liệu từ socket: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
