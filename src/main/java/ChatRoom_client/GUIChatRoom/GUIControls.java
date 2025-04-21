package ChatRoom_client.GUIChatRoom;

import ChatRoom_client.ImplementClient.Login;
import java.io.IOException;
import java.net.Socket;
import java.util.Spliterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;
import javafx.util.Duration;


public class GUIControls {
    private static final Logger LOGGER = Logger.getLogger(GUIControls.class.getName());

    @FXML
    private VBox vbox;
    private Parent fxml;

//    private final Login login;
//
//    public GUIControls() {
//        try{
//            Socket socket = new Socket("192.168.80.3", 9911);
//            this.login = new Login(socket);
//        } catch (IOException e) {
//            throw new RuntimeException("Failed to connect to server: " + e.getMessage(), e);
//        }
//    }

    @FXML
    private void initialize() {
        loadFxml("Login.fxml", vbox.getLayoutX() * 20);
    }


    @FXML
    private void open_signin(ActionEvent event) {
        loadFxml("Login.fxml", vbox.getLayoutX() * 20);
        System.out.println("Open login");
    }

    @FXML
    private void open_signup(ActionEvent event) {
        loadFxml("RegisterGUI.fxml", 0);
    }

    private void loadFxml(String fxmlPath, double translateX) {
        TranslateTransition transition = new TranslateTransition(Duration.seconds(1), vbox);
        transition.setToX(translateX);
        transition.play();
        transition.setOnFinished((e) -> {
            try {
                fxml = FXMLLoader.load(getClass().getClassLoader().getResource(fxmlPath));
                vbox.getChildren().clear();
                vbox.getChildren().setAll(fxml);
            } catch (IOException ex) {
                LOGGER.log(Level.SEVERE, "Error loading FXML file: " + fxmlPath, ex);
            }
        });
    }
}