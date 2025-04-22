package ChatRoom_client.GUIChatRoom;

import ChatRoom_client.RunGUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.*;

public class RegisterControls {
    @FXML
    private TextField username;
    @FXML
    private TextField password;
    @FXML
    private TextField confirmpw;
    @FXML
    private Button register;
    @FXML
    private Label fail_reg;

    @FXML
    private void register_action(ActionEvent event) throws IOException {
        BufferedReader readMSG =
                new BufferedReader( new InputStreamReader(RunGUI.socket.getInputStream()));
        PrintWriter sendMSG =
                new PrintWriter( new OutputStreamWriter(RunGUI.socket.getOutputStream()));

        String name = username.getText();
        String pw = password.getText();
        String confirm = confirmpw.getText();
        boolean accept = false;
        if(!pw.equals(confirm)){
            fail_reg.setText("Password mismatched with confirmation password");
            return;
        }

        String registerRequest = "register:" + name + ":" + pw;
        sendMSG.println(registerRequest);
        sendMSG.flush();
        String response = readMSG.readLine();
        if (Boolean.parseBoolean(response)){
            fail_reg.setText("Now you can login");
        }
        else {
            fail_reg.setText("Choose a name not existing");
        }

    }
}
