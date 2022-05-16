import java.rmi.RemoteException;
import java.rmi.registry.*;
import java.rmi.server.UnicastRemoteObject;

public class HelloServer extends UnicastRemoteObject implements HelloService {

    protected HelloServer() throws RemoteException {
        super();
    }

    @Override
    public String echo(String msg) throws RemoteException {
        System.out.println("From server : "+msg);
        return msg;
    }

    public static void main(String[] args) throws RemoteException {
        Registry registry = LocateRegistry.createRegistry(5099);
        registry.rebind("hello", new HelloServer());
        System.out.println("[## SERVER START ##]");
    }
}
