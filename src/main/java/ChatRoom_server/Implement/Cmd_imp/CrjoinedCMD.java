package ChatRoom_server.Implement.Cmd_imp;

import ChatRoom_server.Implement.Database.User;
import ChatRoom_server.Implement.Database.txtDataBase;
import ChatRoom_server.Interface.ChainCmd;
import ChatRoom_server.Interface.DataBase;

public class CrjoinedCMD implements ChainCmd {
    private String[] result;
    private User sender;

    public CrjoinedCMD(User sender) {
        this.sender = sender;
    }

    @Override
    public void Hanlde(Object request) {
        DataBase dataBase = new txtDataBase();
        result = dataBase.GetjoinedCr(sender);
    }

    @Override
    public void setNext(ChainCmd chainCmd) {

    }

    public String[] getResult() {
        return result;
    }
}
