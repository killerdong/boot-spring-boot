# 스프링 부트 살펴보기

## 빌드 시스템

* 부트 버전에 따라서 스프링 프레임워크 버전 및 각 의존된 라이브러리들의 버전이 정해져 있으며 될 수 있으면 이 것을 따르도록 권장
* 위 버전들은 메이븐 중앙 리파지토리에 배포되어서 확인 가능

### 의존성 관리

* 부트 버전에 따라 지원하는 의존성 라이브러리 버전: https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#appendix-dependency-versions
* 물론 위 버전을 말고 다른 버전의 라이브러리를 사용할 수 있지만 문제가 생기면 개발자 책임
* 위 버전 확인은 spring-boot-dependencies 프로젝트의 `pom.xml`의 속성 값으로도 확인 가능

### 그리에들

* 안쓸 것이라서 패스..

### 메이븐

* spring-boot-starter-parent 에서 제공해주는 기능
   1. 기본 컴파일러 Java 1.8 사용(maven 의 경우 기본 값이 1.6)
   2. UTF-8 소스 인코딩
   3. '의존성 관리'에서, spring-boot-dependencies 를 상속한 경우 선별된 의존성에 대해서는 <version> 태그 생략 가능
   4. 손쉬운 리소스 필터링
   5. 손쉬운 플러그인 구성, surefire, GitcommitID, shade
   6. 프로파일 선언 파일을 포함한 application.properties, apllication.yml 에 대한 리소스 필터링 제공
   7. 메이븐에서 위치 변환자 @..@를 스프링 스타일 ${}로 변경 가능

### 스타터

* 애플리케이션이 이용할 수 있는 의존성을 기술하고 있는 집합체
* 스터디만 등록하면 스타터에 의존된 모든 라이브러리들이 자동으로 등록
* 예로 spring-data-jpa 기능을 사용하고 싶으면 spring-boot-starter-data-jpa만 의존된 모든 라이브러리가 자동으로 추가
* 이름은 spring-boot-starter-* 로 구성되어 있다.

### 부모 스프링 부트 스타터 상속

* 스프링 부트 버전은 부모 스프링 부트 스타터를 상속받는 곳에서 선언
```xml
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.0.4.RELEASE</version>
        <relativePath/> <!-- lookup parent from repository -->
    </parent>
```
* 사용하는 사전정의된 의존성 라이브러리 버전을 변경 가능
```xml
<properies>
    <spring-data-releasetrain.version>Kay-SR2</spring-data-releasetrain.version>
</properies>
```

### spring-boot-starter-parent 없이 사용하기

* 기업 표준 parent POM을 사용하거나 모든 메이븐 구성을 명확하게 선언하기를 원하는 경우에 사용
* 될 수 있으면 위에 부모를 상속받아서 쓰자(편하다.)
* 의존성 관리 기능을 그대로 쓰기 위해서는 아래와 같이 추가하자
```xml
<dependencyManagement>
    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-dependencies</artifactId>
            <version>2.0.4.RELEASE</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
    </dependencies>
</dependencyManagement>
```
* 의존성 라이브러리 버전을 변경하기 위해서는 boot-dependencies 앞에 dependency 를 선언해야 한다.
> 메이븐에서 scope
> 1. compile: 기본 scope이다. 만약 dependency에 아무것도 입력하지 않았다면 기본적으로 입력되는 scope이다. 이 옵션은 프로젝트의 모든 classpath에 추가된다(테스트 중이건 런타임 중이건 상관없이).
> 2. provided : 이 옵션은 compile과 매우 비슷하지만, 실행시 의존관계를 제공하는 JDK나 Web Container(tomcat 같은)에 대해서 적용된다.  예를 들어 Java Enterprise Edition Web application을 개발할때 Servlet API나 Java EE API들은 "provided" scope로 지정해야한다. 왜냐하면 Servlet API같은 경우는 Servlet Container 자체에서 지원해 주기 때문에(Tomcat 같은 경우는 ${tomcat home directory}/lib 디렉토리에 있는 Servlet 라이브러리를 사용) 컴파일시 또는 테스트시에는 필요하지만 실행시에는 필요하지 않기 때문이다.
> 3. runtime : 컴파일 시에는 필요하지 않지만 실행시에 사용되는 경우 사용한다. 이 옵션은 런타임, 테스트 시 classpath에 추가 되지만, 컴파일시에는 추가 되지 않는다.
> 4. test : 일반적인 경우에는 필요하지 않고 테스트시에만 필요한 경우 사용한다.
> 5. system : 해당 jar를 포함해야 한다고 명시적으로 입력 하는 것을 제외하고는 provided와 유사하다. 선언된 artifact들은 항상 사용가능하지만 Maven의 central repository에서 찾아서 가져오는 것은 아니다.
> 6. import : Maven 2.0.9 이상의 버전에서 지원하는 scope로서, 이 scope는 <dependencyManagement> 섹션에서 pom의 의존관계에 대해 사용된다. 지정된 pom이 해당 pom의 <dependencyManagement> 영역에 있는 의존관계로 대체됨을 뜻한다.
     
### 레퍼

* 그래이들 혹은 메이븐을 개발자 로컬환경 CI 서버에 설치하지 않고 프로젝트에 포함시켜 배포
* 프로젝트에 mvnw 파일이 레퍼다.
