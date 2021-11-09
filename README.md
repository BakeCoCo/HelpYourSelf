# HelpYourSelf

it's my practice for Spring Boot Application from Inteli J

2021-10-15

Today my Company senior order me for setting our project Rollingfile

So i ask that what is Rollingfile?

Because i don't know this i heared was first time

But his answer to me "your mind"

So i search google and found this site

This site very benefit to me

Thankyou

log4j2 설정하는법

https://deeplify.dev/back-end/spring/logging





<br>





2021-11-03

오늘은 과장님이 저보고 여태 개발을 대체 어떻게 하고있었냐고 물어보았습니다.

이유인 즉슨...

지나가다 제 화면을 보더니 Command Assitent가 동작하지 않아서 몇번 타자쳐보니

기본적인 개발환경 세팅이 되어있지 않아서 공통에서 쓰이는 function들이 자동완성이 되어야 하는데

저는 아무 Command Assitent를 받지 않고 개발을 뚝딱뚝딱 다 했다는것이 놀라워서 물어보았답니다..

어떻게 function들을 일일이 다 찾아서 사용했냐는 의미로

저는 그런게 자동완성이 되는줄도 모르고......

일일이.. 다.. 찾아서.. 어떻게 동작하는지.... 다... 테스트해보고.. 사용햇는데.....

아무튼 놀랍다고 합니다




2021-11-08~09

어제오늘 Docker를 공부해봤다.

Docker로 Tutorial doc문서를 보면서 해봤는데

이거 해서 뭐해야하는지 모르겠다.

서버를 돌리고 port도 연결해주고 DB도 연결해주었는데

정작 제대로 WEB을 만드는 법을 모르니 서버를 만들어봤자 연결을 어떻게 해야하는지

또 어떻게 사용해야하는지 모르겠다.

그래서 다시 원점으로 WEB을 공부해야되겟다.

그래도 Docker에 기본적인 명령어는 몇개 기억했다.

##이미지 검색
```
docker images
docker images [이미지명]
```
##이미지 다운로드
```
docker pull [이미지명]
             이미지명 ex) centos, ubuntu, tomcat, mysql ...
```
##컨테이너 목록 조회
```
docker ps    (실행중인 컨테이너 조회)
docker ps -a (전체 컨테이너 조회)
```
##이미지를 컨테이너로 생성
```
docker run [이미지명] (이미지가 없으면 다운로드 후 실행)
docker run -it --name [설정할이름][이미지명] -dp [호스트포트:컨테이너포트] -v []
```
##컨테이너에 접속
```docker attach [컨테이너명]```

##컨테이너 시작
```docker start [컨테이너명]```

##컨테이너 중지
```docker stop [컨테이너명]```

##컨테이너 제거
```docker rm [컨테이너명]```

##컨테이너 강제 삭제 (실행중이어도 삭제함)
```docker rm -f [컨테이너명]```

##이미지 제거
```docker rmi [이미지명]```

##옵션
```
-it = 키보드로 입력받는다.
-d = container에 접속하지 않아도 background에서 실행된다.
마지막에 /bin/bash 를 입력해야 container에 bin폴더의 bash를 실행해준다.
```


