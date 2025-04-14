package ChatRoom_client;

public class ChatClientRunner {
    public static void main(String[] args) {
        Client client = new Client("Client1");

        String host = "localhost";
        int port = 9911;
        if (!client.connect(host, port)) {
            System.out.println("Can't connect to the server");
        }

        // Login
        Login login = new Login("User1", "Password@123", client.getSocket());
        if (!login.login()) {
            System.out.println("Login failed. Exiting...");
            return;
        }
        System.out.println("Login Successful!");
    }


}
