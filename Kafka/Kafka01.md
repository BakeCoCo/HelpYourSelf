## docker로 kafka 실행하는법


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

확인후 ```docker container exec -it kafka bash``` 입력하여 bash로 들어감

```
kafka-topics.sh --create --topic quickstart-events --bootstrap-server localhost:9092
```
입력하면 

quickstart-events 라는 TOPIC이 생성됨

확인 명령어
```
kafka-topics.sh --describe --topic quickstart-events --bootstrap-server localhost:9092
```

![image](https://user-images.githubusercontent.com/58055835/164142003-9be020dd-92a2-47ea-bfcb-633f3b2b4c78.png)

Kafka Producer에 입력하기
`kafka-console-producer.sh --topic quickstart-events --bootstrap-server localhost:9092`
입력후 아무거나 입력

`kafka-console-consumer.sh --topic quickstart-events --from-beginning --bootstrap-server localhost:9092` 입력하면

자신이 producer console에서 입력한것을 확인할 수 있다.

