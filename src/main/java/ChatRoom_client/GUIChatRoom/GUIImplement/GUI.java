package ChatRoom_client.GUIChatRoom.GUIImplement;

import java.io.IOException;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;
import javafx.util.Duration;


public class GUI  {
    private static final Logger LOGGER = Logger.getLogger(GUI.class.getName());

    @FXML
    private VBox vbox = new VBox();
    private Parent fxml;

    @FXML
    private void initialize() {
        loadFxml("Login.fxml", vbox.getLayoutX() * 20);
    }

    @FXML
    private void open_signin(ActionEvent event) {
        loadFxml("Login.fxml", vbox.getLayoutX() * 20);
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
