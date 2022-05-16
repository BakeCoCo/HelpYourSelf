java RMI ( Remote Method Invocating )


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



