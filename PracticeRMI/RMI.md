## Java RMI (Java Remote Method Invocation)

# 원격 메소드 호출

```java
// Remote Interface
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface HelloService extends Remote {
    public String echo(String msg) throws RemoteException;
}
```

```java
// 호출용 Client
import java.net.MalformedURLException;
import java.rmi.*;

public class Client {
    public static void main(String[] args) throws MalformedURLException, NotBoundException, RemoteException {
        HelloService service = ( HelloService ) Naming.lookup("rmi://localhost:5099/hello");
        for(int i=0; i<args.length; i++){
            service.echo(i+"번째 args 클라이언트 호출 : "+args[i]);
        }
        System.out.println(" CLIENT END "+service.echo("Bye Server" + " " +service.getClass().getName()));
    }
}
```

```java
// 메소드 처리용? Server
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
```
