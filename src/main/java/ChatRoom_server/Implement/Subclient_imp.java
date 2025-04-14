package ChatRoom_server.Implement;

import ChatRoom_server.Interface.ChainCmd;
import ChatRoom_server.Interface.SubClient;

import java.io.*;
import java.net.Socket;

public class Subclient_imp extends SubClient {
    private String name;
    private Socket s;

    public Subclient_imp(Socket s) {
        this.s = s;
    }

    @Override
    public void run() {
        try {
            String requset = null;
            BufferedReader listenRq =
                    new BufferedReader( new InputStreamReader(s.getInputStream()));
            PrintWriter sendSmg =
                    new PrintWriter( new OutputStreamWriter(s.getOutputStream()));

            LoginCmd result_login = new LoginCmd();
            ChainCmd handle_loginCmd = new SplitCmd(result_login);

            while ((!result_login.isAccept())||(true)){
                String loginCmd = listenRq.readLine();
                handle_loginCmd.Hanlde(loginCmd);
                sendSmg.println("false");
                sendSmg.flush();
                System.out.println("Refuse login");
            }

            while ((requset = listenRq.readLine()) != null){
                switch (requset.split(":")[0].toLowerCase()){
                    case "logout":
                        System.out.println("logout cmd");
                        break;
                    default:
                        System.out.println("Invaild command");
                }
            }
        } catch (IOException e) {
            System.out.println(name + ": Logout");
        }

    }
}
