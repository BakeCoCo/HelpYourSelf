

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
