package ChatRoom_server.Implement;

import ChatRoom_server.Interface.ChainCmd;
import ChatRoom_server.Interface.DataBase;

import java.util.ArrayList;
import java.util.Arrays;

public class LoginCmd implements ChainCmd {
    private ArrayList<String> request = new ArrayList<>();
    private ChainCmd nextCmd = null;
    private boolean accept = false;

    public LoginCmd(ChainCmd nextCmd) {
        this.nextCmd = nextCmd;
    }

    public LoginCmd(){}
    @Override
    public void Hanlde(Object request) {
        this.request = new ArrayList<>(Arrays.asList(((String) request).split(":")));
        String name = this.request.get(0);
        String pw = this.request.get(1);
        DataBase dataBase = new txtDataBase();
        User user = null;
        if ((user = dataBase.Getuser(name)) != null){
            if (user.getPw().equals(pw)){
                accept = true;
            }
        }
    }

    @Override
    public void setNext(ChainCmd chainCmd) {
        this.nextCmd = chainCmd;
    }

    public boolean isAccept() {
        return accept;
    }
}
