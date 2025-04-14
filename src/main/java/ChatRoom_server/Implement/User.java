package ChatRoom_server.Implement;

import java.net.Socket;

public class User {
    private String name;
    private String pw;
    private Socket s;

    public User(String name, Socket s) {
        this.name = name;
        this.s = s;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public Socket getS() {
        return s;
    }

    public void setS(Socket s) {
        this.s = s;
    }
}
