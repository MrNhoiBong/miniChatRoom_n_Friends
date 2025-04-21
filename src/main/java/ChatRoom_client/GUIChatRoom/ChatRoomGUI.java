package ChatRoom_client.GUIChatRoom;

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
    static Stage prim_stage;
//    private static final Logger LOGGER = Logger.getLogger(ChatRoomGUI.class.getName());
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI.fxml"));
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Chatting.fxml"));

//            System.out.println(getClass().getResource("/GUI.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            scene.setFill(Color.TRANSPARENT);
//
            stage.setScene(scene);
            prim_stage = stage;
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.setTitle("Chat Room Application");
//            LOGGER.info("Starting application with transparent stage");
            stage.show();
        } catch (IOException e) {
//            LOGGER.log(Level.SEVERE, "Error loading application", e);
            throw new RuntimeException("Failed to start application", e);
        }
    }
}