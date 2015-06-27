/**
 * Created by TheKarlBrown on 6/26/15.
 */
import java.io.IOException;
import java.util.*;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/chat")
public class ChatServer {
    private static Map<Session,String> connectedUsers = Collections.synchronizedMap(new HashMap<>());

    @OnOpen
    public void onOpen (Session session){
        System.out.println(session.getId() + " opened a connection");
    }

    @OnMessage
    public void onMessage(String message, Session session){
        if((message.length()>7)&&message.substring(0,7).equals("newUser")) {
            connectedUsers.put(session, message.substring(7));
            String currentUsers = userList();
            for (Session sessions : connectedUsers.keySet()){
                try{
                    sessions.getBasicRemote().sendText("---Users---" + currentUsers);
                    sessions.getBasicRemote().sendText(" ( " + message.substring(7) + " has entered the chat )");
                }
                catch (IOException e){ connectedUsers.remove(sessions); }
            }
            System.out.println(message + " has been added");
        }else{
            for (Session sessions : connectedUsers.keySet()) {
                try {  sessions.getBasicRemote().sendText(message);}
                catch (IOException e) {  connectedUsers.remove(sessions); }
            }
        }
    }

    @OnClose
    public void onClose (Session session){
        String userLeaving = connectedUsers.get(session);
        connectedUsers.remove(session);
        String currentUsers = userList();
        for (Session sessions : connectedUsers.keySet()){
            try{
                sessions.getBasicRemote().sendText("---Users---" + currentUsers);
                sessions.getBasicRemote().sendText(" ( " + userLeaving + " has left the chat )");
            }
            catch (IOException e){ connectedUsers.remove(sessions); }
        }
    }


    private String userList(){
        StringBuilder stringBuilder = new StringBuilder();
        for (String username : connectedUsers.values()){  stringBuilder.append("<br>" + username ); }
        return stringBuilder.toString();
    }

}
