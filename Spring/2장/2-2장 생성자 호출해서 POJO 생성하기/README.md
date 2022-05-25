# 2-2 생성자 호출해서 POJO 생성하기

### 과제
```
IoC 컨테이너에서 생성자를 호출해서 POJO 인스턴스/빈을 생성하세요.
생성자를 호출하는건 스프링에서 빈을 생성하는 가장 일반적이면서 직접적인 방법입니다.
자바 new 연산자로 객체를 생성하는 것과 같습니다.
```

```java
// Product
public abstract class Product {
    private String name;
    private double price;

    public Product(){}
    public Product(String name, Double price){
        this.name = name;
        this.price = price;
    }

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
    public double getPrice() {return price;}
    public void setPrice(double price) {this.price = price;}

    @Override
    public String toString() {
        return "Product{" +"name='" + name + '\'' +", price=" + price +'}';
    }
}
```

```java
// Battery
public class Battery extends Product{
    private boolean rechargeable;

    public Battery(boolean rechargeable) {
        this.rechargeable = rechargeable;
    }

    public Battery(String name,Double price) {
        super(name,price);
    }

    public boolean isRechargeable() {
        return rechargeable;
    }

    public void setRechargeable(boolean rechargeable) {
        this.rechargeable = rechargeable;
    }
}
```

```java
// Disc
public class Disc extends Product{
    private int capacity;

    public Disc() {}
    public Disc(String name,Double price) {
        super(name,price);
    }
    public int getCapacity() {return capacity;}
    public void setCapacity(int capacity) {this.capacity = capacity;}
}
```

```java
// ShopConfiguration
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ShopConfiguration {

    @Bean(name ="ABCD")
    public Product aaa(){
        Battery a1 = new Battery("AAA",2.5);
        a1.setRechargeable(true);
        return a1;
    }

    @Bean
    public Product cdrw(){
        Disc d1 = new Disc("CD-RW",1.5);
        d1.setCapacity(700);
        return d1;
    }
}

```

```java
// Main
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(ShopConfiguration.class);
        Product aaa = context.getBean("ABCD",Product.class);
        Product cdrw = context.getBean("cdrw",Product.class);

        System.out.println(aaa);
        System.out.println(cdrw);
    }
}
```


```
결과
Product{name='AAA', price=2.5}
Product{name='CD-RW', price=1.5}

순서

1. Main실행
2. ApplicationContext context는 ShopConfiguration에서 Bean을 가져옴
3. Product aaa는 context에 담긴 ABCD라는 Bean을 사용한다. 
4. @Bean(name="ABCD")로 되어있는 Bean을 사용하는데 Product aaa()로 선언되있다.
5. Prodct aaa 안의 Battery는 2개의 파라미터 String, Double형을 받을때 super(name,price)로 Product를 방문한다.
6. Product(String name, Double price)는 입력받은 name과 price를 return 해준다.
7. Battery에 담긴 인자는 ("AAA", 2.5) 이기 때문에 name="AAA" price=2.5로 세팅된다.
8. cdrw도 동일

Product cdrw는 context에 담긴 cdrw라는 Bean을 사용한다.
```
