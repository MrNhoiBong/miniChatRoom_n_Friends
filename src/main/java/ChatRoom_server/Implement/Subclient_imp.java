package ChatRoom_server.Implement;

import ChatRoom_server.Interface.SubClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Subclient_imp implements SubClient {
    private String name;
    private Socket s;

    @Override
    public void run() {
        try {
            String requset = null;
            BufferedReader listenRq =
                    new BufferedReader( new InputStreamReader( s.getInputStream()));
            while ((requset = listenRq.readLine()) != null){
                System.out.println(requset);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
