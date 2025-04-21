package ChatRoom_client.GUIChatRoom;

import ChatRoom_client.ImplementClient.Login;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class LoginControls {
    @FXML
    private TextField usernameField;
    @FXML
    private TextField passwordField;
    @FXML
    private Label statusLabel;
    @FXML
    private Button loginButton;

    @FXML
    private void Signin(ActionEvent event) {
        System.out.println("Sign in");
    }
}
