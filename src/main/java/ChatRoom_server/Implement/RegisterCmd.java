package ChatRoom_server.Implement;

import ChatRoom_server.Interface.ChainCmd;

import java.util.ArrayList;
import java.util.Arrays;

public class RegisterCmd implements ChainCmd {
    private ArrayList<String> request = new ArrayList<>();
    private ChainCmd nextCmd = null;
    private boolean accept = false;

    @Override
    public void Hanlde(Object request) {
        this.request = new ArrayList<>(Arrays.asList(((String) request).split(":")));
        String name = this.request.get(0);
        String pw = this.request.get(1);

    }

    @Override
    public void setNext(ChainCmd chainCmd) {
        this.nextCmd = chainCmd;
    }
}
