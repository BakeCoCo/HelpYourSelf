```
Windows PowerShell
ssh -p포트번호 아이디@주소
비밀번호 입력

/etc/my.cnf
/etc/my.cnf.d/
에 있는것들 수정
```

## etc/my.cnf

![image](https://user-images.githubusercontent.com/58055835/160532870-3b996a73-a0f2-4b17-9b2c-d827f381f0ca.png)

## etc/my.cnf.d/mysql-clients.cnf

![image](https://user-images.githubusercontent.com/58055835/160532914-350f0669-15ef-4c22-8057-a22999fd189b.png)

## etc/my.cnf.d/server.cnf

![image](https://user-images.githubusercontent.com/58055835/160532971-2b7a7ffd-2f2d-4f65-97d4-8495f0c93648.png)

```
select * from ta_code_m ;
show create table ta_code_m;
show create database msalesdev;

show create procedure SP_AAAA0010_SEL00 ; --이새끼 Database Collation latin1_swedish_ci로 되있는지 확인
--확인했으면 procedure 전부 선택해서 CREATE OR REPLACE 전부 실행해서 다시 바꿔줘야댐

SHOW FULL COLUMNS FROM ta_code_m;

alter database msalesdev default character set utf8 COLLATE utf8_general_ci;
```

![image](https://user-images.githubusercontent.com/58055835/160532275-a43c8adb-f5db-4a29-9e4d-862e001cfc79.png)



--------------------------------------------------

자꾸 유니코드 에러나서 짜증나죽것다

SQL Error [1366] [22007]: (conn=10) Incorrect string value: '\xE3\x85\x81' for column ``.``.`iCOMM_CDNM` at row 1



show variables like 'c%';

그래서 확인해보니 latin으로 지정되있었다. (지금은 수정한거임)

![image](https://user-images.githubusercontent.com/58055835/160520134-e44fb9f2-fa37-4d56-9c53-9f11ce3a7f65.png)

근데 왜 수정했는데도 오류나고 지@인지 잘 모르겠다.

수정할라면 일단 my.cnf파일을 찾아서 수정해야된다.

다른 서버에 DB가 있으니 SSH로 드간다

Windows PowerShell 을 사용해서 접속

ssh -p포트번호 아이디@주소

치면

비밀번호 입력하라고한다

입력후 접속해서

my.cnf파일을 찾아서

수정하자

![image](https://user-images.githubusercontent.com/58055835/160520525-8aee6aea-74dc-4afe-b7ed-327758b0a667.png)

대충 이런식으로 수정하면

저 위에있는 그림처럼 캐릭터셋이 수정이 된다.

근데 왜안되는지 모르겠다 ㅎㅎ
