package ChatRoom_server.Implement.Cmd_imp;

import ChatRoom_server.Implement.Database.User;
import ChatRoom_server.Implement.Database.txtDataBase;
import ChatRoom_server.Interface.ChainCmd;
import ChatRoom_server.Interface.DataBase;

import java.util.Arrays;

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
            dataBase.Broadcast(
                    user.getName(),
                    "newchatroom:"+ Arrays.toString(dataBase.GetAllCr())
        );
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
