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
            ChainCmd handle_registerCmd = new SplitCmd(new RegisterCmd());

            while(result_login.isAccept()){
                String loginCmd = listenRq.readLine();
                switch (requset.split(":")[0].toLowerCase()){
                    case "login":
                        handle_loginCmd.Hanlde(loginCmd);
                        if (result_login.isAccept()){
                            sendSmg.println("Welcome!!");
                            sendSmg.flush();
                            System.out.println("Accept login");
                        }
                        break;
                    case "register":
                        handle_registerCmd.Hanlde(loginCmd);
                        break;
                    default:
                        System.out.println("Invaild command");
                }
            }
//            handle_loginCmd.Hanlde(loginCmd);
//            while ((!result_login.isAccept())){
//                sendSmg.println("false");
//                sendSmg.flush();
//                System.out.println("Refuse login");
//
//            }
//            System.out.println("Accept login");


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
