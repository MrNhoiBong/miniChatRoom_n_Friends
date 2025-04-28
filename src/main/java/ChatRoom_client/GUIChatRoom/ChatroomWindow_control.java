package ChatRoom_client.GUIChatRoom;

import ChatRoom_client.RunGUI;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;

public class ChatroomWindow_control {
    public static boolean exists;
    public static FXMLLoader global_loader;
    public static Stage global_stage;
    String username;
    public static String currentChatroom;

    @FXML
    private GridPane ListofRoom;

    @FXML
    private GridPane chatroom_current;

    @FXML
    private TextField joincr;

    @FXML
    private Pane messengerContain;

    @FXML
    private TextField messengerGet;

    @FXML
    private Label nameCurrentRoom;

    @FXML
    void sendText(KeyEvent event) {
        if (event.getCode() != KeyCode.ENTER){return;}
        if (currentChatroom == null){
            messengerGet.setText("");
            return;
        }
        ChatroomMesscontainer_controller.chatroom_cons.get(currentChatroom).addMess(true, messengerGet.getText());
        try {
            PrintWriter sendMSG = new PrintWriter(
                    new OutputStreamWriter(RunGUI.socket.getOutputStream())
            );

            String sendRq = "sendcr:"+currentChatroom+":"+messengerGet.getText();
            sendMSG.println(sendRq);
            sendMSG.flush();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        messengerGet.setText("");
    }

    @FXML
    void joincr_request(KeyEvent event) throws IOException{
        if (event.getCode() != KeyCode.ENTER){return;}
        if (containsChatroom(joincr.getText())){
            PrintWriter sendSMG =
                    new PrintWriter( new OutputStreamWriter(RunGUI.socket.getOutputStream()));
            sendSMG.println("joincr:" + joincr.getText());
            sendSMG.flush();

            chatroom_current.add(createLabel_chatroom(), 0, chatroom_current.getRowCount());
            new ChatroomMesscontainer_controller().createCRcontain(joincr.getText());
            joincr.setText("");

            return;
        };
        Alert alert = new Alert(Alert.AlertType.WARNING, "Do you want to create a new chatroom?", ButtonType.YES, ButtonType.NO);
        alert.setTitle("Warning");
        alert.setHeaderText(null); // Không hiển thị tiêu đề phụ
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.YES) {
                PrintWriter sendSMG = null;
                try {
                    sendSMG = new PrintWriter( new OutputStreamWriter(RunGUI.socket.getOutputStream()));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                sendSMG.println("joincr:" + joincr.getText());
                sendSMG.flush();
                try {
                    new ChatroomMesscontainer_controller().createCRcontain(joincr.getText());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                chatroom_current.add(createLabel_chatroom(), 0, ListofRoom.getRowCount());
            }
        });
        joincr.setText("");
    }

    void refreshChatroom(String listChatroom_string){
        ListofRoom.getChildren().clear();
        System.out.println(listChatroom_string);
        String[] result = listChatroom_string.replace("[", "").replace("]", "").split("\\s*,\\s*");

        for (String chatroom : result) {
            Label namecr = new Label();

            namecr.setPrefSize(191, 150); // Đặt kích thước width = 191, height = 150
            namecr.setStyle("-fx-background-color: gray; -fx-text-fill: white; -fx-font-weight: bold;");
            namecr.setAlignment(Pos.CENTER); // Căn giữa chữ trong Label

            namecr.setText(chatroom);
            ListofRoom.add(namecr, 0, ListofRoom.getRowCount());
        }
    }

    public void setData(String username, FXMLLoader loader, Stage stage){
        this.username = username;
        global_loader = loader;
        global_stage = stage;
        exists = true;

        Socket socket = RunGUI.socket;
        try {
            PrintWriter sendSMG =
                    new PrintWriter( new OutputStreamWriter(socket.getOutputStream()));
            sendSMG.println("allcr");
            sendSMG.println("crjoined");
            sendSMG.flush();
        } catch (IOException e) {
            System.out.println("cant connect server");
        }

    }

    private boolean containsChatroom(String choice_chatroom) {
        for (Node node : ListofRoom.getChildren()) {
            if (node instanceof Label) { // Kiểm tra nếu Node là Label
                Label label = (Label) node;
                if (label.getText().equals(choice_chatroom)) {
                    return true; // Tìm thấy nhãn trùng khớp
                }
            }
        }
        return false; // Không tìm thấy nhãn trùng khớp
    }

    private Label createLabel_chatroom(){
        String chatroom = joincr.getText();
        Label namecr = new Label();

        namecr.setPrefSize(191, 150); // Đặt kích thước width = 191, height = 150
        namecr.setStyle("-fx-background-color: gray; -fx-text-fill: white; -fx-font-weight: bold;");
        namecr.setAlignment(Pos.CENTER); // Căn giữa chữ trong Label

        namecr.setText(chatroom);
        namecr.setOnMouseClicked(e -> {
            currentChatroom = namecr.getText(); // Cập nhật biến currentChatroom
            nameCurrentRoom.setText(currentChatroom);
            messengerContain.getChildren().clear();
            messengerContain.getChildren().add(
                    ChatroomMesscontainer_controller.chatroomContain.get(currentChatroom)
            );
        });
        return namecr;
    }

    private Label createLabel_chatroom(String chatroom){
        Label namecr = new Label();

        namecr.setPrefSize(191, 150); // Đặt kích thước width = 191, height = 150
        namecr.setStyle("-fx-background-color: gray; -fx-text-fill: white; -fx-font-weight: bold;");
        namecr.setAlignment(Pos.CENTER); // Căn giữa chữ trong Label

        namecr.setText(chatroom);
        namecr.setOnMouseClicked(e -> {
//            System.out.println(ChatroomMesscontainer_controller.chatroomContain);
            currentChatroom = namecr.getText(); // Cập nhật biến currentChatroom
            nameCurrentRoom.setText(currentChatroom);
            messengerContain.getChildren().clear();
            messengerContain.getChildren().add(
                    ChatroomMesscontainer_controller.chatroomContain.get(currentChatroom)
            );
            namecr.setStyle("-fx-background-color: gray; -fx-text-fill: white; -fx-font-weight: bold;");
        });
        return namecr;
    }

    void refreshjoinCr(String listChatroom_string){
        chatroom_current.getChildren().clear();
        String[] result = listChatroom_string.replace("[", "").replace("]", "").split("\\s*,\\s*");

        for (String chatroom : result) {
            try {
                new ChatroomMesscontainer_controller().createCRcontain(chatroom);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            chatroom_current.add(createLabel_chatroom(chatroom), 0, chatroom_current.getRowCount());
//            System.out.println(createLabel_chatroom(chatroom).getOnMouseClicked());
        }
    }
}
