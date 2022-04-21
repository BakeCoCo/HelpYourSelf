## docker로 kafka 실행하는법
https://galid1.tistory.com/792

### docker-compose.yml 파일 생성
```
version: '2'
services:
  zookeeper:
    image: wurstmeister/zookeeper
    container_name: zookeeper
    ports:
      - "2181:2181"
  kafka:
    image: wurstmeister/kafka:2.12-2.5.0
    container_name: kafka
    ports:
      - "9092:9092"
    environment:
      KAFKA_ADVERTISED_HOST_NAME: 127.0.0.1
      KAFKA_ZOOKEEPER_CONNECT: zookeeper:2181
    volumes:
      - /var/run/docker.sock:/var/run/docker.sock
```

해당 파일이 들어있는 경로의 cmd창에서 `docker-compose up -d` 입력

다음과 같이 다운로드가 완료되고

![image](https://user-images.githubusercontent.com/58055835/164141679-144326a8-4435-473e-885e-b23584d376f8.png)

```
docker container ls
```
명령어로 docker container가 실행중인지 확인

```docker container exec -it kafka bash```

입력하여 bash로 들어감

### 기본세팅 (주석해제)
## config/server.properties
```
listeners=PLAINTEXT://:9092
advertised.listeners=PLAINTEXT://{ipAddress}:9092
```
## config/zookeeper.properties
```
listeners=PLAINTEXT://:9092
```

### kafka config설정
```
broker.id : 정수로 된 브로커 번호. 클러스터 내 고유번호로 지정
listeners : kafka 통신에 사용되는 host:port
advertiese.listeners : kafka client가 접속할 host:port
log.dirs : 메시지를 저장할 디스크 디렉토리, 세그먼트가 저장됨
log.segment.bytes : 메시지가 저장되는 파일의 크기 단위
log.retention.ms : 메시지를 얼마나 보존할지 지정. 닫힌 세그먼트를 처리
zookeeper.connect : 브로커의 메타데이터를 저장하는 주키퍼의 위치
auto.create.topics.enable : 자동으로 토픽 생성여부
num.partitions : 자동생성된 토픽의 default partition 개수
message.max.bytes : kafka broker에 쓰려는 메시지 최대 크기
```


### kafka 명령어 
```
Linux에서는 sh
Windows에서는 bat


.\bin\windows\zookeeper-server-start.bat config\zookeeper.properties
.\bin\windows\kafka-server-start.bat config\server.properties
.\bin\windows\kafka-topics.bat --create --topic test --bootstrap-server localhost:9092 --replication-factor 1 --partitions 3
.\bin\windows\kafka-console-producer.bat --bootstrap-server localhost:9092 --topic test
.\bin\windows\kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic test --from-beginning
.\bin\windows\kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic test -group testgroup --from-beginning
.\bin\windows\kafka-console-consumer.bat --bootstrap-server localhost:9092 --list
.\bin\windows\kafka-console-consumer.bat --bootstrap-server localhost:9092 --group testgroup --describe
.\bin\windows\kafka-console-consumer.bat --bootstrap-server localhost:9092 --group testgroup --topic test --reset-offsets --to-earliest --execute
.\bin\windows\kafka-console-consumer.bat --bootstrap-server localhost:9092 --group testgroup --topic test1 --reset-offsets --to-offset 10 --execute
.\bin\windows\kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic test --property print.key=true --property key.separator="-"
```

# Kafka 커맨드 라인 명령어

## 1. Topic 명령어

### (1) 기본 Topic 생성
```
.\bin\windows\kafka-topics.bat
--create
--topic [토픽명]
--bootstrap-server [아이피:호스트]
```

### (2) Topic 생성 옵션
```
.\bin\windows\kafka-topics.bat
--create
--bootstrap-server [아이피:호스트]
--topic [토픽명]
--partitions [파티션 갯수]
--replication-factor [브로커 복제 개수] 
--config retention.ms=[토픽의 데이터를 유지할 시간(단위:ms)]
```

### (3) Topic 조회
```
.\bin\windows\kafka-topics.bat
--bootstrap-server [아이피:호스트]
--list
```

### (4) Topic 상세 조회
```
.\bin\windows\kafka-topics.bat
--bootstrap-server [아이피:호스트]
--topic
--describe
```

### (5) Topic 수정
#### --alter 이하의 설정들을 모두 개별적으로 수정 가능
```
.\bin\windows\kafka-topics.bat
--bootstrap-server [아이피:호스트]
--alter
--partitions [변경할 파티션 수]
```

#### --add-config 옵션을 사용하면 기존것을 merge한다.
```
.\bin\windows\kafka-topics.bat
--bootstrap-server [아이피:호스트]
--alter
--add-config retention.ms=[토픽의 데이터를 유지할 시간(단위:ms)]
```

#### --delete-config를 사용한 토픽 설정 삭제
```
.\bin\windows\kafka-topics.bat
--bootstrap-server [아이피:호스트]
--alter
--delete-config retention.ms
```

### (6) json 파일을 활용하여 삭제
`delete-topic.json`
``` 
{
  "partitions":
  [
    {
      "topic" : "[삭제할 토픽명]",
      "partition" : "[삭제할 파티션 번호]",
      "offset" : "[처음부터 삭제할 offset 번호]"
    }
  ],
  "version" : 1
}
```
```
.\bin\windows\kafka-topics.bat
--bootstrap-server [아이피:호스트]
--offset-json-file delete-topic.json
```

## 2. Producer 명령어

### (1) key가 없고 value만 있는 Producer
```
.\bin\windows\kafka-console-producer.bat
--bootstrap-server [아이피:호스트]
--topic [토픽명]
```

### (2) key와 value가 있는 Producer
```
.\bin\windows\kafka-console-producer.bat
--bootstrap-server [아이피:호스트]
--topic [토픽명]
--property parse.key=true
--property key.separator=":"
```

## 3. Consumer 명령어

### (1) key없이 value만 보여주는 Consumer
`--from-beginning` : Topic의 첫 Data부터 읽는다.
```
.\bin\windows\kafka-console-consumer.bat
--bootstrap-server [아이피:호스트]
--topic [토픽명]
--from-beginning
```

### (2) key와 value를 보여주는 Consumer
```
.\bin\windows\kafka-console-consumer.bat
--bootstrap-server [아이피:호스트]
--topic [토픽명]
--property print.key=true
--property key.separator="-"
--group [그룹명 (그룹명이 없는 경우 새로 생성) ]
--from-beginning
```

## 4. Consumer Group

### (1) 그룹에 속하는 Consumer List 조회
```
.\bin\windows\kafka-consumer-groups.bat
--bootstrap-server [아이피:호스트]
--list
```

### (2) Consumer Group이 어떤 Topic의 Data를 처리하는지 조회

그룹명, 토픽명, 토픽의 파티션 번호, 최신 오프셋, 컨슘한 오프셋, 지연-랙(LAG), 아이디를 알 수 있다.
```
.\bin\windows\kafka-consumer-groups.bat
--bootstrap-server [아이피:호스트]
--group [그룹명]
--describe
```


