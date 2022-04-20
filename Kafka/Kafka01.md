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

### kafka 명령어 
```
Linux에서는 sh
Windows에서는 bat

kafka-topics.sh --create --topic test --bootstrap-server localhost:9092 --replication-factor 1 --partitions 3
kafka-console-producer.sh --bootstrap-server localhost:9092 --topic test
kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic test --from-beginning
kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic test -group testgroup --from-beginning
kafka-console-consumer.sh --bootstrap-server localhost:9092 --list
kafka-console-consumer.sh --bootstrap-server localhost:9092 --group testgroup --describe
kafka-console-consumer.sh --bootstrap-server localhost:9092 --group testgroup --topic test --reset-offsets --to-earliest --execute
kafka-console-consumer.sh --bootstrap-server localhost:9092 --group testgroup --topic test1 --reset-offsets --to-offset 10 --execute

```

### kafka config 명령어
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

## Ex) config/server.properties
```
listeners=PLAINTEXT://:9092
advertised.listeners=PLAINTEXT://{ipAddress}:9092
```



![image](https://user-images.githubusercontent.com/58055835/164142003-9be020dd-92a2-47ea-bfcb-633f3b2b4c78.png)


