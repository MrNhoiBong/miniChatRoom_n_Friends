package ChatRoom_server.Implement.Cmd_imp;

import ChatRoom_server.Implement.Database.txtDataBase;
import ChatRoom_server.Interface.ChainCmd;
import ChatRoom_server.Interface.DataBase;

public class GetAllCrCmd implements ChainCmd {
    private String[] parameter;
    private String[] result;
    private String sender;

    @Override
    public void Hanlde(Object request) {
        DataBase dataBase = new txtDataBase();
        result = dataBase.GetAllCr();
    }

    @Override
    public void setNext(ChainCmd chainCmd) {

    }

    public String[] getResult() {
        return result;
    }
}
