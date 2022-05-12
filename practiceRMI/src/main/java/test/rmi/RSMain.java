package test.rmi;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.RMIClientSocketFactory;
import java.rmi.server.RMIServerSocketFactory;
import java.rmi.server.UnicastRemoteObject;

public class RSMain extends UnicastRemoteObject implements RSInterface {
    private static final long serialVersionUID = 1L;
    private static final String BIND_NAME = "rs";

    public static void main(String[] args) {
        System.out.println("[RMI-SERVER] START");
        try {
            Naming.rebind(BIND_NAME, new RSMain());
            while (true);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try { Naming.unbind(BIND_NAME); }catch (Exception e){}
        }
        System.out.println("[RMI-SERVER] EXIT");
    }

    protected RSMain() throws RemoteException {
        super();
    }

    protected RSMain(int port) throws RemoteException {
        super(port);
    }

    protected RSMain(int port,RMIClientSocketFactory csf,RMIServerSocketFactory ssf) throws RemoteException {
        super(port,csf,ssf);
    }

    @Override
    public void println(String msg) throws RemoteException {
        System.out.println("[RMI-SERVER] : "+msg);
    }
}
