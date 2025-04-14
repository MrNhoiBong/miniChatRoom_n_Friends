package ChatRoom_server.Implement;

import ChatRoom_server.Interface.ChainCmd;
import ChatRoom_server.Interface.SubClient;

import java.io.*;
import java.net.Socket;

public class Subclient_imp extends SubClient {
    private User user;
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
            ChainCmd handle_registerCmd = new SplitCmd(new RegisterCmd());

            while(!result_login.isAccept()){
                requset = listenRq.readLine();
                switch (requset.split(":")[0].toLowerCase()){
                    case "login":
                        handle_loginCmd.Hanlde(requset);
                        if (result_login.isAccept()){
                            sendSmg.println("true");
                            sendSmg.flush();
                            user = result_login.getUser();
                        }
                        else {
                            sendSmg.println("false");
                            sendSmg.flush();
                        }
                        break;
                    case "register":
                        handle_registerCmd.Hanlde(requset);
                        sendSmg.println("true");
                        sendSmg.flush();
                        break;
                    default:
                        System.out.print("Invaild command: ");
                        break;
                }
            }

            while ((requset = listenRq.readLine()) != null){
                switch (requset.split(":")[0].toLowerCase()){
                    case "logout":
                        System.out.println("Logout cmd");
                        break;
                    default:
                        System.out.println("Invaild command");
                }
            }
        } catch (IOException e) {
            System.out.println(user.getName() + ": Logout");
        }

    }
}
