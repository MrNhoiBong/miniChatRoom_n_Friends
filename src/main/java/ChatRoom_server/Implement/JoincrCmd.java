package ChatRoom_server.Implement;

import ChatRoom_server.Interface.ChainCmd;
import ChatRoom_server.Interface.DataBase;

import java.net.Socket;

public class JoincrCmd implements ChainCmd {
    private String nameChatroom;
    private User user;
    private String sender;
    private String result;
    private ChainCmd nextCmd;

    public JoincrCmd(String sender) {
        DataBase dataBase = new txtDataBase();
        this.user = dataBase.Getuser(sender);
    }

    @Override
    public void Hanlde(Object request) {
        DataBase dataBase = new txtDataBase();

        nameChatroom = ((String[]) request)[0];
        if (dataBase.GetCr(nameChatroom) == null){
            dataBase.AddCr(nameChatroom);
            result = "Create new chatroom";
        }
        else {
            result = "Join exiting chatroom";
        }
        dataBase.GetCr(nameChatroom).AddCl(user);
    }

    @Override
    public void setNext(ChainCmd chainCmd) {
        this.nextCmd = chainCmd;
    }

    public String getResult() {
        return result;
    }
}
