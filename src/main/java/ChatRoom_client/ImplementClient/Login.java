package ChatRoom_client.ImplementClient;

import ChatRoom_client.Interface.InterfaceLogin;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Login implements InterfaceLogin {
    private String userName;
    private String passWord;
    private Socket s;


    public Login(Socket s) {
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
    public void sendLoginRequest(String request) {
        try {
            PrintWriter out = new PrintWriter(s.getOutputStream(), true);
            out.println(request);
        } catch (Exception e) {
            System.out.println("Error sending request: " + e.getMessage());
        }
    }


    // Received login response from the server
    public String receivedLoginResponse() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            return in.readLine();
        } catch (Exception e) {
            System.out.println("Error receiving login response: " + e.getMessage());
            return null;
        }
    }

    // Perform login
    public boolean login() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {

            while (true) {
                // Display menu for login/register
                System.out.println("--- Welcome ---");
                System.out.println("1. Login");
                System.out.println("2. Register");
                System.out.print("Choose an option (1 or 2): ");
                String choice = reader.readLine().trim();

                // Prompt for credentials
                System.out.print("Enter username: ");
                this.userName = reader.readLine().trim();
                System.out.print("Enter password: ");
                this.passWord = reader.readLine().trim();

                // Validate input
                if (userName.isEmpty() || passWord.isEmpty()) {
                    System.out.println("Username and password cannot be empty. Please try again.");
                    continue;
                }

                String request;
                switch (choice) {
                    case "1": // Login
                        request = "LOGIN:" + userName + ":" + passWord;
                        sendLoginRequest(request);
                        String loginResponse = receivedLoginResponse();

                        if (loginResponse == null) {
                            System.out.println("No response from server. Exiting...");
                            return false;
                        }

                        if (loginResponse.equals("LOGIN_SUCCESS") || loginResponse.equals("true")) {
                            System.out.println("Successfully logged in! The client has been registered");
                            return true; // Exit the loop and method
                        } else if (loginResponse.equals("USER_EXISTS")) {
                            System.out.println("Username '" + userName + "' does not exist or password is incorrect. Please try again.");
                        } else {
                            System.out.println("Login failed: " + loginResponse + ". Please try again.");
                        }
                        break;

                    case "2": // Register
                        request = "REGISTER:" + userName + ":" + passWord;
                        sendLoginRequest(request);
                        String registerResponse = receivedLoginResponse();

                        if (registerResponse == null) {
                            System.out.println("No response from server. Exiting...");
                            return false;
                        }

                        if (registerResponse.equals("REGISTER_SUCCESS") || registerResponse.equals("true")) {
                            System.out.println("Register Successfull! Now logging in...");
                            // Automatically attempt to login after successful registration
                            request = "LOGIN:" + userName + ":" + passWord;
                            sendLoginRequest(request);
                            String autoLoginResponse = receivedLoginResponse();

                            if (autoLoginResponse != null && (autoLoginResponse.equals("LOGIN_SUCCESS") || autoLoginResponse.equals("true"))) {
                                System.out.println("Successfully logged in! The client has been registered");
                                return true; // Exit the loop and method
                            } else {
                                System.out.println("Auto-login failed after registration: " + autoLoginResponse + ". Please try logging in manually.");
                            }
                        } else if (registerResponse.equals("USER_EXISTS")) {
                            System.out.println("Username '" + userName + "' already exists. Please choose a different username.");
                        } else {
                            System.out.println("Registration failed: " + registerResponse + ". Please try again.");
                        }
                        break;

                    default:
                        System.out.println("Invalid choice. Please enter 1 for Login or 2 for Register.");
                        continue;
                }

                // Prompt to try again if login/register fails
                System.out.println("--- Try Again ---");
            }
        } catch (Exception e) {
            System.out.println("Error reading input: " + e.getMessage());
            return false;
        }
    }


}

