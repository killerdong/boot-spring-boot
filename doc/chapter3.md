# 스프링 부트 기능 소개

배울 내용

* 스프링 부트의 진입점인 SpringApplication
* 배포 환경에 맞춰 속성을 부여하는 방법
* 프로파일(Profile) 을 통한 실행환경을 구분, 다양한 서드파티 생태계와 연동, 필요에 따라 구성하는 과정

## 스프링 애플리케이션(SpringApplication)

* SpringApplication 클래스는 어플리케이션 실행 시 main() 메서드를 통해 여러 편의 기능 제공
* 가장 기본적인 사용 방법은 아래와 같다.
```java
    public static void main(String... args) {
        SpringApplication.run(BootSpringBootApplication.class, args);
    }
```
* 애플리케이션이 사작되면서 보여주는 INFO 로깅 메시지는 애플리케이션과 관련된 상세한 정보를 포함

### 구동 실패

* 구동 실패 시 FailureAnalyzers 를 통해서 발생한 문제를 출력하고, 이 문제를 해결할 수 있는 기회를 제공
* https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#howto-failure-analyzer 사이트를 통해서 쉽게 재사용 가능
* debug 모드를 통하면 자동구성 보고서를 통해서 어느 부분이 잘못되었는지 살펴볼 수 있다.
* debug 모드는 org.springframework.boot.autoconfigure.logging.ConditionEvaluationReportLoggingListener 와 관련
* debug 모드 설정 방법
   * java -jar boot-spring-boot.1.0.0.RELEASE.jar --debug
   * `application.yml` 의 debug 속성을 활용

### 배너 기능

* 어플리케이션 운영에는 상관없지만 개발자가 크게 환호할... 만한 기능
* banner 파일(txt, 이미지 사용 가능)을 클래스 패스에 두거나 banner.location 에 정의하면 됨
* txt 의 경우 인코딩이 필요하면 banner.charset 을 설정
* 빌드하여 실행 할 경우 MAINFEST.MF 파일을 참고하여 여러가지 정보를 가져올 수 있음
* 해당 정보는 maven 같은 경우 각 종 설정 값에서 가져온다.
* spring-main-banner-mode 속성을 활용해서 출력 방식을 설정할 수 있다.
   * console : System.out 으로 출력
   * log : Logger 로 출력
   * off : 출력 끄기

* SpringApplication.setBanner을 이용하여 배너 생성을 프로그래밍 할 수 있음

### SpringApplication 재정의

* 기본 설정이 마음에 들지 않을 경우 로컬 인스턴스를 만들어서 재정의 가능
* application.yml 파일을 이용하여 구성이 가능한데 이 부분은 나중에 다시

### 유연한 빌더 API

* ApplicationContext 계층을 구성하거나 유연한 형태의 빌더 API를 선호한다면 이 것을 사용
* ApplicationContext 계층을 생성할 때 제약
   1. 웹 컴포넌트는 하위 컨텍스트 네에 포함
   2. 부모와 자식 컨텍스트는 동일한 Environment 가 사용된다.
   
### 애플리케이션 이벤트 및 리스너

* SpringApplication은 ContextRefreshedEvent 와 일반적인 스프링 프레임워크 이벤트 외에도 추가적인 이벤트를 전송한다.

> 몇 가지 이벤트는 ApplicationContext 가 생성되기 전에 발생하기 떄문에 컴포넌트 리스너로 등록할 수 없고, SpringApplication.
addListeners() 혹은 SpringApplicationBuilder.listeners() 메소드를 통해서 등록 가능. 자동 등록을 원하면 META-INF/spring.factories
파일을 추가하고 org.springframework.context.ApplicationListener 키로 등록

* 애플리케이션이 실행될 때 애플리케이션 이벤트 순서
   1. ApplicationStartingEvent 는 구동 후 리스너와 이니셜라이저가 등록되기 전에 발생
   2. ApplicationEnvironmentPreparedEvent 는 컨텍스트가 생성되기 전 Environment 가 사용할 컨텍스트를 알고 있는 경우 전송
   3. ApplicationPreparedEvent 는 정의한 빈을 애플리케이션 컨텍스트에 등록 했지만 이를 애플리케이션에 반영하기 전에 전송
   4. ApplicationReadyEvent 는 애플리케이션이 서비스 요청을 처리할 준비가 되었을 때 전송
   5. ApplicationFailedEvent 는 구동 중에 예외가 발생했을 떄 전송
   
* ApplicationListener 는 하나의 인터페이스로 이벤트 발생 시 onApplicationEvent 메서드가 실행

### 웹 환경

* SpringApplication 은 사용자에게 유용한 ApplicationContext를 제공
* 만약 환경을 강제로 변경하기 위해서는 setWebApplicationType 을 이용하면 된다.
* ApplicationContext 를 변경하기 위해서는 setApplicationContext 를 이용할 수 있다.

### 애플리케이션 실행인자 접근

* 애플리케이션 구동 시 Args 에 접근하고 싶을 때 org.springframework.boot,ApplicationArguments 빈을 이용

