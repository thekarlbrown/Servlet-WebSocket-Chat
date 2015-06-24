package MyServlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.Enumeration;
import java.util.Observable;
import java.util.Observer;
import java.util.Vector;

/**
 * ChatServlet with Socket's and Observable
 * Created by TheKarlBrown on 6/24/15.
 * Adapted from http://docstore.mik.ua/orelly/java-ent/servlet/ch10_03.htm
 * Intended Clients are JS and Android
 * Provides a minimal chat client to illustrate an understanding of various concepts and cross-platform support
 */
public class ChatServlet extends HttpServlet implements ChatServer {
    MessageSource source = new MessageSource();
    Vector socketClients = new Vector();

    protected int getSocketPort() {
        return 2426;
    }

    /**
     * Add Socket Client
     *
     * @param client Socket client to add to list
     */
    @Override
    public void addClient(Socket client) throws RemoteException {
        socketClients.addElement(client);
    }

    /**
     * Returns the next message (if there is one)
     *
     * @param request  Request that was sent to the servlet
     * @param response The response from the servlet containing your message
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();
        out.println(getNextMessage());
    }

    /**
     * Accepts a new message and broadcasts it to all
     *
     * @param request  Request that was sent to the servlet containing the message
     * @param response Response (set to no response) from the servlet
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String message = request.getParameter("message");
        // Broadcast the message if there is content
        if (message != null) broadcastMessage(message);
        // No response content
        response.setStatus(response.SC_NO_CONTENT);
    }

    /**
     * Gets the next messsage, blocks until message occurs
     *
     * @return MessageSink Observer, potentially waiting in while loop
     */
    @Override
    public String getNextMessage() throws RemoteException {
        return new MessageSink().getNextMessage(source);
    }

    @Override
    public void broadcastMessage(String message) throws RemoteException {
        // Send to HTTP-based clients
        source.sendMessage(message);

        // Send to socket-based clients (Android)
        Enumeration enumeration = socketClients.elements();
        while (enumeration.hasMoreElements()) {
            Socket client = null;
            try {
                client = (Socket) enumeration.nextElement();
                PrintStream out = new PrintStream(client.getOutputStream());
                out.println(message);
            } catch (IOException e) {
                try {
                    if (client != null) client.close();
                } catch (IOException ignored) {
                }
                socketClients.removeElement(client);
            }
        }
    }
}

/**
 * Class to act as source of messages. Utilizes Observable to relay messages to clients
 */
class MessageSource extends Observable {
    /**
     * Sends a message to each of the observers (clients)
     *
     * @param message String containing the chat message
     */
    public void sendMessage(String message) {
        setChanged();
        notifyObservers(message);
    }
}

/**
 * Class to act as a reciever of messages. Utilizes Observer to obtain relayed messages
 */
class MessageSink implements Observer {
    String message = null;

    /**
     * Updates each of the clients messages from the Observable
     * synchronized is added in-case multiple clients are viewing messages to prevent
     * thread interference and memory consistency errors
     *
     * @param o   Observable object
     * @param arg Message being passed in (String)
     */
    @Override
    synchronized public void update(Observable o, Object arg) {
        message = (String) arg;
        notify();
    }

    synchronized public String getNextMessage(MessageSource source) {
        source.addObserver(this);
        while (message == null) {
            try {
                wait();
            } catch (Exception ignored) {
            }
        }

        // Stop informing us about new messages
        source.deleteObserver(this);

        String messagecopy = message;

        // Set to null so synchronized methods can be called again
        message = null;
        return messagecopy;
    }
}
