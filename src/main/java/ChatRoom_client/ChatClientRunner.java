package ChatRoom_client;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ChatClientRunner {
    public static void main(String[] args) {
        Client client = new Client("Client1");
        Scanner scanner = new Scanner(System.in);

        String host = "localhost";
        int port = 9911;
        if (!client.connect(host, port)) {
            System.out.println("Failed to connect to the server. Exiting...");
            scanner.close();
            return;
        }

        // Login
        Login login = new Login(client.getSocket());
        if (!login.login()) {
            System.out.println("Login failed after retries. Exiting...");
            scanner.close();
            return;
        }
        System.out.println("Login Successful!");

        // Get the username from Login
        String username = login.getUserName();

        // Start a thread to listen for server messages
        Thread receiverThread = new Thread(() -> {
            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(client.getSocket().getInputStream()));
                String message;
                while ((message = in.readLine()) != null) {
                    // Parse messages of the form "sender: message"
                    if (message.contains(":")) {
                        String[] parts = message.split(":", 2);
                        String sender = parts[0];
                        String content = parts[1];
                        System.out.println(sender + ": " + content);
                    } else {
                        System.out.println("Received: " + message);
                    }
                }
            } catch (Exception e) {
                System.out.println("Error receiving message: " + e.getMessage());
            }
        });
        receiverThread.start();

        // Handle user input and send commands in the main thread
        SendCommand sender = new SendCommand(client.getSocket());
        System.out.println("Enter messages to send (type 'exit' to quit, or '/msg <user> <message>' for direct messaging):");

        while (true) {
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("exit")) {
                break;
            }

            // Parse user input for commands
            if (input.startsWith("/msg")) {
                String[] parts = input.split(" ", 3);
                if (parts.length < 3) {
                    System.out.println("Usage: /msg <username> <message>");
                    continue;
                }
                String recipient = parts[1];
                String message = parts[2];
                System.out.println("Send:" + username + ":" + recipient + ":" + message);
                sender.sendCmd("Send:" + username + ":" + recipient + ":" + message);
            } else {
                // Default: broadcast message to all
                System.out.println("Send:" + username + ":ALL:" + input);
                sender.sendCmd("Send:" + username + ":ALL:" + input);
            }
        }

        // Cleanup
        scanner.close();
        try {
            client.getSocket().close();
        } catch (Exception e) {
            System.out.println("Error closing socket: " + e.getMessage());
        }
    }
}
