package ChatRoom_client.GUIChatRoom.GUIImplement;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ChatRoomGUI extends Application {

    private static final Logger LOGGER = Logger.getLogger(ChatRoomGUI.class.getName());
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        try {
            // Check resource link
            String fxmlPath = "GUI.fxml";
            java.net.URL fxmlUrl = getClass().getResource(fxmlPath);
            if (fxmlUrl == null) {
                LOGGER.log(Level.SEVERE, "FXML file not found: {0}", fxmlPath);
                throw new IOException("Cannot find " + fxmlPath);
            }

            LOGGER.info("Loading FXML file: " + fxmlPath);
            Parent root = FXMLLoader.load(fxmlUrl);

            Scene scene = new Scene(root);
            scene.setFill(Color.TRANSPARENT);

            stage.setScene(scene);
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.setTitle("Chat Room Application");
            LOGGER.info("Starting application with transparent stage");
            stage.show();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error loading application", e);
            throw new RuntimeException("Failed to start application", e);
        }
    }
}