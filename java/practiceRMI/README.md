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

```
Open CMD in RMI Folder

// create class file
CMD[1] : javac *.java

// rmi registry start
CMD[1] : start rmiregistry

// Server Start
CMD[1] : java HelloServer

// Client Start and input arguments
CMD[2] : java Client 1 2 3 4 5
```

![image](https://user-images.githubusercontent.com/58055835/168197266-27ef20a4-0cd0-435e-9b79-3b5462b3539b.png)

