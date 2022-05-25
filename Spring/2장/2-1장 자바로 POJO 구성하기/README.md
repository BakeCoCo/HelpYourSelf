## 2-1 자바로 POJO 구성하기

### 패키지 com.example.practicespring 에서 테스트


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
```java
/*
    ApplicationContext는 기본 기능에 충실하면서 BeanFactory보다 발전된 기능을 가지고 있다.
    그래서 Resource에 제약받는 상황이 아니라면 ApplicationContext를 사용하는게 좋다.
*/
//기본 사용 예)
ApplicationContext context = new AnnotationConfigApplicationContext(클래스명.class);
```

### IoC 컨테이너에서 POJO 인스턴스/빈 가져오기
```java
/*
    Configuration 클래스에 선언된 Bean을 BeanFactory 또는 ApplicationContext에서 가져오려면
    유일한 Bean이름을 getBean() 메서드의 인수로 호출한다.
    getBean() 메서드는 java.lang.Object형으로 반환하므로 Type에 맞게 캐스팅한다.
*/
//기본 사용 예1)
//가져올 Configuration에 있는 Bean을 가져오는것이니
Configuration클래스명 변수명 = (Configuration클래스명) context.getBean(Bean클래스명);

SequenceGenerator generator = (SequenceGenerator) context.getBean("sequenceGenerator");

//기본 사용 예2)
//캐스팅을 안 하려면 getBean() 메서드의 두 번째 인수에 Bean클래스명을 지정.
SequenceGenerator generator = context.getBean("sequenceGenerator",SequenceGenerator.class);

//기본 사용 예3)
//Bean이 1개 뿐이라면 이름을 생략할 수 있다.
SequenceGenerator generator = context.getBean(SequenceGenerator.class);

//이런 식으로 POJO 인스턴스/빈을 스프링 외부에서 생성자를 이용해 여느 객체처럼 사용할 수 있다.
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

```java
//실행 결과
SequenceGenerator{prefix='30', suffix='A', initial=100000, counter=0}
SequenceGenerator{prefix='30', suffix='A', initial=100000, counter=0}
```

### POJO 클래스에 @Component를 붙여 DAO Bean 생성하기
```
POJO는 대부분 DB나 유저 입력을 활용해 인스턴스로 만든다.

이번엔
1. Domain클래스
2. DAO(Data Access Object)
    를 이용해 POJO를 생성한다.

이번에 하는것이 기초역할을 하므로 반드시 이런 구조에 익숙해져야 한다.
```

```java
// id, prefix, suffix 3프로퍼티를 지닌 Sequence 도메인 클래스를 생성.
public class Sequence {
    private final String id;
    private final String prefix;
    private final String suffix;

    public Sequence(String id,String prefix,String suffix) {
        this.id = id;
        this.prefix = prefix;
        this.suffix = suffix;
    }

    public String getId() {
        return id;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getSuffix() {
        return suffix;
    }
}
```


```java
// DB 데이터를 엑세스 처리하는 DAO 인터페이스 생성.
public interface SequenceDao {
    public Sequence getSequence(String sequenceId);
    public int getNextValue(String sequenceId);
}
```

```java
// 실제로는 데이터 엑세스 로직을 DAO인터페이스에 구현하겠지만
// 편의상 하드코딩한다.
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

// Component에 sequenceDao를 넣으면 스프링은 이 클래스를 이용해 POJO를 생성한다.
@Component("sequenceDao")
public class SequenceDaoImpl implements SequenceDao{

    private final Map<String,Sequence> sequenceMap = new HashMap<>();
    private final Map<String, AtomicInteger> values = new HashMap<>();

    public SequenceDaoImpl() {
        sequenceMap.put("IT",new Sequence("IT","30","A"));
        values.put("IT", new AtomicInteger(10000));
    }

    @Override
    public Sequence getSequence(String sequenceId) {
        return sequenceMap.get(sequenceId);
    }

    @Override
    public int getNextValue(String sequenceId) {
        AtomicInteger value = values.get(sequenceId);
        return value.getAndIncrement();
    }
}
```

### Component
```
@Component는 스프링이 발견할 수 있게 POJO에 붙이는 범용 Annotation이다.
스프링에는 Persistence(영속화), Service, Presentation(표현), 3Layer(계층)이 있는데
3Layer 에는 @Repository, @Service, @Controller가 각각 3Layer를 가리키는 Annotation이다.

POJO의 쓰임새를 잘 모르면 그냥 @Component를 붙여도 되지만
특정 용도에 맞는 혜택을 누리려면 구체적으로 명시하는 편이 좋다.

예) @Repository는 발생한 예외를 DataAccessException으로 감싸 던지므로 디비깅 시 유리함.
```

### Annotation을 스캐닝하는 필터로 IoC컨테이너 초기화 하기.
```
기본적으로 스프링은
@Configuration
@Bean
@Component
@Repository
@Service
@Controller
가 달린 클래스를 모두 감지한다.

이때 하나 이상의 포함/제외 Filter를 적용해서 Scanning 과정을 Customizing할 수 있다.

Filter표현식은 4종류가 있다.
annotation , assignable은 각각 필터 대상의 Annotation Type및 Class/Interface 를 지정하며
regex, aspectj는 각각 정규표현식과 AspectJ 포인트컷 표현식으로 Class를 매치하는 용도로 쓰인다.
```

```java
@ComponentScan(
    includeFilters = {
        @ComponentScan.Filter(
            // 이름에 Dao나 Service가 포함된 클래스를 Include
            // Annotation이 달려있지 않은 클래스도 Include한다.
            type = FilterType.REGEX,
            pattern = {"com.example.practicespring.*Dao",
                       "com.example.practicespring.*Service"})
    },
    excludeFilters = {
        @ComponentScan.Filter(
            // @Controller Annotation은 exclude한다.
            type = FilterType.ANNOTATION,
            classes = {org.springframework.stereotype.Controller.class})
    }
)
```

### IoC 컨테이너에서 POJO 인스턴스/빈 가져오기

```java
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext("com.example.practicespring");
        SequenceDao sequenceDao = context.getBean(SequenceDao.class);

        System.out.println(sequenceDao.getNextValue("IT"));
        System.out.println(sequenceDao.getNextValue("IT"));
    }
}

결과
10000
10001
```
