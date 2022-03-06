# Docker을 이용해서 Linux에 Gitlab을 설치


### 먼저 docker에서 gitlab iamge다운
[Docker Hub-gitlab](https://hub.docker.com/r/gitlab/gitlab-ce)

<img width="793" alt="5464564544" src="https://user-images.githubusercontent.com/58055835/155876271-667de95a-ff58-4fde-a32f-9c44a8aa0916.png">

Docker Pull Command 복사
```
docker pull gitlab/gitlab-ce:latest
```

### 다운로드후 docker run
```
docker run --detach \
  --hostname gitlab.example.com \  나는 127.0.0.1 설정
  --publish 443:443 --publish 80:80 --publish 22:22 \   443=https, 80=http, 22=ssh
  --name gitlab \
  --restart always \
  --volume $GITLAB_HOME/config:/etc/gitlab \    /srv/gitlab
  --volume $GITLAB_HOME/logs:/var/log/gitlab \  /srv/gitlab
  --volume $GITLAB_HOME/data:/var/opt/gitlab \  /srv/gitlab
  --shm-size 256m \
  gitlab/gitlab-ee:latest
```


### 다음과 같이 작성함
```
docker run --detach --hostname 127.0.0.1 --publish 3000:80 --publish 2222:22 --name gitlab --restart always --volume /srv/gitlab/config:/etc/gitlab --volume /srv/gitlab/logs:/var/log/gitlab --volume /srv/gitlab/data:/var/opt/gitlab gitlab/gitlab-ce:latest
```

### gitlab 컨테이너 접속
```
docker exec -it gitlab /bin/bash
```

### gitlab.rb 수정
```
cd /etc/gitlab/
vi gitlab.rb

vi편집기로 gitlab.rb에 접속하여 찾기로 검색하여 해당 부분 수정
/external_url '
external_url 'http://127.0.0.1:3000'

/gitlab_shell_ssh_port
gitlab_rails['gitlab_shell_ssh_port'] = 2222

:wq
```

### 설정 적용

```
gitlab-ctl reconfigure
```

### Docker 재시작
```
exit
docker restart gitlab
```

### gitlab 웹에서 접속
http://127.0.0.1:3000

<img width="946" alt="3423466" src="https://user-images.githubusercontent.com/58055835/155876736-54547247-d960-48d0-bb71-3cf05e991ab5.png">

gitlab 접속 성공

이제 접속해서 아이디 만들고 접속해보려고하는데 아직 admin이 확인안했다고 뜬다.

그럼 [GitLab 가이드](https://docs.gitlab.com/ee/security/reset_user_password.html) 를 참고하여 관리자가 뭔지 알아보자

[GitLab Rails console](https://docs.gitlab.com/ee/administration/operations/rails_console.html)

### console에서 입력한 모습

<img width="650" alt="453224df" src="https://user-images.githubusercontent.com/58055835/155877501-04f962f7-f7ab-4328-af32-1342cb8b3e58.png">

```
gitlab-rails console // GitLab console접속
user = User.find_by_username 'root' // username이 root인거 찾아서 변수 user에 입력
user.password = 'root' // user의 비밀번호 설정
user.password_confirmation = 'root' // user의 비밀번호 확인
user.save! // 저장
// 하면 최소 8글자이상 입력하라고 한다.
// 아무튼 8글자 이상 입력하고 저장하고 접속해보자
exit // 나갈때
```

### 다 했으면 root계정으로 접속해보자
<img width="1885" alt="66433466435" src="https://user-images.githubusercontent.com/58055835/155877741-e5f35251-6dd3-47ad-a279-097094849df8.png">

### root계정으로 접속한 모습


### SSH-KEY를 등록해보자

```
ssh-keygen
엔터
엔터
하면 기본경로에 만들어졌다고 뭐가 뜬다.
```
