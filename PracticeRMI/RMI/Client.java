import java.net.MalformedURLException;
import java.rmi.*;

public class Client {
    public static void main(String[] args) throws MalformedURLException, NotBoundException, RemoteException {
        HelloService service = ( HelloService ) Naming.lookup("rmi://192.168.0.133:5099/hello");
        for(int i=0; i<args.length; i++){
            service.echo(i+"번째 args 클라이언트 호출 : "+args[i]);
        }
        System.out.println(" CLIENT END "+service.echo("Bye Server" + " " +service.getClass().getName()));
    }
}
