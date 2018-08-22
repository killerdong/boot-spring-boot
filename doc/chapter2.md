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

## 코드 구조

* 특별하게 어떤 식으로 작성하라는 방식은 없음
* 아래 규칙은 몇 가지 도움이 될만한 가이드이다.

### 기본 페키지 이용

* 기본 페키지로 간주되는 곳에 클래스를 작성하는 것은 피하자.
* @ComponentScan, @EntityScan, @SpringBootApplication 애너테이션을 사용할 경우 모든 jar에서 모든 클래스를 탐색하기 때문에 망한다.

### 애플리케이션 메인 클래스 위치

* 최상위 패키지에 위치하는 것이 좋다.
* @EnableAutoConfiguration 처럼 애플리케이션 구성과 관련된 애너테이션의 경우가 해당
* Scan 애너테이션의 경우 기본적으로 해당 페키지 내에 있는 서브 페키지 들을 탐색하기 때문에 따로 설정이 필요 없다.
* 다른 위치에  둘 경우 scanBasePackages 를 등록시켜 줘야 한다.
* 나중에 전문가가 되면 머 마음대로 해되 되지만 최상위 패키지에 두는게 여러가지고 편하다.

## 구성 클래스

* 스프링 부트는 자바기반 구성을 선호한다.
* 물론 XML을 통해서 구성을 하는 것도 가능하지만 하지 말자.
* @Configuration 으로 선언된 클래스에 main() 메서드를 정의해두는 것이 좋은 방법

### 구성 클래스 불러오기

* 여러 개의 구성 클래스로 분리해서 사용할 경우 @import 어노테이션을 이용해서 불어올 수 있다.
```java
@Configuration
@Import(EnableWebMvcConfiguration.class)
```
* 다른 방법은 @ComponentScan 을 사용하면 @Configuration 을 포함한 모든 스프링 컴포넌트를 사용할 수 있다.

### XML 구성 불러오기

* @ImportResource 애너테이션을 사용하면 된다.

## 자동 구성

* 추가된 의존성을 기반으로 애플리케이션을 자동구성하는 기능
* 자동구성 기능을 활성화하려면 @Configuration 이 선언된 클래스에 @EnableAutoConfiguration 혹은 @SpringBootApplication을 추가
* @SpringBootApplication 은 @EnableAutoConfiguration, @SpringBootConfiguration, @ComponentScan 을 합쳐놓은 것

### 자동구성 대체하기

* 비침투적이어서 자동구성된 특정 부분을 재정의하고 시작하는 것이 가능
* 자동 구성이 무엇인지 찾아보려면 --debug 를 활성화하면 된다.

### 특정 자동구성 비활성화하기

* @EnableAutoConfiguration 의 exclude 속성을 이용해서 비활성화 할 수 있다.
* excludeName 속성을 이용하면 지금은 없지만 나중에 추가되어 현재 프로젝트에 영향을 줄 수 있는 자동구성을 제외할 수 있다.
* application.yml의 spring.autoconfigure.exclude 속성을 통해서도 제외가 가능

## 스프링 빈과 의존성 주입

* 스프링 프레임워크 표준기술을 사용하여 Bean과 의존성 주입을 자유롭게 사용할 수 있다.
* @ComponentScan 사용해서 빈을 찾고, @Autowire 를 선언하여 생성자 의존성 주입 방식을 사용
* 최상위 패키지에 애플리케이션을 등록할 경우 모든 애플리케이션 컴포넌트룰 찾아서 자동 등록
* 특정 필드에 `final` 을 표시해두면 빈이 생성된 이후 변경되지 않는 것을 **명시적**으로 알려주는 역할
* 생성자가 1개인 경우 @Autowired 를 하지 않아서 생성자 주입이 가능하다.

## @SpringBootApplication 애너테이션 사용

