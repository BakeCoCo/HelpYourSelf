# JMX (Java Management eXtensions)

응용 프로그램/객체/장치 및 서비스지향 네트워크 등을 감시관리를 위한 도구를 제공하는 API

MBean이라는 객체로 표현된다.


### jconsole로 모니터링

mBean으로 JavaBean 오브젝트를 통해 애플리케이션, 디바이스, 서비스를 원격으로 제어 가능

mBean들은 mBean 서버에 등록되며, mBean 서버는 리소스에 접근하는 모든 원격 매니저를 관리한다.

jconsole은 JDK/bin 폴더에 있다.

C:/Program Files/Java/jdk1.8.0_311/bin/jconsole.exe

실행하면 다음 화면과 같이 나타난다.

![image](https://user-images.githubusercontent.com/58055835/168519213-af1a7166-64cc-4707-9016-165b1c76dd7e.png)

현재 실행중인 Local 프로세스목록

실행하면 이것저것 많은데 `MBeans`를 사용할 것이다.

![image](https://user-images.githubusercontent.com/58055835/168519434-0f35d00b-ee45-4f8c-8ac4-9e15278adf41.png)

현재 mbeanController 이라는 MBeans가 있고 그 아래 `Operations`로 원격으로 메소드를 실행할 수 있다.

setX와 setY로 값을 세팅하고 add, sub, div, mul을 통해 더하고 빼고 곱하고 나누기를 출력할 수 있게 만들었다.

```java
// MbeanExporter
import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jmx.export.MBeanExporter;

@Configuration // Configuration등록
public class MbeanExporter{
	@Bean // Bean
	public MBeanExporter mBeanExporter(MbeanController mbeanController) {
		MBeanExporter mx = new MBeanExporter();
		Map beans = new HashMap();
    
    // [ put을 통해 Jconsole에 MBeans를 SET한다. ]
		beans.put("mbeanController:name=MbeanController", mbeanController);
    beans.put("testMBean:name=TESTBEANS", mbeanController);
		mx.setBeans(beans);
		return mx;
	}
}
```

![image](https://user-images.githubusercontent.com/58055835/168520359-74fa1e7a-525b-446e-aed9-d3d485a27b7a.png)



```java
// MbeanController
import org.springframework.stereotype.Service;

@Service("MbeanController") //Service를 등록
public class MbeanController implements AplusConfigurationMBean{
	
	private String name = "LHJ";
	private int valX = 0;
	private int valY = 0;
	
  // Name은 일부러 get 제거함 name 메소드를 호출해서 가져오게끔
	public void setName(String name) {	this.name = name;	}
	public int getValX() {	return valX;  }
	public int getValY() {	return valY;	}
	public void setValX(int valX) {	this.valX = valX;	}
	public void setValY(int valY) {	this.valY = valY;	}
	
	@Override
	public String name() {		return "너의 이름은 : "+name;	}
	public int add() {		return valX+valY;	}
	public int sub() {		return valX-valY;	}
	public int mul() {		return valX*valY;	}
	public int div() {		return valX/valY;	}
}
```
```java
// interface
// 파일의 이름 뒤엔 MBean이 들어가야 한다
public interface AplusConfigurationMBean {

	// 인자값을 아무것도 안받는 이유는 Controller에서 private으로 X,Y값과 NAME을 SET해준후 출력하게 만들었기 때문이다.
	public int add();
	public int sub();
	public int mul();
	public int div();
	
	public String name();
	
}
```
