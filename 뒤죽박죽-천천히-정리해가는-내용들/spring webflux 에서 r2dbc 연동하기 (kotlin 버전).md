# spring webflux 에서 r2dbc 연동하기 (kotlin 버전)

개인적으로 spring webflux 와 코틀린을 도전적으로 적용해봤었는데, 그냥 세팅과정을 한번 정리해봐야겠다는 생각이 들어서 정리하기 시작했다. 정리 안하면 어차피 또 나중에 허둥댄다. 그래서 정리해두기로 결정 ㅠㅠ<br>

얼른 후딱 정리하고 다른 공부 좀 해야겠다. 코틀린도 정리해야 할 부분이 꽤 있었다ㅋㅋ;;;<br>

코틀린 잘하고 싶음. 써보니 편하고, 좋고, 자꾸 자바방식의 변수선언을 하는 나를 발견하게 됨<br>

<br>

오늘 정리하는 내친 김에 도커이미지로 배포하고 Mysql도 network로 연동하는 과정을 정리해놔야 겠다.<br>

그 다음에 시간나면 Fat Jar 관련해서도 정리하고 겸사겸사 이렇게 또 연관퀘스트를 만들어내네...ㅋㅋ<br>

<br>

# 프로젝트 다운로드

프로젝트의 기본 의존성은 아래와 같이 지정해줬다.<br>

스프링 부트 버전은 전 버전대에서 가장 안정된 버전인 2.6.12 버전을 선택했다. 이렇게 보수적으로 선언하는 것도 꽤 좋은 선택인것 같다.<br>

> 2022.09.26 현재 2.7.4 버전은 GA 이지만, WEBFLUX 아래에서 R2DBC 를 사용하는 것이 꽤 부자연 스러웠다. 이런 이유로 만들고 있던 토이 프로젝트를 버리고 새로운 프로젝트로 죄다 옮겨서 개발하는 불상사가 일어났었다...<BR>

![1](./img/webflux-kopring-setup-2022-09/1.png)

<br>

Explore 버튼을 눌러서 의존성을 확인해보면 아래와 같이 나타난다.

```groovy
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.6.12"
	id("io.spring.dependency-management") version "1.0.14.RELEASE"
	kotlin("jvm") version "1.6.21"
	kotlin("plugin.spring") version "1.6.21"
	kotlin("plugin.jpa") version "1.6.21"
}

group = "io.testprj"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-data-r2dbc")
	implementation("org.springframework.boot:spring-boot-starter-webflux")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
	runtimeOnly("com.h2database:h2")
	runtimeOnly("dev.miku:r2dbc-mysql")
	runtimeOnly("io.r2dbc:r2dbc-h2")
	runtimeOnly("io.r2dbc:r2dbc-postgresql")
	runtimeOnly("mysql:mysql-connector-java")
	runtimeOnly("org.postgresql:postgresql")
	annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("io.projectreactor:reactor-test")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "17"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
```

<br>

# Mysql 세팅

MySQL을 컴퓨터에 설치하기에는 조금 무거우니, 도커로 설치하자. 아래는 도커컴포즈 파일이다.

`docker-compose.yml` 

```yaml
version: '3'
services:
  mysql-for-test:
    image: mysql:8
    restart: always
#    command: --lower_case_table_names=1
    container_name: mysql-test
    ports:
      - "13306:3306"
    environment:
      - MYSQL_USER=collector
      - MYSQL_PASSWORD=1111
      - MYSQL_DATABASE=collector
      - MYSQL_ROOT_PASSWORD=1111
      - TZ=Asia/Seoul
    command:
      - --character-set-server=utf8mb4
      - --collation-server=utf8mb4_unicode_ci
    volumes:
      - ./init/:/docker-entrypoint-initdb.d/

```

위의 도커 컴포즈 파일을 로컬 PC에서 구동시키려면 아래의 명령어를 터미널에 입력해주자. (윈도우도 가능)

```bash
docker-compose up -d
```

주의할 점은 `docker-compose.yml ` 파일을 만들어둔 폴더 내에서 위의 `docker-compose up -d` 명령어를 실행해야 한다는 점이다.<br>

<br>

위의 설정은 로컬 PC 내의 13306 포트에 mysql 을 실행시킨다.<br>

<br>





