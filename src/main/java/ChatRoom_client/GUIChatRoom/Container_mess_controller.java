package ChatRoom_client.GUIChatRoom;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;

public class Container_mess_controller {
    public static HashMap<String, Container_mess_controller> name2controller = new HashMap<>();
    public static HashMap<String, ScrollPane> name2chat = new HashMap<>();
    public static String current_chat;

    public void setName_n_chat(String[] users){
        java.util.List<String> userList = Arrays.asList(users);

        // Remove entries not present in the users array.
        Iterator<String> iterator = name2controller.keySet().iterator();
        while (iterator.hasNext()) {
            String name = iterator.next();
            if (!userList.contains(name)) {
                iterator.remove();
            }
        }
        Iterator<String> iterator2 = name2controller.keySet().iterator();
        while (iterator2.hasNext()) {
            String name = iterator2.next();
            if (!userList.contains(name)) {
                iterator2.remove();
            }
        }

        // Add a new ScrollPane for each user in the users array that is missing from name2controller.
        for (String user : users) {
            if (!name2controller.containsKey(user)) {
                FXMLLoader loader = new FXMLLoader(
                        getClass().getClassLoader().getResource(
                                "Container_mess.fxml"
                        )
                );
                try {
                    ScrollPane chatWindow = loader.load();

                    name2chat.put(user, chatWindow);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                Container_mess_controller chatcontroller =
                        loader.getController();
                name2controller.put(user, chatcontroller);
            }
        }
    }

    @FXML
    private GridPane mess;

    public void addMess(boolean owner, String messText){
        Label mess_context = new Label();
        // Enable text wrapping
        mess_context.setWrapText(true);

        // Set the maximum width to 300 pixels
        mess_context.setMaxWidth(300);

        // Set the background color to gray using inline CSS
        mess_context.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-padding: 10;");

        int col = 0;
        int row = mess.getRowCount();
        if (owner){
            col = 1;
            mess_context.setAlignment(Pos.CENTER_RIGHT);
        }
        mess_context.setText(messText);
        mess.add(mess_context, col, row);
        mess.addRow(1);
    }

}
