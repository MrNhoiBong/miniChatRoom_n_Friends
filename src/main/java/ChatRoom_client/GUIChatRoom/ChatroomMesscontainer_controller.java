package ChatRoom_client.GUIChatRoom;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.util.HashMap;

public class ChatroomMesscontainer_controller {
    public static HashMap<String, ChatroomMesscontainer_controller> chatroom_cons = new HashMap<>();
    public static HashMap<String, ScrollPane> chatroomContain = new HashMap<>();
    @FXML
    private GridPane messContainCR;

    public void createCRcontain(String namechatroom) throws IOException {
        if (chatroom_cons.get(namechatroom) != null){return;}
        FXMLLoader loader = new FXMLLoader(
            getClass().getClassLoader().getResource("Chatroom_messcontainer.fxml")
        );
        ScrollPane chatroom_mess_wd = loader.load();
        chatroom_cons.put(namechatroom ,loader.getController());
        chatroomContain.put(namechatroom, chatroom_mess_wd);
    }

    public void addMess(boolean owner, String messText){
        Label mess_context = new Label();
        // Enable text wrapping
        mess_context.setWrapText(true);

        // Set the maximum width to 300 pixels
        mess_context.setMaxWidth(300);

        // Set the background color to gray using inline CSS
        mess_context.setStyle(
                "-fx-background-color: #00FFFF;" +
                "-fx-background-radius: 20px;" +
                "-fx-text-fill: white; " +
                "-fx-padding: 10px;" +
                "-fx-text-fill: black;" +
                "-fx-font-size: 22px;");

        int col = 0;
        int row = messContainCR.getRowCount();
        if (owner){
            col = 1;
            mess_context.setAlignment(Pos.CENTER_RIGHT);
        }
        mess_context.setText(messText);
        messContainCR.add(mess_context, col, row);
        GridPane.setMargin(mess_context, new Insets(5, 0, 5, 0));
        messContainCR.addRow(1);
    }

}
