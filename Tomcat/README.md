
한글깨짐
```
catalina.bat

setlocal
set JAVA_OPTS=%JAVA_OPTS% %LOGGING_MANAGER% -Dfile.encoding=utf-8
```

```
server.xml

<Connector port="8080" protocol="HTTP/1.1"
               connectionTimeout="20000"
               redirectPort="8443" URIEncoding="UTF-8"/>
```

톰캣 콘솔 한글깨짐

![image](https://user-images.githubusercontent.com/58055835/209515129-9e35df71-95c5-49bc-8917-360df7c21c93.png)



톰캣 관리자 설정

```
tomcat-user.xml

  <role rolename="manager-gui"/>
  <role rolename="manager-script"/>
  <role rolename="manager-jmx"/>
  <role rolename="manager-status"/>
  <user username="admin" password="admin" roles="manager-gui,manager-script,manager-jmx,manager-status"/>
```


환경설정

```
catalina.bat
set JAVA_OPTS=%JAVA_OPTS% -Dspring.profiles.active=local
set JAVA_OPTS=%JAVA_OPTS% -Dspring.profiles.active=dev
set JAVA_OPTS=%JAVA_OPTS% -Dspring.profiles.active=prod

알아서 필요한것 사용
```

