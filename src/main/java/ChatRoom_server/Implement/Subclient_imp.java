package ChatRoom_server.Implement;

import ChatRoom_server.Interface.ChainCmd;
import ChatRoom_server.Interface.DataBase;
import ChatRoom_server.Interface.SubClient;

import java.io.*;
import java.net.Socket;

public class Subclient_imp extends SubClient {
    private User user;
    private Socket s;

    public Subclient_imp(Socket s) {
        this.s = s;
        System.out.println(s);
    }

    @Override
    public void run() {
        DataBase dataBase = new txtDataBase();
        try {
            String requset = null;
            BufferedReader listenRq =
                    new BufferedReader( new InputStreamReader(s.getInputStream()));
            PrintWriter sendSmg =
                    new PrintWriter( new OutputStreamWriter(s.getOutputStream()));

            LoginCmd result_login = new LoginCmd(s);
            RegisterCmd result_register = new RegisterCmd(s);
            ChainCmd handle_loginCmd = new SplitCmd(result_login);
            ChainCmd handle_registerCmd = new SplitCmd(result_register);

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
                        if (result_register.isAccept()) {
                            sendSmg.println("true");
                        }
                        else {
                            sendSmg.println("false");
                        }
                        sendSmg.flush();
                        break;
                    default:
                        System.out.print("Invaild command: ");
                        break;
                }
            }

            System.out.println(user.getName()+" login");

            while ((requset = listenRq.readLine()) != null){
                System.out.println(requset);
                switch (requset.split(":")[0].toLowerCase()){
                    case "send":
                        SendCmd sendCmd = new SendCmd();
                        new SplitCmd(sendCmd).Hanlde(requset);
                        sendSmg.println(sendCmd.getResult());
                        System.out.println(sendCmd.getResult());
                        break;
                    case "joincr":
                        System.out.println("use join Chatroom");
                        break;
                    case "sendcr":
                        System.out.println("use send chatroom");
                        break;
                    case "logout":
                        System.out.println("use logout");
                        break;
                    default:
                        System.out.println("Invaild command");
                }
            }
        } catch (IOException e) {
            System.out.println(user.getName() + " Logout");
        }

    }
}
