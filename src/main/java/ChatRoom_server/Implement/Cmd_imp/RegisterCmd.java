package ChatRoom_server.Implement.Cmd_imp;

import ChatRoom_server.Implement.Database.User;
import ChatRoom_server.Implement.Database.txtDataBase;
import ChatRoom_server.Interface.ChainCmd;
import ChatRoom_server.Interface.DataBase;

import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;

public class RegisterCmd implements ChainCmd {
    private ArrayList<String> request = new ArrayList<>();
    private ChainCmd nextCmd = null;
    private boolean Accept = false;
    private Socket s = null;

    public RegisterCmd(Socket s) {
        this.s = s;
    }

    @Override
    public void Hanlde(Object request) {
        this.request = new ArrayList<>(Arrays.asList(((String[]) request)));
        String name = this.request.get(0);
        String pw = this.request.get(1);
        DataBase dataBase = new txtDataBase();
        if (dataBase.Getuser(name) != null){
            return;
        }
        User newUser = new User(name, pw);
        dataBase.Adduser(newUser);
        Accept = true;
    }

    @Override
    public void setNext(ChainCmd chainCmd) {
        this.nextCmd = chainCmd;
    }

    public boolean isAccept() {
        return Accept;
    }
}
