package ChatRoom_client;

import org.json.JSONObject;

public class Main {
    public static void main(String[] args) {
        JSONObject jo = new JSONObject();
        jo.put("name", "jon doe");
        jo.put("age", "22");
        jo.put("city", "chicago");
        System.out.println(jo);
    }
}
