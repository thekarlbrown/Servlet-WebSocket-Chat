package MyServlets;

import java.net.Socket;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by Karl Brown ( TheKarlBrown ) on 6/24/15.
 */
public interface ChatServer extends Remote{
    public String getNextMessage() throws RemoteException;
    public void broadcastMessage(String message) throws RemoteException;
    public void addClient (Socket client) throws RemoteException;
}