### CommandLineRunner 혹은 ApplicationRunner 사용하기

* SpringApplication 과 함께 실행되도록 빈을 정의할 수 있는 인터페이스
* 차이점은 CommandLineRunner 는 실행인자를 문자열 배열로 받고, ApplicationRunner 는 ApplicationArguments 로 이터러블 객체를 받는다.
* 애플리케이션이 시작할 때 반드시 한번 실행되어야 하는 기능을 구현
* org.springframework.core.Ordered 인터페이스를 구현하거나 Order 어노테이션을 활용할 경우 순서 설정이 가능

### 애플리케이션 종료

* 종료 시 표준 스프링 라이프사이클 콜백(DisposableBean 구현체이거나 @PreDestory 사용)이 사용
* org.springframework.boot.ExitCodeGenerator 인터페이스를 구현하면 SpringApplication.exit() 시 특정 종료 코드를 받을 수 있도록 변경 가능

## 외부 구성

* 동일한 애플리케이션을 각기 다른 환경에서 실행할 수 있도록 애플리케이션 구성을 외부에서 조작 가능
* yaml 파일, properties 파일, 환경변수 그리고 커맨드라인 실행인자를 이용해 애플리케이션을 외부 구성 가능
* @Value 애너테이션을 통해서 주입하거나 스프링 Environment 를 이용해서 접근하거나 @ConfigurationProperties 를 통해 접근 가능
* 스프링 부트의 PropertySource 값 우선 순위
   1. 사용자 홈 디렉토리에 있는 개발자 도구 전역 설정(개발자 도구가 활성화 되어 있는 경우 ~/.spring-boot-devtools.properties 파일)
   2. 테스트에서 @TestPropertySource 애너테이션을 선언한 경우
   3. 테스트에서 @SpringBootTest#properties 애너테이션을 선언한 경우
   4. 커맨트라인 실행인자
   5. SPRING_APPLICATION_JSON 정의된 속성(json 형식을 띄고 환경변수나 시스템 속성으로 정의)
   6. ServletConfig 초기화 파라미터
   7. ServletContext 초기화 파라미터
   8. java:comp/env 으로 설정한 JNDI 속성
   9. JVM 파라미터 속성(System,getProperties())
   10. 운영체제 환경변수
   11. random.* 속성을 가진 RandomValuePropertySource
   12. 패키징된 jar 외부에서 선언된 프로파일 정의 속성(application.properties, application.yml)
   13. jar 파일 내부에 있는 프로파일 정의 속성(application.properties, application.yml)
   14. 패키징된 jar 외부에서 선언한 애플리케이션 속성들(application.properties, application.yml)
   15. jar 파일 내부에 있는 애플리케이션 속성들(application.properties, application.yml)
   16. @Configuration 클래스에 있는 @PropertySource 애너테이션
   17. 기본 속성들

* @Value 를 활용해서 속성 주입이 가능

### 무작위값 구성

* RandomValuePropertySource 는 무작위 값을 주입하는 유용

### 커맨드라인 속성 접근

* SpringApplication 은 기본적으로 커맨드라인 실행인자(--으로 시작하는) 를 스프링 Environment 에 속성으로 추가 가능
* 커맨드 라인 속성은 언제나 다른 유형의 속성정의보다 우선(1,2,3은 테스트 환경)
* 커맨드라인 실행인자를 비활성화 할려면 SpringApplication.setAddCommandLineProperties(false) 로 선언

### 애플리케이션 속성 파일

* SpringApplication 은 다음 위치에 있는 application.yml 파일을 읽어서 Environment 에 속성을 잭재한다.
   1. 현재 디렉토리 하위에 있는 /config
   2. 현재 디렉토리
   3. 클래스패스 /config 패키지
   4. 최상위 클래스패스
   
* 구성 파일 이름 변경은 spring.config.name 을 이용해서 변경 가능(커맨드 명령)
* spring.config.location 속성을 이용해서 특정 위치를 지정할 수 있음(커맨드 명령). /으로 끝나야 함

### 프로파일 정의 속성

* application.properties 의 경우 프로파일 선언을 위해 application-{프로파일}.properties 로 구성
* 지정된 프로파일 속성 파일은 application.properties 와 동일한 위치에 있어야 한다.
* 최종 승자 전략으로 적용

### 속성 내 치환자

* application.yml 에 정의된 값은 Environment 에 존재할 경우 필터링 되기 때문에 앞서 정의한 값을 사용 가능
* 순차적으로 값이 등록되기 때문에 순서가 중요

### properties 를 대신하여 YAML 사용하기

* yaml 은 json 의 확장판
* 계층적으로 데이터를 구성하여 사람이 쉽게 읽을 수 있는 형식
* 클래스패스 내에 SnakeYAML 을 가지고 있다면 자동으로 yaml 을 지원

#### YAML 적재

* YAML 문서 적재에 사용하기 위한 두 가지의 클래스 제공(YamlPropertiesFactoryBean, YamlMap-FactoryBean)
*  


 
 


  


