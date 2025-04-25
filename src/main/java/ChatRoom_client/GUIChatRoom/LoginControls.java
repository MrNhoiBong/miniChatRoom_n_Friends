package ChatRoom_client.GUIChatRoom;

import ChatRoom_client.RunGUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.*;

public class LoginControls {
    public static FXMLLoader globalLoader;
    @FXML
    private TextField usernameField;
    @FXML
    private TextField passwordField;
    @FXML
    private Label statusLabel;
    @FXML
    private Button loginButton;
    @FXML
    private Label fail_respone;

    @FXML
    private void Signin(ActionEvent event) throws IOException {
        BufferedReader readMSG =
                new BufferedReader( new InputStreamReader(RunGUI.socket.getInputStream()));
        PrintWriter sendMSG =
                new PrintWriter( new OutputStreamWriter(RunGUI.socket.getOutputStream()));

        String name = usernameField.getText();
        String pw = passwordField.getText();
        String loginRequest = "login:"+name+":"+pw;

        sendMSG.println(loginRequest);
        sendMSG.flush();
        String respone = readMSG.readLine();
        if (Boolean.parseBoolean(respone)){
            globalLoader = new FXMLLoader(getClass().getClassLoader().getResource("Chatting.fxml"));
            Parent root = globalLoader.load();
            Scene scene = new Scene(root);
            ChatRoomGUI.prim_stage.setScene(scene);
            ChatRoomGUI.prim_stage.setOnCloseRequest(e ->{
                try {
                    PrintWriter  logoutRq=
                            new PrintWriter(new OutputStreamWriter(RunGUI.socket.getOutputStream()));
                    logoutRq.println("logout");
                    logoutRq.flush();
                    RunGUI.socket.close();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });
            RunGUI.name_user = name;
            RunGUI.listenThread = new listenerThread();
            RunGUI.listenThread.start();
            ((ChatControls) globalLoader.getController()).setUsername(name);
            ((ChatControls) globalLoader.getController()).refresh_function(null);
        }
        else {
            fail_respone.setText("Incorrect user information or unregistered user details entered");
        }

    }
}
