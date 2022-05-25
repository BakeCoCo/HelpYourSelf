# Spring TEST

## SpringFramework의 기초

1. POJO (Plain Old Java Object)
2. IoC  (Inversion of Control)
3. DI   (Dependency Injection)
4. AOP  (Aspect-Oriented Programming)


## POJO (Plain Old Java Object) 오래된 방식의 자바객체
```java
// 기본 예
public class User {
    private String user;
    private String pass;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}
```

## IoC  (Inversion of Control) 제어의 역전
```java
// 기본 예
public class testDI {
    private User user;
    // new를 사용하여 사용자가 직접 넣어준다.
    public testDI(){
        this.user = new User();
    }
    
    // new를 사용하지 않고 의존성을 주입해준다.
    public testDI(User user){
        this.user = user;
    }
}
```

```
IoC컨테이너는 두 종류로 나뉜다.
1. BeanFactory
2. ApplicationContext
```

## DI  (Dependency Injection) 의존성 주입
```java
// 기본 예
public testDI(User user){
    this.user = user;
}
```

## AOP
```

```
