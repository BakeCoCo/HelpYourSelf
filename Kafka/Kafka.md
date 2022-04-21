## Apache Kafka

## APACHE KAFKA QUICKSTART
https://kafka.apache.org/quickstart


Zookeeper는 아직까지 사용해야 하나보다


## 카프카 관련 유튜브

https://www.youtube.com/watch?v=VJKZvOASvUA

https://github.com/AndersonChoi/tacademy-kafka

![image](https://user-images.githubusercontent.com/58055835/161549778-62459d5b-e1d2-455b-bc9f-9ada043e4bea.png)

따로 따로 파이프라인을 만들어야 해서 코드의 복잡성이 증가


![image](https://user-images.githubusercontent.com/58055835/161549746-49c92f24-ebb1-44b9-ac4a-dded3fc1eab9.png)

무중단으로 스케일 아웃 가능


## Kafka Broker & Cluster

* 주키퍼와 연동 필요(~2.5.0 버젼)
  * 주키퍼의 역할 : 메타데이터(브로커id, 컨트롤러id 등) 저장
* n개 브로커 중 1대는 컨트롤러(Controller)기능 수행

* 컨트롤러 : 각 브로커에게 담당파티션 할당 수행
  * 브로커 정상 동작 모니터링 관리.
  * 누가 컨트롤러 인지는 주키퍼에 저장

### Record
```
객체를 프로듀서에서 컨슈머로 전달하기 위해 Kafka 내부에 byte형태로 저장할 수 있도록 직렬화/역직렬화 하여 사용.
기본 제공 직렬화 class : StringSerializer, ShortSerializer등
커스텀 직렬화 class : Custom Object 직렬화/역질렬화 가능


new ProducerRecord<String,String>("topic","key","message");

ConsumerRecords<String,String> records = consumer.poll(1000);
for(ConsumerRecord<String,String> record : records){
  ....
}

// Key는 Null, Value는 JSON으로 된 자체 형식으로 사용 중

```
![image](https://user-images.githubusercontent.com/58055835/161551827-ca073b8c-74b5-44d0-80cc-ac4ebfcc76e7.png)

Consumer A가 9번을 가져감
Consumer B가 11번을 가져감

이 의미는
Consumer A는 0~9까지 가져갔다
Consumer B는 0~11번까지 가져갔다

이 의미는
브로커에 있는 레코드는 여러번 사용 가능
브로커가 주는게 아닌 컨슈머가 요청하여 가져가는것

![image](https://user-images.githubusercontent.com/58055835/161552417-f61665fc-409d-487a-aa9d-2d94d095e3f1.png)

메시지는 세그먼트 파일에 저장됨
브로커나 토픽에 설정된 용량, 시간에 따라 압축,삭제가 됨

![image](https://user-images.githubusercontent.com/58055835/161552662-23296e92-e52f-4981-b022-b7d04006e886.png)

컨슈머 1대당 처리하는 시간은 한정적
각 파티션 마다 컨슈머 1개 

![image](https://user-images.githubusercontent.com/58055835/161553102-501258a8-c0d5-457a-853c-7f85b4fe61fd.png)

![image](https://user-images.githubusercontent.com/58055835/161553238-d3d8d80f-3e46-4d37-81c5-7493624aef4d.png)

실시간으로 보기 위해서는 엘라스틱서치를 사용
이전 데이터를 확인하기 위해서는 하둡을 사용
이러한 사용 목적으로 분류해서 컨슈머 그룹을 분리해서 사용 가능

만약 하둡에 컨슈머의 적재지연이 발생하더라도
엘라스틱서치에 적재하는 컨슈머의 동작에는 이슈가 없음.


![image](https://user-images.githubusercontent.com/58055835/161553573-8fefaed8-eda1-4c1c-8c7c-992034e509ac.png)


```
카프카 토픽 생성 쉘 스크립트
$ bin/kafka-topics.sh --bootstrap-server localhost:9092 --create --topic topic_name --partitions 3 --replication-factor 3
$ bin/kafka-topics.sh --부트스트랩-서버   로컬호스트:9092 --생성    --토픽  토픽명     --파티션 갯수    --레플리케이션 3개를 복제한다
```

![image](https://user-images.githubusercontent.com/58055835/161553677-bc4c03af-4140-4ef0-b4ae-16ff2d643524.png)

![image](https://user-images.githubusercontent.com/58055835/161553726-d8a71b8a-36e5-4e32-af70-15e127b050a9.png)

리더 파티션이 장애가 나면
다른 브로커에 있는 파티션에서 리더파티션을 선출함

리더파티션이 0~100까지 있는데 장애가남
팔로워파티션은 0~90까지 복제가 되어있다고 친다면
ISR이 아닌 상태에서 장애가 나면
unclean.leader.election.enable 는 기본 false 이기 때문에 리더파티션의 레코드를 복제하기 전까지는 리더를 선출하지 않는다.

1개의 Rack에 1개의 Broker를 넣는것이 좋음

장애허용은 중요함.

## Kafka 핵심 요소 정리

* Broker    : 카프카 애플리케이션 서버 단위
* Topic     : 데이터 분리 단위, 다수 파티션 보유
* Partition : 레코드를 담고 있음. 컨슈머 요청시 레코드 전달
* Offset    : 각 레코드당 파티션에 할당된 고유 번호
* Consumer  : 레코드를 Polling하는 애플리케이션
  * Consumer Group  : 다수 컨슈머 묶음
  * Consumer Offset : 특정 컨슈머가 가져간 레코드의 번호
* Producer  : 레코드를 브로커로 전송하는 애플리케이션
* Replication : 파티션 복제 기능
  * ISR(In-Sync-Replica) : 리더 + 팔로워 파티션의 sync가 된 묶음  (default 옵션 : unclean.leader.election.enable = false )
* Rack-awareness : Server rack 이슈에 대응


## Kafka Client

* Kafka와 데이터를 주고받기 위해 사용하는 Java Library
```
<!-- https://mvnrepository.com/artifact/org.apache.kafka/kafka-clients -->
<dependency>
    <groupId>org.apache.kafka</groupId>
    <artifactId>kafka-clients</artifactId>
    <version>3.1.0</version>
</dependency>

<!-- https://mvnrepository.com/artifact/org.apache.kafka/kafka -->
<dependency>
    <groupId>org.apache.kafka</groupId>
    <artifactId>kafka_2.13</artifactId>
    <version>3.1.0</version>
</dependency>

<!-- https://mvnrepository.com/artifact/org.apache.kafka/kafka-streams -->
<dependency>
    <groupId>org.apache.kafka</groupId>
    <artifactId>kafka-streams</artifactId>
    <version>3.1.0</version>
</dependency>
```


![image](https://user-images.githubusercontent.com/58055835/161890309-a476a46e-a395-4a5d-974f-0a3af22f1826.png)



![image](https://user-images.githubusercontent.com/58055835/161890323-5e944797-6ecd-4436-a554-8119f2430b85.png)



