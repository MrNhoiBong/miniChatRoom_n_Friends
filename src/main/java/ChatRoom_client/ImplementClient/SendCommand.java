package ChatRoom_client.ImplementClient;

import java.io.*;
import java.net.*;

public class SendCommand {
    private Socket s;

    public SendCommand(Socket s) {
        this.s = s;
    }

    // Getters and setters
    public Socket getSocket() {
        return s;
    }

    public void setSocket(Socket s) {
        this.s = s;
    }

    public void sendCmd(String command) {
        try {
            PrintWriter out = new PrintWriter(s.getOutputStream(), true);
            out.println(command);
        } catch (Exception e) {
            System.out.println("Error sending command: " + e.getMessage());
        }
    }
}
