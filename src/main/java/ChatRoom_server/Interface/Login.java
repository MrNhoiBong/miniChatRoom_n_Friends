package ChatRoom_server.Interface;

public abstract class Login extends Thread{
    public abstract Boolean check(String userName, String userPw);
    @Override
    public abstract void run();
}
