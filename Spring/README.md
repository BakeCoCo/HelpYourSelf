# Spring TEST

## SpringFramework의 기초

1. POJO (Plain Old Java Object)
2. IoC  (Inversion of Control)
3. DI   (Dependency Injection)
4. AOP  (Aspect-Oriented Programming)


## POJO (Plain Old Java Object) 오래된 방식의 자바객체
```java
// 기본 예 Getter, Setter라고 생각하면 된다.
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
제어권을 타 객체에게 넘겨주어 자신이 사용하는 객체에 대한 컨트롤을 하지 않는다.
자신이 필요한 객체를 직접 생성하지 않고 외부 객체로부터 필요한 객체를 주입받는다.

// 이게 의존성 주입인가...? 사실 잘 모르겠다.
public class User {
    private String name;
    private String game;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGame() {
        return game;
    }

    public void setGame(String game) {
        this.game = game;
    }
}
```
```java
public interface GameTest {
    public void print(User user);
}
```
```java
public class GameStart implements GameTest{

    @Override
    public void print(User user) {
        System.out.println(user.getName()+"회원이 "+user.getGame()+"을 플레이합니다.");
    }
}
```
```java
public class Main {
    public static void main(String[] args) {
        GameStart st = new GameStart();
        User user = new User();
        user.setName("LHJ");
        user.setGame("Spring");
        st.print(user);
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
// 의존성 주입의 3가지 방법

// 1. 생성자 주입
private User user;
public testDI(User user){
    this.user = user;
}

// 2. Setter 주입
private User user;
public void setB(User user){
    this.user = user;
}

// 3. Interface 주입
public interface DItest {
    void inject(User user);
}

public testDI implements DItest{
    private User user;
    public void inject(User user){
        this.user = user;
    }
}
```

## AOP
```

```
