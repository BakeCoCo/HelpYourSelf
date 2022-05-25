# Spring 5 Recipes 참고


### POJO (Plain Old Java Object)
```java
//오래된 방식의 간단한 자바 오브젝트 라는 의미

//기본형태 예시)
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


### 자바로 POJO 구성하기

```java
// SequenceGenerator
import java.util.concurrent.atomic.AtomicInteger;

public class SequenceGenerator {
    private String prefix;
    private String suffix;
    private int initial;
    private final AtomicInteger counter = new AtomicInteger();

    public SequenceGenerator() {}
    public String getPrefix() {return prefix;}
    public void setPrefix(String prefix) {this.prefix = prefix;}
    public String getSuffix() {return suffix;}
    public void setSuffix(String suffix) {this.suffix = suffix;}
    public int getInitial() {return initial;}
    public void setInitial(int initial) {this.initial = initial;}

    @Override
    public String toString() {
        return "SequenceGenerator{" +
                "prefix='" + prefix + '\'' +
                ", suffix='" + suffix + '\'' +
                ", initial=" + initial +
                ", counter=" + counter +
                '}';
    }
}
```

```java
// SequenceGeneratorConfiguration
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SequenceGeneratorConfiguration {

    @Bean
    public SequenceGenerator sequenceGenerator(){
        SequenceGenerator seqgen = new SequenceGenerator();
        seqgen.setPrefix("30");
        seqgen.setSuffix("A");
        seqgen.setInitial(100000);
        return seqgen;
    }
}
```

### @Configuration
```
@Configuration은 이 클래스가 구성 클래스임을 스프링에 알림
그리고 내부에 정의된 Bean 인스턴스 즉, @Bean을 붙인(Bean 인스턴스를 생성해 반환하는) 자바 메서드를 찾는다.
```
### @Bean
```
@Bean을 붙이면 그 메서드와 동일한 이름의 빈이 생성.
Bean의 이름을 따로 명시하려면 name 속성을 적는다.
예) @Bean (name = "Test") = Test라는 이름의 빈을 만든다.
이렇게 이름을 지정하면 메서드명은 무시된다.
```

### IoC 컨테이너 (Inversion of Control) 초기화하여 Annotation 스캐닝 하기
```
애너테이션을 붙인 자바클래스를 스캐닝 하려면 IoC컨테이너를 인스턴스화 해야한다.
그래야 @Configuration, @Bean을 발견하고 나중에 IoC컨테이너에서 Bean 인스턴스를 가져올수 있다.

Spring은
1. Bean Factory
2. Application Context 

2가지 IoC컨테이너를 제공한다.
구성 파일은 두 컨테이너 모두 동일.
```

### ApplicationContext
```
ApplicationContext는 기본 기능에 충실하면서 BeanFactory보다 발전된 기능을 가지고 있다.
그래서 Resource에 제약받는 상황이 아니라면 ApplicationContext를 사용하는게 좋다.

기본 사용 예)
ApplicationContext context = new AnnotationConfigApplicationContext(클래스명.class);
```

### IoC 컨테이너에서 POJO 인스턴스/빈 가져오기
```
Configuration 클래스에 선언된 Bean을 BeanFactory 또는 ApplicationContext에서 가져오려면
유일한 Bean이름을 getBean() 메서드의 인수로 호출한다.
getBean() 메서드는 java.lang.Object형으로 반환하므로 Type에 맞게 캐스팅한다.

기본 사용 예1)
가져올 Configuration에 있는 Bean을 가져오는것이니
Configuration클래스명 변수명 = (Configuration클래스명) context.getBean(Bean클래스명);

SequenceGenerator generator = (SequenceGenerator) context.getBean("sequenceGenerator");

기본 사용 예2)
캐스팅을 안 하려면 getBean() 메서드의 두 번째 인수에 Bean클래스명을 지정.
SequenceGenerator generator = context.getBean("sequenceGenerator",SequenceGenerator.class);

기본 사용 예3)
Bean이 1개 뿐이라면 이름을 생략할 수 있다.
SequenceGenerator generator = context.getBean(SequenceGenerator.class);

이런 식으로 POJO 인스턴스/빈을 스프링 외부에서 생성자를 이용해 여느 객체처럼 사용할 수 있다.
```

```java
// Main
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(SequenceGeneratorConfiguration.class);
        SequenceGenerator generator = context.getBean(SequenceGenerator.class);
        System.out.println(generator.toString());
        System.out.println(generator.toString());
    }
}
```

```
실행 결과
SequenceGenerator{prefix='30', suffix='A', initial=100000, counter=0}
SequenceGenerator{prefix='30', suffix='A', initial=100000, counter=0}
```

