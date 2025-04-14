package ChatRoom_client;

import ChatRoom_server.Interface.ChainCmd;

import java.util.ArrayList;

public class RegisterCmd implements ChainCmd {
    private ArrayList<String> request = new ArrayList<>();
    private ChainCmd nextCmd = null;
    private boolean accept = false;

    @Override
    public void Hanlde(Object request) {

    }

    @Override
    public void setNext(ChainCmd chainCmd) {
        this.nextCmd = chainCmd;
    }
}
