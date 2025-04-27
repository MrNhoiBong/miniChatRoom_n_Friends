package ChatRoom_client.GUIChatRoom;

import ChatRoom_client.RunGUI;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;


public class ChatControls {
    public static volatile String users_string = "";
    String nameuser;

    @FXML
    private Button nameUser;

    @FXML
    private TextField chatField;

    @FXML
    private GridPane listUser;

    @FXML
    private ImageView logoutButton;

    @FXML
    private Pane mess_place;

    @FXML
    private ImageView sendButton;

    @FXML
    private Label nameSender;

    @FXML
    void getmess(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            this.sendMess((MouseEvent)null);
        }
    }

    @FXML
    void sendMess(MouseEvent event) {
        String mess = this.chatField.getText();
        if (!mess.equals("")) {
            String current_chatter = Container_mess_controller.current_chat;
            Container_mess_controller chatWindow = (Container_mess_controller)Container_mess_controller.name2controller.get(current_chatter);
            if (chatWindow == null) {
                this.chatField.setText("");
                return;
            }

            chatWindow.addMess(true, mess);

            try {
                PrintWriter sendMSG = new PrintWriter(new OutputStreamWriter(RunGUI.socket.getOutputStream()));
                sendMSG.println("send:" + current_chatter + ":" + mess);
                sendMSG.flush();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            this.chatField.setText("");
        }

    }
    @FXML
    private void mouseClickAction(MouseEvent event) throws IOException {
        PrintWriter sendMSG =
                new PrintWriter( new OutputStreamWriter(RunGUI.socket.getOutputStream()));

        logoutButton.setPickOnBounds(true);
        logoutButton.setOnMouseClicked((MouseEvent e) -> {
            try {
                sendMSG.println("logout");
                sendMSG.flush();

//                RunGUI.listenThread.interrupt();
                RunGUI.socket.close();
                Container_mess_controller.name2controller = new HashMap<>();

                FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("GetIP.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                ChatRoomGUI.prim_stage.setScene(scene);
                ChatRoomGUI.prim_stage.setOnCloseRequest(_ -> {});

            } catch (IOException ex) {
                throw new RuntimeException("Failed to return Login");
            }
        });

    }

    @FXML
    public void refresh_function(MouseEvent event) {
        Socket s = RunGUI.socket;
        BufferedReader readSMG;
        PrintWriter sendSMG;
        try {
            readSMG =
                    new BufferedReader( new InputStreamReader(s.getInputStream()));
            sendSMG =
                    new PrintWriter(new OutputStreamWriter(s.getOutputStream()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        sendSMG.println("alluser");
        sendSMG.flush();
        while (users_string.isEmpty()){}
        String[] users = convertToArray(users_string);
        users_string = "";
        new Container_mess_controller().setName_n_chat(users);
        listUser.getChildren().clear();
        mess_place.getChildren().clear();
        nameSender.setText("");
        Container_mess_controller.current_chat = "";
        int row = 0;
        for (String user:users){
            Label name = new Label(user);
            // Set preferred dimensions: width = 315, height = 150.
            name.setPrefSize(313, 50);

            // Set the font to bold with size = 20.
            name.setFont(Font.font("System", FontWeight.BOLD, 20));

            // Center the text inside the label.
            name.setAlignment(Pos.CENTER);

            // Set the background color using CSS.
            name.setStyle("-fx-background-color: #46474b; -fx-text-fill: white;");

            name.setOnMouseClicked(e -> {
                HashMap<String, ScrollPane> name2chat = Container_mess_controller.name2chat;
                mess_place.getChildren().clear();
                mess_place.getChildren().add(name2chat.get(name.getText()));
                Container_mess_controller.current_chat = name.getText();
                nameSender.setText(name.getText());
            });

            if (!RunGUI.name_user.equals(name.getText())) {
                listUser.add(name, 0, row++);
            }
        }

    }

    String[] convertToArray(String input) {
        // Check if the input is null or too short to have proper brackets.
        if(input == null || input.length() < 2) {
            return new String[0];
        }

        // Remove the leading '[' and trailing ']' characters.
        // This assumes the string starts with '[' and ends with ']'
        String trimmed = input.substring(1, input.length() - 1);

        // If the string is empty after removing brackets, return an empty array.
        if(trimmed.trim().isEmpty()) {
            return new String[0];
        }

        // Split the string on commas.
        String[] elements = trimmed.split(",");

        // Optionally, trim spaces from each element.
        for (int i = 0; i < elements.length; i++) {
            elements[i] = elements[i].trim();
        }

        return elements;
    }

    public void setUsername(String name){
        nameuser = name;
        nameUser.setText(name);
    }

    @FXML
    void chatroomSwitch_function(MouseEvent event) {
        if (ChatroomWindow_control.exists){return;}

        Stage chatWindow = new Stage();
        FXMLLoader loader = new FXMLLoader(
                getClass().getClassLoader().getResource("ChatroomWindow.fxml")
        );
        try {
            System.out.println(getClass().getClassLoader().getResource("ChatroomWindow.fxml"));
            Parent root = loader.load();

            ChatroomWindow_control CRcontrol = loader.getController();
            CRcontrol.setData(nameuser, loader, chatWindow);

            Scene scene = new Scene(root);
            chatWindow.setScene(scene);
            chatWindow.setOnCloseRequest(e -> {
                ChatroomWindow_control.exists = false;
            });
            chatWindow.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
