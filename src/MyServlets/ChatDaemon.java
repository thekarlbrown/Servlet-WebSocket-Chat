package MyServlets;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * Daemon to support the opening of Sockets for client/server communication
 * Created by Karl Borwn ( TheKarlBrown ) on 6/25/15.
 */
public class ChatDaemon extends Thread {
    private ServerSocket serverSocket;
    private ChatServlet servlet;
    public ChatDaemon (ChatServlet servlet){
        this.servlet=servlet;
    }

    /**
     * Core section that is run by the thread upon starting
     */
    public void run(){
        // Attempt to open a Socket to accept chat Clients
        try{
            serverSocket=new ServerSocket(servlet.getSocketPort());
        }catch (IOException e){ return; }

        // Continually search for new clients
        while(true){
            try {
                servlet.addClient(serverSocket.accept());
            }catch(IOException e){ }
        }
    }
}
