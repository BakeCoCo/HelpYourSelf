## Java RMI (Java Remote Method Invocation)

Exporting the Service using the `RmiServiceExporter`

1. Spring Container Set Up

### 서버

```java
public class Account implements Serializable{

    private String name;

    public String getName(){
        return name;
    }

    public void setName(String name) {
      this.name = name;
    }
}
```

```java
public interface AccountService {

    public void insertAccount(Account account);

    public List<Account> getAccounts(String name);
}
```

```java
public interface RemoteAccountService extends Remote {

    public void insertAccount(Account account) throws RemoteException;

    public List<Account> getAccounts(String name) throws RemoteException;
}
```

```java
// the implementation doing nothing at the moment
public class AccountServiceImpl implements AccountService {

    public void insertAccount(Account acc) {
        // do something...
    }

    public List<Account> getAccounts(String name) {
        // do something...
    }
}
```

#### Spring container 세팅

```xml
<bean id ="accountService" class="example.AccountServiceImpl">
  <!-- any additional properties, maybe a DAO? -->
</bean>
```

#### `RmiServiceExporter`를 사용해서 서비스를 노출한다.

```xml
<bean class="org.springframework.remoting.rmi.RmiserviceExporter">
  <!-- does not necessarily have to be the same name as the bean to be exported -->
  <property name="serviceName" value="AccountService"/>
  <property name="service" ref="accountService"/>
  <property name="serviceInterface" value="example.AccountService"/>
  
  <!-- RMI PORT Default 1099 -->
  <property name="registryPort" value="1199"/>
</bean>
```

#### 클라이언트 측에서는 `'rmi://HOST:1199/AccountService'` 해당 URL형식으로 연결한다.

| `servicePort`는 생략되었는데 기본 0이다.

| 익명 포트가 서비스와 통신하는데 사용된다.


### 클라이언트

```java
public class SimpleObject {

    private AccountService accountService;

    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }

    // additional methods using the accountService

}
```

#### 별도의 Spring container 구성

```xml
<bean class="example.SimpleObject">
    <property name="accountService" ref="accountService"/>
</bean>

<bean id="accountService" class="org.springframework.remoting.rmi.RmiProxyFactoryBean">
    <property name="serviceUrl" value="rmi://HOST:1199/AccountService"/>
    <property name="serviceInterface" value="example.AccountService"/>
</bean>
```

### 필요한것은 `RmiServiceExporter`, `RmiProxyFactoryBean` 두개뿐이다.

### `RmiServiceExporter`로 원격 계정을 활성화 하고 `RmiProxyFactoryBean`으로 클라이언트에서 연결한다.

### 이걸 container 없이 표현하면 다음과 같다.


### Server 

#### Container 대신

```java
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.rmi.RmiServiceExporter;

@Configuration
public class RmiConfig {
	@Autowired
	private AccountService accountService;
  
	@Bean
	public RmiServiceExporter rmiServiceExporter() {
		RmiServiceExporter rm = new RmiServiceExporter();
		rm.setServiceName("AccountService");
		rm.setService(accountService);
		rm.setServiceInterface(AccountService.class);
		rm.setRegistryPort(1199);
		return rm;
	}
}
```

### Client

#### SimpleObject + Container

```java
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.remoting.rmi.RmiProxyFactoryBean;
import org.springframework.stereotype.Component;

@Component
public class SimpleObject {
	
    @Autowired
    private AccountService accountService;
    
    public void setAccountService(AccountService accountService) {
		this.accountService = accountService;
	}
    
    @Bean
    public RmiProxyFactoryBean rmiproxyFactory() {
    	RmiProxyFactoryBean rf = new RmiProxyFactoryBean();
    	rf.setServiceUrl("rmi://HOST:1199/AccountService");
    	rf.setServiceInterface(AccountService.class);
    	return rf;
    }
}
```









# 예제

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

