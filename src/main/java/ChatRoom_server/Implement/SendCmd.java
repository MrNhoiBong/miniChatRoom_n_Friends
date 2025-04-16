package ChatRoom_server.Implement;

import ChatRoom_server.Interface.ChainCmd;
import ChatRoom_server.Interface.DataBase;

import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class SendCmd implements ChainCmd {
    private String[] parameter;
    private ChainCmd nextCmd;
    private String result;

    @Override
    public void Hanlde(Object request) {
        DataBase dataBase = new txtDataBase();
        parameter = (String[]) request;
        String sender = parameter[0];
        String receiver = parameter[1];
        String message = parameter[2];

        try {
            // Lấy đối tượng User từ receiver
            User user = dataBase.Getuser(receiver);

            if (user == null) {
                result = "Người dùng không tồn tại: " + receiver;
                return;
            }

            // Lấy Socket từ User
            Socket socket = dataBase.GetSocketfUser(user);

            if (socket == null) {
                result = "Không thể kết nối tới người dùng: " + receiver;
                return;
            }

            // Gửi tin nhắn thông qua Socket
            PrintWriter sendSmg =
                    new PrintWriter( new OutputStreamWriter(socket.getOutputStream()));
            sendSmg.println(message);
            sendSmg.flush();

            result = "Tin nhắn đã gửi tới " + receiver + ": " + message;
        } catch (Exception e) {
            result = "Lỗi khi gửi tin nhắn: " + e.getMessage();
        }
    }

    @Override
    public void setNext(ChainCmd chainCmd) {
        this.nextCmd = chainCmd;
    }

    public String getResult() {
        return result;
    }
}
