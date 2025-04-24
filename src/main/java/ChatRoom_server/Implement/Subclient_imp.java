package ChatRoom_server.Implement;

import ChatRoom_server.Implement.Cmd_imp.*;
import ChatRoom_server.Implement.Database.User;
import ChatRoom_server.Implement.Database.txtDataBase;
import ChatRoom_server.Interface.ChainCmd;
import ChatRoom_server.Interface.DataBase;
import ChatRoom_server.Interface.SubClient;

import java.io.*;
import java.net.Socket;
import java.util.Arrays;

public class Subclient_imp extends SubClient {
    private User user;
    private Socket s;

    public Subclient_imp(Socket s) {
        this.s = s;
    }

    @Override
    public void run() {
        DataBase dataBase = new txtDataBase();
        try {
            String request = null;
            BufferedReader listenRq =
                    new BufferedReader( new InputStreamReader(s.getInputStream()));
            PrintWriter sendSmg =
                    new PrintWriter( new OutputStreamWriter(s.getOutputStream()));

            LoginCmd result_login = new LoginCmd(s);
            RegisterCmd result_register = new RegisterCmd(s);
            ChainCmd handle_loginCmd = new SplitCmd(result_login);
            ChainCmd handle_registerCmd = new SplitCmd(result_register);

            while(!result_login.isAccept()){
                request = listenRq.readLine();
                switch (request.split(":")[0].toLowerCase()){
                    case "login":
                        handle_loginCmd.Hanlde(request);
                        if (result_login.isAccept()){
                            sendSmg.println("true");
                            user = result_login.getUser();
                        }
                        else {
                            sendSmg.println("false");
                        }
                        sendSmg.flush();
                        break;
                    case "register":
                        handle_registerCmd.Hanlde(request);
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

            while ((request = listenRq.readLine()) != null){
                switch (request.split(":")[0].toLowerCase()){
                    case "send":
                        SendCmd sendCmd = new SendCmd(user.getName());
                        new SplitCmd(sendCmd).Hanlde(request);
//                        System.out.println(user.getName()+": "+sendCmd.getResult());
                        sendSmg.println(sendCmd.getResult());
                        sendSmg.flush();
                        break;

                    case "joincr":
                        JoincrCmd joincrCmd = new JoincrCmd(user.getName());
                        new SplitCmd(joincrCmd).Hanlde(request);
//                        System.out.println(user.getName()+": "+joincrCmd.getResult());
                        sendSmg.println(joincrCmd.getResult());
                        sendSmg.flush();
                        break;

                    case "sendcr":
                        SendcrCmd sendcrCmd = new SendcrCmd(user.getName());
                        new SplitCmd(sendcrCmd).Hanlde(request);
//                        System.out.println(user.getName()+": "+sendcrCmd.getResult());
                        sendSmg.println(sendcrCmd.getResult());
                        sendSmg.flush();
                        break;

                    case "logout":
                        System.out.println(user.getName() + " Logout");
                        dataBase.RemoveUser2Socket(user);
                        return;

                    case "alluser":
                        GetAllUsCmd getAllUsCmd = new GetAllUsCmd();
                        new SplitCmd(getAllUsCmd).Hanlde(request);
                        sendSmg.println("alluser:"+Arrays.toString(getAllUsCmd.getResult()));
                        sendSmg.flush();
                        break;
                    case "allcr":
                        GetAllCrCmd getAllCrCmd = new GetAllCrCmd();
                        new SplitCmd(getAllCrCmd).Hanlde(request);
                        sendSmg.println("allcr:"+Arrays.toString(getAllCrCmd.getResult()));
                        sendSmg.flush();
                        break;

                    default:
                        sendSmg.println("Invaild command");
                        sendSmg.flush();
                }
            }
        } catch (IOException e) {
            if (user == null){return;}
            System.out.println(user.getName() + " Logout");
            dataBase.RemoveUser2Socket(user);
        }
    }
}
