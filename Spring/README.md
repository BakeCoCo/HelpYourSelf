# Spring TEST

## SpringFramework의 기초

1. POJO (Plain Old Java Object)         오래된 방식의 자바객체
2. IoC  (Inversion of Control)          제어의 역전
3. DI   (Dependency Injection)          의존성 주입
4. CGLIB(Code Generator Library)        코드 생성 라이브러리
5. AOP  (Aspect-Oriented Programming)   관점 지향 프로그래밍

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

    public User() {
        this.name = "사용자";
        this.game = "게임";
    }
    
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
        user.setName("LHJ");    // 입력을 안하면 사용자
        user.setGame("Spring"); // 입력을 안하면 게임
        st.print(user);
    }
}
```

```
결과 : LHJ회원이 Spring을 플레이합니다.
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

### CGLIB (Code Generator Library) 코드 생성 라이브러리
```
CGLIB는 코드 생성 라이브러리(Code Generator Library)로 런타임에 동적으로 자바 클래스의 Proxy를 생성해주는 기능을 제공한다.
CGLIB를 사용하면 매우 쉽게 Proxy객체를 생성할 수 있으며, 성능 또한 우수하다.
더불어, Interface가 아닌 Class에 대해서 동적 Proxy를 생성할 수 있기 때문에 다양한 프로젝트에서 널리 사용되고 있다.
예를 들어, Hibernate는 JavaBean 객체에 대한 프록시를 생성할 때 CGLIB를 사용하며, Spring은 Proxy기반의 AOP를 구현할때 CGLIB를 사용하고 있다.
```

## AOP  관점 지향 프로그래밍
```
AOP란 흩어진 AspectJ자바 프레임워크를 다른 구현체와 연동하여 사용하는 방법
흩어진 Aspect를 모듈화 하는 프로그래밍 기법.
```
1. Aspect
```
흩어진 관심사(Crosscutting Concerns)를 묶어서 모듈화 한 것.
하나의 모듈. Advice와 Point Cut이 들어간다.
```

2. Target
```
Aspect가 가지고 있는 Advice가 적용되는 대상(클래스, 메서드 등등)을 말한다.
```

3. Advice
```
어떤 일을 해야하는지에 대한것.
해야할 일들에 대한 정보를 가지고 있다.
```

4. Join Point
```
가장 흔한 Join Point는 메서드 실행 시점이다.
Advice가 적용될 위치, 끼어들 지점, 생성자 호출 직전, 생성자 호출시, 필드에 접근하기전, 필드에 값을 가져갈때 등등
```

5. Point Cut
```
Join Point의 상세한 스펙을 정의한것.
어디에 적용해야 하는지에 대한 정보를 가지고 있다.
"A클래스에 B메서드를 적용할 때 호출을 해라." 와 같은 구체적인 정보를 준다.
```


## @Configuration

```java
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DemoCGLIB {
    private int counter;

    //@Bean
    public String something(){
        System.out.println("method invoked");
        return String.valueOf(++counter);
    }

    public static void main(String... strings) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(DemoCGLIB.class);
        DemoCGLIB bean = context.getBean(DemoCGLIB.class);
        System.out.println(bean.something());
        System.out.println(bean.something());
        System.out.println(bean.something());
    }
}

@Bean이 있을때 실행한 결과
method invoked
1
1
1

@Bean이 없을때 실행한 결과
method invoked
1
method invoked
2
method invoked
3

@Configuration 클래스는 CGLIB에 의해 하위 분류되며 @Bean 메서드를 poxying하여 라이프사이클을 제어한다.
이러한 프록시 방법으로 생성된 Bean 인스턴스는 캐시된다.
이것이 Bean 메서드가 여러 번 호출될 경우 동일한 인스턴스를 반환하는 이유다.
```

















