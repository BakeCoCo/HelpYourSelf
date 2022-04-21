# Windows 기준

[Apache Kafka Downloads](https://kafka.apache.org/downloads)

### 원하는 위치에 압축 해제 (단, 경로명이 한글이나 너무 길면 문제 있으니 알아서)

### config폴더의 properties를 수정한다.

#### server.properties
```
.....이하생략
############################# Socket Server Settings #############################

# The address the socket server listens on. It will get the value returned from 
# java.net.InetAddress.getCanonicalHostName() if not configured.
#   FORMAT:
#     listeners = listener_name://host_name:port
#   EXAMPLE:
#     listeners = PLAINTEXT://your.host.name:9092
#listeners=PLAINTEXT://:9092 <-주석 해제

# Hostname and port the broker will advertise to producers and consumers. If not set, 
# it uses the value for "listeners" if configured.  Otherwise, it will use the value
# returned from java.net.InetAddress.getCanonicalHostName().
#advertised.listeners=PLAINTEXT://your.host.name:9092 <- 주석 해제 및 [your.host.name => localhost]로 변경
이하생략..........
```

### Kafka압축해제한 폴더에서 CMD창 연다.
## 명령어 하나에 CMD창 하나
#### Windows에서는 sh파일이 아닌 bat파일을 사용해야 해서 다음과 같이 입력.

### Zookeeper Server Start, Kafka Broker Server Start
```
.\bin\windows\zookeeper-server-start.bat config\zookeeper.properties
.\bin\windows\kafka-server-start.bat config\server.properties
```

### Topic Create
#### 이제부터는 따로 설정파일 지정할 필요 없으니
```
cd bin\windows
// topic 생성
kafka-topics.bat --bootstrap-server localhost:9092 --create --topic test --replication-factor 1 --partitions 3

// topic 조회
kafka-topics.bat --bootstrap-server localhost:9092 --list
```

### Producer Start
```
// 기본적인 Producer
kafka-console-producer.bat --bootstrap-server localhost:9092 --topic test

// Key와 Value를 구분해주는 Producer
kafka-console-producer.bat --bootstrap-server localhost:9092 --topic test --property parse.key=true --property key.separator=":"
```

### Consumer Start
```
// 컨슈머 그룹 자동 생성
kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic test -group testgroup --from-beginning

// 기본적인 Consumer
kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic test --from-beginning

// Key와 Value를 구분해주는 Consumer
kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic test --property print.key=true --property key.separator="-"

// Key와 Value를 구분해주고 지정된 파티션만 출력해주는 Consumer
kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic test --partition [숫자] --property print.key=true --property key.separator="-"
```

### Consumer Group
```
// group 상세 조회
kafka-consumer-groups.bat --bootstrap-server localhost:9092 --group testgroup --describe

// group offset 리셋
kafka-consumer-groups.bat --bootstrap-server localhost:9092 --group testgroup --topic test --reset-offsets --to-earliest --execute

// group offset 10으로 지정
kafka-consumer-groups.bat --bootstrap-server localhost:9092 --group testgroup --topic test --reset-offsets --to-offset 10 --execute
```

# JAVA

## Spring Boot + Maven

[Maven Kafka](https://mvnrepository.com/artifact/org.apache.kafka)

Maven 설정
```
<!-- https://mvnrepository.com/artifact/org.apache.kafka/kafka -->
<dependency>
    <groupId>org.apache.kafka</groupId>
    <artifactId>kafka_2.13</artifactId>
    <version>3.1.0</version>
</dependency>
```

## 테스트는 Kafka Broker Server와 Zookeeper Server를 켠 상태로 테스트 한다.

## Simple Producer Code

```java
/* Simple Producer Code
*  1초마다 TOPIC_NAME과 data를 Broker에 Send
*/
public class Main {
  
  private static String TOPIC_NAME = "test";      // 생성한 토픽명
  private static String BOOTSTRAP_SERVERS = "localhost:9092";
  
  public static void main(String[] args) {
    Map<String,Object> configs = new HashMap<String,Object>();
    configs.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
    configs.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
    configs.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
    
    KafkaProducer<String, String> producer = new KafkaProducer<>(configs);
    
    for (int index = 0; index < 10; index++) {
      
      String data = "This is record " + index;
      
      ProducerRecord<String, String> record = new ProducerRecord<>(TOPIC_NAME, data);
      
      try {
        producer.send(record);
        System.out.println("Send to "+ TOPIC_NAME +" | data : "+data);
        Thread.sleep(1000);
      } catch (Exception e) {
        System.out.println(e);
      }
    }
  }
}
```

### 실행하면 1초마다 Consumer에 "This is record 0~10" 까지가 출력된다.

### --- 필수옵션
```
BOOTSTRAP_SERVERS_CONFIG : 카프카 클러스터에 연결하기 위한 브로커 목록

KEY_SERIALIZER_CLASS_CONFIG : 메시지 Key 직렬화에 사용되는 클래스

VALUE_SERIALIZER_CLASS_CONFIG : 메시지 Value 직렬화에 사용되는 클래스
```

### --- 선택옵션 - default값 존재
```
ACKS_CONFIG : 레코드 전송 신뢰도 조절(replication)
[0 = 가장 빠름, 데이터 유실 가능성 있음]
[1 = 보통, default값, 데이터 유실 가능성 있음]
[-1,all = 느림, 메시지 전달 손실 가능성 없음]

COMPRESSION_TYPE_CONFIG : snappy, gzip, lz4중 하나로 압축하여 전송.

RETRIES_CONFIG : 클러스터 장애에 대응하여 메시지 전송을 재시도하는 횟수.

BUFFER_MEMORY_CONFIG : 브로커에 전송될 메시지의 버퍼로 사용 될 메모리 양.

BATCH_SIZE_CONFIG : 여러 데이터를 함께 보내기 위한 레코드의 크기.

LINGER_MS_CONFIG : 현재의 배치를 전송하기 전까지 기다리는 시간.

CLIENT_ID_CONFIG : 어떤 클라이언트인지 구분하는 식별자.

PARTITIONER_CLASS_CONFIG : 파티션을 구분하는 식별자.
```



## PARTITIONER_CLASS_CONFIG 를 사용하여 파티션을 구분하여 Record를 Send
```java
public class CustomPartitionDivide implements Partitioner{
	@Override
	public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes, Cluster cluster) {
		
		/*
		 * 레코드에 메시지 KEY를 지정하지 않은 경우 -> 비정상적인 데이터로 간주하고 InvalidRecordException 발생
		 * */
		if(keyBytes == null) {
			throw new InvalidRecordException("You Need message Key : CustomPartitionDivide.java");
		}
		
		/*
		 * 메시지 KEY에 따라 레코드가 전달될 파티션을 지정
		 * */
		
		String keyName = (String)key;
		if(keyName.equals("AA")) {
			return 0;
		}else if(keyName.equals("BB")) {
			return 1;
		}else if(keyName.equals("CC")) {
			return 2;
		}
		
		/*
		 * 메시지 KEY가 존재하지만 "TEST", "SAFE", "APLUS"가 아닌 경우
		 * 해시값을 이용하여 특정 파티션에 매칭되도록 한다.
		 * */
		List<PartitionInfo> partitions = cluster.partitionsForTopic(topic);
		int pNo = partitions.size();
		return Utils.toPositive(Utils.murmur2(keyBytes)) % pNo;
	}

	@Override
	public void close() {	}
	
	@Override
	public void configure(Map<String, ?> configs) {	}
}
```



## Simple Producer Code with Key/Value
```java
/*
* console창에 직접 입력하여 실험
*/
public class Main {
	private static String TOPIC_NAME = "test";
	private static String BOOTSTRAP_SERVERS = "localhost:9092";
	
	public static void main(String[] args) {
		Map<String,Object> configs = new HashMap<String,Object>();
		
		configs.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
		configs.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		configs.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		configs.put(ProducerConfig.PARTITIONER_CLASS_CONFIG, CustomPartitionDivide.class);
		KafkaProducer<String, String> producer = new KafkaProducer<>(configs);
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		ProducerRecord<String, String> record = null;
		
		String []arr = new String[3];
		arr[0]="AA";
		arr[1]="BB";
		arr[2]="CC";
		try {
			while(true) {
				String msg = ""+br.readLine();
				int len = msg.length();
				// ProducerRecord<>( 토픽명, 파티션번호, 메시지 )
				// 파티션 번호는 PARTITIONER_CLASS_CONFIG로 인해 CustomPartitionDivide에서 설정됨
				record = new ProducerRecord<>(TOPIC_NAME, arr[len%3],arr[len%3]+" : "+msg);
				producer.send(record);
			}
		}catch(Exception e) {
			System.out.println(e);
		}
	}
}
```

### 실행하면 문자의 길이만큼 KEY를 구분하여 구분된 KEY값 마다 Partition에 출력해준다.
```
// 제대로 실행되는지 확인하기 위해선 지정된 파티션만 출력해주는 Consumer를 실행해야 한다.
// 지정된 파티션만 출력
kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic test --partition [숫자]

// 지정된 파티션 및 Key와 Value를 구분하여 출력
kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic test --partition [숫자] --property print.key=true --property key.separator="-"
```
