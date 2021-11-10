## 메이븐

maven은 `.xml` 파일로 프로젝트를 통합 관리한다.

기본적으로 pom.xml파일을 사용

## pom.xml의 기본 설정

<< 메이븐의 기본 구분자 >>

```xml
<!-- 최상위 태그 -->
<project>
  <!-- 프로젝트의 그룹명. 일반적으로 회사 도메인을 역순으로함. -->
  <groupId>org.maven.practice</groupId>
  <!-- 해당 프로젝트 명칭. groupId 내에서 유일해야함 -->
  <artifactId>practice001</artifactId>
  <!-- POM.xml의 문서버전. 현재는 무조건 4.0.0 -->
  <modelVersion>4.0.0</modelVersion>
  <!-- artifact의 버전. SNAPSHOT은 아직 개발중이라는 의미 -->
  <version>0.0.1-SNAPSHOT</version>
  <!-- 프로젝트의 이름을 설정 -->
  <name>Maven-Practice-Good</name>
  <!-- 프로젝트의 주석 -->
  <description>Hello Maven Help Me Please</description>
  <!-- 어떤 파일형태로 패키징할 것인가 명시. war, exe, jar 등등.. -->
  <packaging>war</packaging>
  
  <!-- 해당 xml에서 변수처럼 사용 -->
  <properties>
    <!-- 변수 good 설정 -->
    <good>this is maven properties</good>
  </properties>
  <!--변수명 good 호출 -->
  ${good} <!-- this is maven properties -->
  
</project>
```
<br>

자세한 사항은 apache maven 홈페이지에서 확인 가능하다.

[메이븐 pom.xml설정](https://maven.apache.org/ref/3.8.3/maven-model/maven.html)
- - - 

## MAVEN을 사용하는 이유
1. 작업 중인 프로젝트의 정보 관리 ( 저장공간, 버전, 부분설정 )
2. 런타임 시 설정을 분리(fragment) 하거나 사용 될 환경을 설정
3. 프로젝트에 사용되는 의존성 관리 => 이행적 의존 [모듈 간 의존성의 이해관계 설정 및 대응]
4. 라이브러리 캐싱, 토큰 검증 등 의존성 편의 기능
5. 프로젝트의 배포 및 빌드 관리 ( 빌드 시 사용하는 플러그인의 통합적 관리 )


## pom.xml에서 설정 등록

```xml
<!-- 최상위 태그 -->
<project>
  <!-- 프로젝트의 그룹명. 일반적으로 회사 도메인을 역순으로함. -->
  <groupId>org.maven.practice</groupId>
  <!-- 해당 프로젝트 명칭. groupId 내에서 유일해야함 -->
  <artifactId>practice001</artifactId>
  <!-- POM.xml의 문서버전. 현재는 무조건 4.0.0 -->
  <modelVersion>4.0.0</modelVersion>
  <!-- artifact의 버전. SNAPSHOT은 아직 개발중이라는 의미 -->
  <version>0.0.1-SNAPSHOT</version>
  <!-- 프로젝트의 이름을 설정 -->
  <name>Maven-Practice-Good</name>
  <!-- 프로젝트의 주석 -->
  <description>Hello Maven Help Me Please</description>
  <!-- 어떤 파일형태로 패키징할 것인가 명시. war, exe, jar 등등.. -->
  <packaging>war</packaging>
  
  <!-- 설정 등록 구간 -->
  <!-- 해당 xml에서 변수처럼 사용 -->
  <properties> 
    <good>this is maven properties</good>
  </properties>
  
    <!-- 의존성 등록 구간 -->
    <dependencies> </dependencies>
  
    <!-- 플러그인 등록 구간 -->
    <build>
      <plugins>
        <plugin>
          <!-- antrun plugin을 등록함 -->
          <artifactId>org.apache.maven.plugins</artifactId>
          <groupId>maven-antrun-plugin</groupId>
          <version>1.8</version>
          
          <executions>
            <execution>
              <!-- id설정 -->
              <id>echo-in-maven</id>
              <!-- plugin의 설정이 필요한 경우 configuration을 사용 -->
              <configuration>
                <!-- ant문법 <target> <echo> -->
                <target>
                  <echo>Hello echo and ${good} </echo> <!-- Hello echo and this is maven properties -->
                </target>
              </configuration>
            </execution>
          </executions>
          
        </plugin>
      </plugins>
    </build>
  </properties>
</project>
```
<br>

콘솔창 `cmd`에서 `mvn` 명령어를 사용하여 메이븐을 실행

```
mvn -f 설정파일.xml antrun@echo-in-maven

mvn -f [설정파일명.xml] [plugin명]@[id]
```

## 버전의 통합 관리

메이븐의 버전은 릴리즈`Realease`와 스냅샷`Snapshot`라이브러리를 다르게 적용한다.

```
<version>0.0.1-SNAPSHOT</version>
```
`SNAPSHOT` :
- 아직 개발 도중이며 현재 변경이 가능한 버전.
- 로딩시 SNAPSHOT은 매번 업데이트를 하여 라이브러리를 지속적으로 통합 관리.
- 개발이 완료된 후 SNAPSHOT을 RELEASE로 변경하거나 삭제하여야 한다.
