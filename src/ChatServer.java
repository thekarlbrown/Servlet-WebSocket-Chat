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
    private static Set<Session> connectedUsers = Collections.synchronizedSet(new HashSet<Session>());

    @OnOpen
    public void onOpen (Session session){
        System.out.println(session.getId() + " opened a connection");
        connectedUsers.add(session);
        String currentUsers = userList();
        for (Session sessions : connectedUsers){
            try{ sessions.getBasicRemote().sendText("Users" + currentUsers); }
            catch (IOException e){ connectedUsers.remove(sessions); }
        }
    }

    @OnMessage
    public void onMessage(String message, Session session){
        for (Session sessions: connectedUsers){
            try{ sessions.getBasicRemote().sendText(message); }
            catch (IOException e){ connectedUsers.remove(sessions); }
        }
    }

    @OnClose
    public void onClose (Session session){
        connectedUsers.remove(session);
    }


    private String userList(){
        StringBuilder stringBuilder = new StringBuilder();
        for (Session session : connectedUsers){  stringBuilder.append("\n" + session.getId() ); }
        return stringBuilder.toString();
    }

}
