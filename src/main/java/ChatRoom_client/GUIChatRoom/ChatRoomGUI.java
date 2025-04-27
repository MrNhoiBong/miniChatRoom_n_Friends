package ChatRoom_client.GUIChatRoom;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
        prim_stage = stage;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GetIP.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.initStyle(StageStyle.DECORATED);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException("Failed to start application", e);
        }
    }
}