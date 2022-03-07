
```
docker pull ther0604/gitlab_image:latest

docker run --detach --hostname 127.0.0.1 --publish 28800:80 --publish 20022:22 --publish 26443:443 --name gitlab --restart always --volume /srv/gitlab/config:/etc/gitlab --volume /srv/gitlab/logs:/var/log/gitlab --volume /srv/gitlab/data:/var/opt/gitlab ther0604/gitlab_image:latest

docker exec -it gitlab /bin/bash

gitlab-rails console
user = User.find_by_username 'root'
user.password = 'safe7900'
user.password_confirmation = 'safe7900'
user.save!
exit

cd /root/.ssh/
vi id_rsa.pub
복사

gitlab 로그인
http://localhost:28800/

user : root
pass : safe7900

Edit Profile

SSH Keys

Key 붙여넣기
Title
safecnc
Expiration date
9999 12 30

```
