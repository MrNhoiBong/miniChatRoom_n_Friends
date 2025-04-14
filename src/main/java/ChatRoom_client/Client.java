package ChatRoom_client;

import java.io.*;
import java.net.*;


public class Client {
    private Socket s;
    private String name;

    public Client(String name) {
        this.s = null;
        this.name = name;
    }

    // Connect to server
    public boolean connect(String host, int port ) {
        try {
            s = new Socket(host, port);
            System.out.println("Connected to server: " + host + ": " + port);
            return true;
        } catch (Exception e) {
            System.out.println("Connection failed: " + e.getMessage());
            return false;
        }
    }

    // Setter and getters
    public Socket getSocket() {
        return s;
    }

    public void setSocket(Socket s) {
        this.s = s;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
