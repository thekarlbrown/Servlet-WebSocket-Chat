import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by TheKarlBrown on 6/24/15.
 */
public interface ChatServer extends Remote{

    public String getNextMessage() throws RemoteException;
    public void broadcastMessage(String message) throws RemoteException;
    
    public void addClient (ChatClient client) throws RemoteException;
    public void deleteClient (ChatClient client) throws RemoteException;
    public String identifyClient (ChatClient client) throws RemoteException;

}