* 예전에 @Configuration, @EnableAutoConfiguration, @ComponentScan 을 따로 선언
* 1.2.0 버전 이후 부터 위 3개를 합쳐서 @SpringBootApplication 으로 선언이 가능
* 스프링 부트 애플리케이션에서는 위 어노테이션을 **하나만 사용해야 한다.**

## 애플리케이션 실행하기

* 애플리케이션 컨테이너를 함께 JAR로 패키징 할 경우 어디서나 애플리케이션을 실행할 수 있는 특징을 가진다.

### IDE 에서 실행하기

* IntelliJ 의 경우 자동으로 구성(위에 버튼 클릭)

### 패키징된 애플리케이션 실행하기

```
java -jar build/lib/boot-spring-boot-1.0.0.RELEASE.jar
```

### 그레이들 플러그인으로 실행하기

```
./gradlew bootRun

export JAVA_OPTS=Xmx1024m -XX:MaxPermSize=128M
```

### 메이븐 플러그인으로 실행하기

```
./mvnw spring-boot:run
```

## 스프링 부트 개발자도구

* 애플리케이션 개발경험을 향상시키는데 도움이 되는 도구를 포함
* 개발자 도구를 포함하기 위해서는 모듈 의존성을 빌드 스크립트에 추가하면 된다.
* 개발자 도구의 경후 패키징된 애플리케이션이 실행될 때 자동으로 비활성화

### 속성 기본 정의

* 운영에는 필요하나 개발에는 불편한 속성들을 자동으로 비활성화(캐싱기능 등등)
* 개발자도구에서 비활성화 처리하는 목록(https://github.com/spring-projects/spring-boot/blob/master/spring-boot-project/spring-boot-devtools/src/main/java/org/springframework/boot/devtools/env/DevToolsPropertyDefaultsPostProcessor.java)

### 자동 재시작

* 클래스 패스 내에 파일이 변경될 때 자동으로 재시작

#### 재시작 트리거

* 클래스 패스를 지켜보다가 클래스 패스가 갱신되면 재시작 트리거가 동작
* 인텔리J의 경우 프로젝트를 빌드할 때 발생
* 재시작 동안에는 애플리케이션 컨텍스트 셧다운 훅을 잠궈둔다. 셧 다운 훅을 비활성화 할 경우 해당 기능 사용 불가
* 클래스 패스가 변경되었을 때 spring-boot, spring-boot-devtools, spring-boot-autoconfigure, spring-boot-actuator
, spring-boot-starter 같은 spring-boot 관련 프로젝트를 제외하고 재시작되는 진입점을 결정

#### 재시작 vs 재적재

* 구매는 머하니 그냥 기본으로
* 클래스가 변경되지 않았을 때 기본 클래스로더에 적재
* 클래스가 변경되면 재시작 클래스로더에 적재
* 애플리케이션이 재시작될 때 기존 재시작 클래스로더를 없애고 새로운 것을 만든다.

#### 재시작 대상 제외

* 리소스 같은 경우는 변경 사항이 발생하더라도 재시작할 필요가 없다.
* 이런 것을 제외하기 위해서는 `spring.devtools.restart.exclude` 속성에서 설정 가능
```
spring.devtools.restart.exclude: static/**, public/**
```

#### 재시작 감시경로 추가

* 클래스 패스외에 내용 변경 시 재시작이 필요한 경우에 설정
* `spring.devtools.restart.addtional-exclude` 속성에서 설정

#### 재시작 비활성화

* 재시작 기능을 사용하고 싶지 않은 경우
* `spring.devtools.restart.enabled` 속성에서 설정
* main에 System.setProperty 를 사용해서도 설정 가능

#### 트리거 파일 사용

* 특정 파일이 변경될 때 자동으로 재시작되도록 하도록 설정
* `spring.devtools.restart.trigger-file` 속성 설정

### 전역 설정

* 홈디렉토리($HOME)에 `.spring-boot-devtools.properties` 이름의 파일 생성하면 전역으로 개발자도구를 설정할 수 있음

## 출시를 위한 애플리케이션 패키징

뒤에서 보자
