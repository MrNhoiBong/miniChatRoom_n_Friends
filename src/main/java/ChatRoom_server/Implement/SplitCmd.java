package ChatRoom_server.Implement;

import ChatRoom_server.Interface.ChainCmd;

public class SplitCmd implements ChainCmd{
    private String request;
    private ChainCmd nextCmd = null;

    public SplitCmd(ChainCmd nextCmd) {
        this.nextCmd = nextCmd;
    }

    public SplitCmd(){}

    @Override
    public void Hanlde(Object request_cl) {
        this.request = (String) request_cl;

        // Tìm vị trí của dấu ":"
        int colonIndex = request.indexOf(":");
        // Xóa phần từ đầu đến dấu ":"
        String modifiedString = request.substring(colonIndex + 1);

        if (nextCmd == null){return;}
        nextCmd.Hanlde(modifiedString.split(":"));
    }

    @Override
    public void setNext(ChainCmd chainCmd) {
        this.nextCmd = chainCmd;
    }
}
