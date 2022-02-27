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


