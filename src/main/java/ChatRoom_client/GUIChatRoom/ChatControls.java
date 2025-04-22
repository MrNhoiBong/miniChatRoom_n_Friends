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
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.io.PrintWriter;


public class ChatControls {
    @FXML
    private ImageView logoutButton;
    @FXML
    private TextField searchField;
    @FXML
    private Label nameLabel;
    @FXML
    private TextField chatField;
    @FXML
    private ImageView sendButton;
    @FXML
    private Pane chatContentPane;

    @FXML
    private void chatAction(ActionEvent event) {

    }

    @FXML
    private void mouseClickAction(MouseEvent event) {
        logoutButton.setPickOnBounds(true);
        logoutButton.setOnMouseClicked((MouseEvent e) -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("GUI.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                ChatRoomGUI.prim_stage.setScene(scene);
            } catch (IOException ex) {
                throw new RuntimeException("Failed to return Login");
            }

        });

    }
}
