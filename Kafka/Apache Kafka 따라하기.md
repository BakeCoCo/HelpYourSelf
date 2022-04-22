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
## ☆☆명령어 하나에 CMD창 하나☆☆
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
  private static String BOOTSTRAP_SERVERS = "localhost:9092";	// Kafka Cluster 주소 [ 클러스터 = 브로커구성 ]
  
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
	private static String TOPIC_NAME = "test";	// 
	private static String BOOTSTRAP_SERVERS = "localhost:9092";	// Kafka Cluster 주소 [ 클러스터 = 브로커구성 ]
	
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

## Simple Consumer Code

```java
/*
* Broker에 저장된 Message를 Poll해서 가져온다.
**/
public class Main {
	private static String TOPIC_NAME = "test";	// 토픽명
	private static String GROUP_ID = "testgroup";	// 그룹명
	private static String BOOTSTRAP_SERVERS = "localhost:9092";	// Kafka Cluster 주소 [ 클러스터 = 브로커구성 ]

	public static void main(String[] args) {

		Map<String, Object> props = new HashMap<String,Object>();
		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
		props.put(ConsumerConfig.GROUP_ID_CONFIG, GROUP_ID);
		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
		
		KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
		
		// Consumer subscribe(구독)
		consumer.subscribe(Arrays.asList(TOPIC_NAME));
		
	 	while (true) {
	 		ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(1));
	 		for (ConsumerRecord<String, String> record : records) {
	 			System.out.println("Topic : "+record.value());
			}
		}
	}
}
```


### Consumber subscribes 

```
// 1개 토픽만 구독
consumer.subscribe(Arrays.asList(TOPIC_NAME));

// n개 토픽 구독
// 정규 표현식(regex)를 통한 토픽 구독
consumer.subscribe(Pattern.compile("test.*"));

// 특정 토픽의 파티션 구독
// Key를 포함한 Record를 Consume할 때, 특정 파티션을 할당하고 싶으면
consumer.assign(Collections.singleton(new TopicPartition("test", 1)));
```

### Consumer Options

-- 필수옵션
```
BOOTSTRAP_SERVERS_CONFIG : 카프카 클러스터에 연결하기 위한 브로커 목록

GROUP_ID_CONFIG : 컨슈머 그룹 ID

KEY_DESERIALIZER_CLASS_CONFIG : 메시지 Key 역직렬화에 사용되는 클래스

VALUE_DESERIALIZER_CLASS_CONFIG : 메시지 Key 역직렬화에 사용되는 클래스
```

-- 선택옵션 -Default값 존재
```
ENABLE_AUTO_COMMIT_CONFIG : 자동 오프셋 커밋 여부
[true = 일정간격(AUTO_COMMIT_INTERVAL_MS_CONFIG) 마다 poll() 메서드를 호출시 자동 commit]
[commit 관련 코드를 작성할 필요가 없어 편리함]
[속도가 가장 빠름]
[데이터 중복, 유실이 발생할 수 있음 = (은행,카드 X),(센서,GPS O)]

AUTO_COMMIT_INTERVAL_MS_CONFIG : 자동 오프셋 커밋일 때 interval 시간

AUTO_OFFSET_RESET_CONFIG : 신규 컨슈머그룹일때 읽을 파티션의 오프셋 위치

CLIENT_ID_CONFIG : 클라이언트 식별값

MAX_POLL_RECORDS_CONFIG : poll() 메서드 호출시 반환되는 레코드의 최대 갯수

SESSION_TIMEOUT_MS_CONFIG : 컨슈머가 브로커와 연결이 끊기는 시간
```


### 데이터 중복이 발생시키는 방법

### ENABLE_AUTO_COMMIT_CONFIG = true

```java
public class Main {
	private static String TOPIC_NAME = "test";
	private static String GROUP_ID = "testgroup";
	private static String BOOTSTRAP_SERVERS = "localhost:9092";

	public static void main(String[] args) {
		
		//Properties configs = new Properties();
		Map<String, Object> props = new HashMap<String,Object>();
		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
		props.put(ConsumerConfig.GROUP_ID_CONFIG, GROUP_ID);
		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
		props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, true);
		props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG,60000); // 60,000 ms 마다 commit
		
		KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
		
		consumer.subscribe(Arrays.asList(TOPIC_NAME));
	 	while (true) {
	 		ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(1000));
	 		for (ConsumerRecord<String, String> record : records) {
	 			System.out.println("group : testgroup, Topic : test "+record.value());
			}
		}
	}
}
```

![image](https://user-images.githubusercontent.com/58055835/164573389-fc10b04f-da2f-4b62-852f-0e43a3992436.png)

### ★Reboot Consumer★

- 60초 마다 Commit 하기 때문에 Commit이 안된 데이터가 중복 출력됨

- 데이터 중복처리 

--- 주문, 결제, 문자 처럼 데이터가 중복처리되면 안되는것들에선 사용 X

### ★데이터 중복을 막을 수 있는 방법★
- 오토 커밋을 사용하지만 컨슈머가 죽지 않도록 잘 보살핀다.

--> ★불가능★ 서버/애플리케이션은 언젠가 모두 죽어..... ex)배포

- 오토 커밋을 사용하지 않는다.

--> Kafka Consumer의 `commitSync()`, `commitAsync()` 함수를 사용




### ENABLE_AUTO_COMMIT_CONFIG = false

```java
........동일생략
props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);

while (true) {
	ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(1000));
	for (ConsumerRecord<String, String> record : records) {
		System.out.println("group : testgroup, Topic : test "+record.value());
	}
	Map<Topi
	
	try {
		consumer.commitSync();
	} catch (CommitFailedException e) {
		System.err.println("commit failed");
	}
}
```

1)) commitSync() : 동기 커밋
- ConsumerRecord 처리 순서를 보장함
- 가장 느림
- poll() 메서드로 반환된 ConsumerRecord의 마지막 Offset을 커밋
- Map<TopicPartition, OffsetAndMetadata> 를 통해 Offset지정 커밋 가능 [어떻게하지]


2)) commitAsync() : 비동기 커밋
- 동기 커밋보다 빠름
- 중복 발생 가능
- 일시적인 통신문제로 이전 Offset보다 이후 Offset이 먼저 커밋될 때 ConsumerRecord 처리 순서를 보장하지 못함
- 처리 순서가 중요한 서비스(주문, 재고관리 등)에서는 사용이 제한됨

