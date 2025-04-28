package ChatRoom_client.Interface;

import java.net.Socket;

public interface InterfaceLogin {
    String getUserName();
    void setUserName(String userName);
    String getPassWord();
    void setPassWord(String passWord);
    Socket getSocket();
    void setSocket(Socket s);
    void sendLoginRequest(String request);
    String receivedLoginResponse();
    boolean login();
}
