package ChatRoom_client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

public class Login  {
    private String userName;
    private String passWord;
    private Socket s;

    public Login (String userName, String passWord, Socket s) {
        this.userName = userName;
        this.passWord = passWord;
        this.s = s;
    }

    // Getters and Setters
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public Socket getSocket() {
        return s;
    }

    public void setSocket(Socket s) {
        this.s = s;
    }

    // Send login request to the server
    public void sendLoginRequest() {
        try {
            PrintWriter out = new PrintWriter(s.getOutputStream(), true);
            out.println("Login:" + userName + ":" + passWord);

        } catch (Exception e) {
            System.out.println("Error to sending login request" + e.getMessage());
        }
    }

    // Received login response from the server
    public String receivedLoginResponse() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            return in.readLine();
        } catch (Exception e) {
            System.out.println("Error receiving login response: " + e.getMessage());;
            return null;
        }
    }

    // Perform login
    public boolean login() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            sendLoginRequest();
            String response = receivedLoginResponse();

            if (response == null) {
                System.out.println("No response from the server. Exiting...");
                scanner.close();
                return false;
            }

            if (response.equals("LOGIN_SUCCESS")) {
                System.out.println("Login successful!");
                scanner.close();
                return true;
            } else if (response.equals("USERS_EXISTS")) {
                System.out.println("Username '" + userName + "' already exists. Please try a different username.");
            } else {
                System.out.println("Login failed: " + response + " . Please try again.");
            }

            // Prompt for new credentials
            System.out.print("Enter new username: ");
            this.userName = scanner.nextLine();
            System.out.print("Enter new password: ");
            this.passWord = scanner.nextLine();
        }
    }

}
