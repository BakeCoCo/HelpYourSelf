## 기초명령어
```
docker run -it --name test01 ubuntu
docker를 run한다 -it입출력 --name이름정한다 test01로 ubuntu이미지를 사용해서
```
## 우분투는 아무것도 안깔려있다.
```
apt-get install sudo  // sudo
apt-get install yum   // yum
apt-get install vi    // vi
apt-get install vim   // vim 이 vi편집기.. vi는 뭐지..?
```

## 근데 왜 안깔리냐
```
sudo apt-get install yum
Reading package lists... Done
Building dependency tree
Reading state information... Done
E: Unable to locate package yum
```

ubuntu에서 package를 다운로드하는 홈페이지 주소가 추가되어 있지 않아서 그렇다.고 한다.

`/etc/apt/source.list` 에 다음 내용을 추가한다. (혹시 모르니 source.list파일 백업 필수)

```
deb http://archive.ubuntu.com/ubuntu bionic main restricted universe multiverse
deb http://archive.ubuntu.com/ubuntu bionic-security main restricted universe multiverse
deb http://archive.ubuntu.com/ubuntu bionic-updates main restricted universe multiverse

파일 읽으려면 vim편집기가 깔려있어야 한다.
vi로 파일읽고 i가 편집모드 esc로 명령모드 
:wq 저장O 종료
:q  종료
:q! 저장X 종료


입력하고 
apt-get update 하여 업데이트한다.

그리고 다시
apt-get install yum
```


+) yum은 레드햇 계열 리눅스 파일 관리자 라고한다.


우분투에서는 그냥 apt로 써도 된다.



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
