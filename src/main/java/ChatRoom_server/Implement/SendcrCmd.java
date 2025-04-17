package ChatRoom_server.Implement;

import ChatRoom_server.Interface.ChainCmd;
import ChatRoom_server.Interface.Chatroom;
import ChatRoom_server.Interface.DataBase;

public class SendcrCmd implements ChainCmd {
    private String[] parameter;
    private boolean success = false;
    private String sender;

    public SendcrCmd(String sender) {
        this.sender = sender;
    }

    @Override
    public void Hanlde(Object request) {
        parameter = (String[]) request;
        String nameCr = parameter[0];
        String mess = parameter[1];
        DataBase dataBase = new txtDataBase();

        Chatroom chatroom = dataBase.GetCr(nameCr);
        if (chatroom == null ){return;}
        success = true;
        chatroom.Send2Cr(dataBase.Getuser(sender), mess);

    }

    public boolean isSuccess() {
        return success;
    }

    @Override
    public void setNext(ChainCmd chainCmd) {

    }
}
