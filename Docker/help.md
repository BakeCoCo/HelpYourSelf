## 이미지 검색
```
docker images
docker images [이미지명]
```
## 이미지 다운로드
```
docker pull [이미지명]
             이미지명 ex) centos, ubuntu, tomcat, mysql ...
```
## 컨테이너 목록 조회
```
docker ps    (실행중인 컨테이너 조회)
docker ps -a (전체 컨테이너 조회)
```
## 이미지를 컨테이너로 생성
```
docker run [이미지명] (이미지가 없으면 다운로드 후 실행)
docker run -dp [포트:포트] --name [설정할이름] [이미지명]
docker run -it --name [설정할이름] [이미지명] -dp [호스트포트:컨테이너포트]
```
## 컨테이너에 접속
```
docker attach [컨테이너명]
or
docker exec [컨테이너명] 
```

## 컨테이너 시작
```docker start [컨테이너명]```

## 컨테이너 중지
```docker stop [컨테이너명]```

## 컨테이너 제거
```docker rm [컨테이너명]```

## 모든 컨테이너 삭제
```
docker container prune
or
docker stop $(docker ps -a -q)
docker rm $(docker ps -a -q)
```

## 컨테이너 강제 삭제 (실행중이어도 삭제함)
```docker rm -f [컨테이너명]```

## 이미지 제거
```docker rmi [이미지명]```

## 로그 보기
```docker logs [컨테이너명]```

## 옵션
```
-i = 상호 입출력
-t = tty를 활성화해서 bash셸을 사용.
-d = container에 접속하지 않아도 background에서 실행된다.
-p = port번호 입력
--name = 설정할 이름 입력
-v = 호스트directory 매핑해서 volume생성
-rm = 종료되면 자동삭제
마지막에 /bin/bash 를 입력해야 container에 bin폴더의 bash를 실행해준다.
```
