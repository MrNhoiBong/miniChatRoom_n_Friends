package ChatRoom_client.GUIChatRoom;

import ChatRoom_client.RunGUI;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class GetIPControls {

    @FXML
    private TextField ip_field;

    @FXML
    void gettext(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER){
            RunGUI.ip = ip_field.getText();
            try {
                RunGUI.socket = new Socket(RunGUI.ip, 9911);

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                scene.setFill(Color.TRANSPARENT);
                ChatRoomGUI.prim_stage.setScene(scene);
                ChatRoomGUI.prim_stage.setTitle("Chat Room Application");
                ChatRoomGUI.prim_stage.show();
            } catch (IOException e) {
                ip_field.setText("");
            }
        }
    }

}
