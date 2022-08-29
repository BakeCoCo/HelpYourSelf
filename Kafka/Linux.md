# 1. Instance에 Java 설치.

### 설치경로

`/usr/local/src/ 에 설치`


### Linux

`sudo yum install -y java-1.8.0-openjdk-devel.x86_64`

`java -version`


### Ubuntu

`sudo apt-get install openjdk-8-jdk`

`java -version`


# 2. Kafak 설치

wget 으로 다운로드 (Source가 아닌 Binary로 받아야함)

`wget https://downloads.apache.org/kafka/3.2.1/kafka_2.12-3.2.1.tgz` Binary

`wget https://archive.apache.org/dist/kafka/3.2.0/kafka-3.2.0-src.tgz` Source

`tar xvf kafka-3.2.0-src.tgz` 압축해제

`cd kafka-3.2.0-src` 경로 이동

# 3. Kafka 실행준비

`vim ~/.bashrc`

`a` 눌러 Insert Mode로 마지막부분에

`export KAFKA_HEAP_OPTS="-Xmx400m -Xms400m"` 추가

`ESC누르고 :wq (저장)`

`source ~/.bashrc`

`echo $KAFKA_HEAP_OPTS`

# 4. Kafka 브로커 실행 옵션 설정
```
vim config/server.properties
#listeners=PLAINTEXT://:9092 -- #(주석)제거
#advertised.listeners=PLAINTEXT://your.host.name:9092 -- your.host.name에 ip(localhost or 사용자ip) 로 변경후 #(주석)제거

저장
```

# 5. Zookeeper 실행

```
kafka 디렉토리 위치에서
bin/zookeeper-server-start.sh config/zookeeper.properties --실행
bin/zookeeper-server-start.sh -daemon config/zookeeper.properties --daemon옵션 추가시 백그라운드로 실행
```

`jps` 입력하여 (실행되고 있는 프로그램 출력)

`Jps`, `QuorumPeerMain` 출력되면 정상 실행중...

# 6. Kafka 실행

```
kafka 디렉토리 위치에서
bin/kafka-server-start.sh config/server.properties --실행
bin/kafka-server-start.sh -daemon config/server.properties --daemon옵션 추가시 백그라운드로 실행
```

`jps` 입력하여

`Jps`, `QuorumPeerMain`, `kafka` 출력되면 카프카 브로커도 정상 실행중...


`tail -f logs/server.log` 커맨드를 통해 카프카 브로커의 로그를 실시간으로 확인할 수 있다.

`Ctrl+C`를 통해 빠져나올수 있다.


# 7. 로컬 컴퓨터에서 카프카와 통신 확인하기

로컬 컴퓨터에서도 kafka를 설치 후 통신을 시도해본다.

로컬에는 이미 Kafka가 설치되어있다.

나는 Window를 사용하니 
```
CMD 실행후
kafka설치된 경로로 이동
/bin/windows/kafka-broker-api-versions.bat --bootstrap-server 192.168.0.51:9092 (입력한IP:포트번호) 실행하면

192.168.0.51:9092 (id: 0 rack: null) -> ( ~~~~~이것저것~~~~~~ ) 뜨면 성공
```
