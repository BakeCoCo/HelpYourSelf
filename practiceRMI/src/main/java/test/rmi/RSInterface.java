package test.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
//
public interface RSInterface extends Remote {
    public void println(String msg) throws RemoteException;
}
