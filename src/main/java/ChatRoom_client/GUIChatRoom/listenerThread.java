package ChatRoom_client.GUIChatRoom;

import ChatRoom_client.RunGUI;
import javafx.application.Platform;

import java.io.*;

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
                        String sender_crRq = messSplit[1];
                        String chatroom_crRq = messSplit[2];
                        String mess_crRq = messSplit[3];
                        ChatroomMesscontainer_controller chatroomWindow =
                                ChatroomMesscontainer_controller.chatroom_cons.get(chatroom_crRq);
                        Platform.runLater(() -> {
                            chatroomWindow.addMess(false, sender_crRq+":"+mess_crRq);
                        });
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
                    case "newlogin", "newlogout":
                        Platform.runLater(() -> {
                            ((ChatControls) LoginControls.globalLoader.getController()).refresh_function(null);
                        });
                        break;
                    case "newchatroom", "allcr":
                        if (messSplit[1].length() > 2) {
                            Platform.runLater(() -> {
                                ((ChatroomWindow_control) ChatroomWindow_control.global_loader.getController()).refreshChatroom(messSplit[1]);
                            });
                        }
                        break;
                    case "create new chatroom":
                        PrintWriter sendSMG = null;
                        try {
                            sendSMG = new PrintWriter( new OutputStreamWriter(RunGUI.socket.getOutputStream()));
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        sendSMG.println("allcr");
                        sendSMG.flush();
                        break;
                    case "crjoined":
                        if (messSplit[1].length() > 2) {
                            Platform.runLater(() -> {
                                ((ChatroomWindow_control) ChatroomWindow_control.global_loader.getController()).refreshjoinCr(messSplit[1]);
                            });
                        }
                        break;
                    default:
                        System.out.println("Received:"+message);
                }
            }
        } catch (IOException e) {
            System.err.println("Exit");
        }
    }
}
