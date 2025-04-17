package ChatRoom_client;

import java.io.*;


public class ChatClientRunner {
    public static void main(String[] args) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));


            Client client = new Client("Client1");

//            String host = "192.168.80.116";
            String host = "localhost";
            int port = 9911;
            if (!client.connect(host, port)) {
                System.out.println("Failed to connect to the server. Exiting...");
                return;
            }
        while (true) {
            // Login
            Login login = new Login(client.getSocket());
            if (!login.login()) {
                System.out.println("Login failed after retries. Exiting...");
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
                    System.out.println("Server disconnected: Connection closed.");
                    System.exit(1);
                } catch (IOException e) {
                    System.out.println("\nError receiving message: " + e.getMessage());
                    System.exit(1);
                }
            });
            receiverThread.start();

            // Handle user input and send commands in the main thread
            SendCommand sender = new SendCommand(client.getSocket());
            System.out.println("Enter messages in the format 'Send:<your_username>:<recipient>:<message>' (e.g., 'send:" + username + ":Long:Hello').");
            System.out.println("Use 'send:" + username + ":ALL:<message>' to broadcast to all. Type 'logout' to quit.");

            while (true) {
                String input;
                try {
                    input = reader.readLine(); // Đọc dòng đầu vào bằng BufferedReader
                } catch (IOException e) {
                    System.out.println("Error reading input: " + e.getMessage());
                    break;
                }

                if (input != null && input.equalsIgnoreCase("logout")) {
                    try {
                        sender.sendCmd("logout");
                    } catch (Exception e) {
                        System.out.println("Error sending logout message: " + e.getMessage());
                    }
                    break;
                }

                if (input == null || input.isEmpty()) {
                    System.out.println("Message cannot be empty. Please try again.");
                    continue;
                }
                sender.sendCmd(input);
//            String recipient;
//            String message;
//
//            // Kiểm tra xem có phải tin nhắn riêng (bắt đầu bằng @) không
//            if (input.startsWith("@")) {
//                // Tách recipient và message
//                int firstSpace = input.indexOf(" ");
//                if (firstSpace == -1 || firstSpace == input.length() - 1) {
//                    System.out.println("Invalid format for direct message. Use '@<recipient> <message>'.");
//                    continue;
//                }
//
//                recipient = input.substring(1, firstSpace); // Lấy recipient (bỏ @)
//                message = input.substring(firstSpace + 1).trim(); // Lấy message
//
//                if (recipient.isEmpty() || message.isEmpty()) {
//                    System.out.println("Recipient and message cannot be empty. Use '@<recipient> <message>'.");
//                    continue;
//                }
//            } else {
//                // Tin nhắn phát sóng
//                recipient = "All";
//                message = input;
//            }
//
//            // Hiển thị tin nhắn theo định dạng Send:<username>:<recipient>:<message>
//            System.out.println("Send:" + username + ":" + recipient + ":" + message);
//
//            // Gửi tin nhắn đến server
//            try {
//                sender.sendCmd("send:" + recipient + ":" + message);
//            } catch (Exception e) {
//                System.out.println("Error sending message: " + e.getMessage());
//                break;
//            }
//        }

                // Cleanup
//            scanner.close();
        }
            System.out.println("Logged out successfully. Returning to login screen...");
        }
    }
}
