# spring boot (kotlin), mysql 을 docker 이미지로 배포하기 (1)

kotllin 이라고 해서 gradle 빌드가 뭔가 달라지는 것은 아니다. 오늘 정리하는 내용은 jar 파일을 최적화해서 배포하는 방식은 아니다. jar 파일이 굉장히 무거운 것을 fat jar 라고 하는데, fatjar 를 가볍게 최적화 하는 것은 바로 다음 문서에 정리해볼 예정이다.<br>

오늘 정리할 때 아래의 순서로 정리하려고 한다.<br>

- docker network 생성
- docker container 로 mysql 구동
- 스프링 부트 애플리케이션의 도커 이미지 빌드, 생성
- docker conatiner 로 스프링 부트 이미지 구동
- 테스트 (1)
- docker-compose 파일로 만들기
- 테스트 (2)

<br>

# 참고자료

오늘 작성한 예제는 [gitlab.com/soongood/study-kopring](https://gitlab.com/soongood/study-kopring/-/tree/main/%EB%92%A4%EC%A3%BD%EB%B0%95%EC%A3%BD-%EC%B2%9C%EC%B2%9C%ED%9E%88-%EC%A0%95%EB%A6%AC%ED%95%B4%EA%B0%80%EB%8A%94-%EB%82%B4%EC%9A%A9%EB%93%A4/springwebflux-kopring-202209/kopring_webflux_202209) 에 있다.<br>

아래는 참고했던 자료다.<br>

- [Spring Boot Docker 컨테이너 배포 (With Gradle)](https://inma.tistory.com/148)
- [Docker - Spring Boot Application Docker 로 배포하기 및 MySQL 연동](https://galid1.tistory.com/726)
- [Docker- Docker network 란? (Docker Network 사용예제)](https://galid1.tistory.com/723)
- [github/soon-good/docker-scripts](https://github.com/soon-good/docker-scripts/blob/develop/docker-mysql/docker-mysql-start.sh)

<br>

참고했던 책

- [스프링 마이크로 서비스 코딩 공작소](http://www.kyobobook.co.kr/product/detailViewKor.laf?ejkGb=KOR&mallGb=KOR&barcode=9791140700448&orderClick=LEa&Kc=)
- 알고보니 책에 이미 다 나와있던 내용이었다.

<br>

# docker 네트워크 생성

도커네트워크를 아래와 같이 생성해준다.

```bash
docker network create network-test
```

<br>

# docker container 로 mysql 구동

mysql 을 docker container로 구동시켜준다.

```bash
docker container run --rm -d -p 13306:3306 --name mysql-test --network network-test -e MYSQL_USER=collector -e MYSQL_PASSWORD=1111 -e MYSQL_DATABASE=collector -e MYSQL_ROOT_PASSWORD=1111 -d mysql:5.7 --character-set-server=utf8mb4 --collation-server=utf8mb4_unicode_ci
```

다소 스크립트가 길다. 윈도우에서는 `\` 이 다른 연산으로 쓰일것 같아서 그냥 평문으로 정리했다. 위의 스크립트를 `\` 를 사용해서 들여쓰기를 하면 아래와 같은 스크립트가 된다.<br>

<br>

```bash
docker container run --rm -d -p 13306:3306 --name mysql-test --network network-test \ 
		-e MYSQL_USER=collector \ 
		-e MYSQL_PASSWORD=1111 \ 
		-e MYSQL_DATABASE=collector \ 
		-e MYSQL_ROOT_PASSWORD=1111 \ 
		-d mysql:5.7 \ 
		--character-set-server=utf8mb4 \ 
		--collation-server=utf8mb4_unicode_ci
```

<br>

# 스프링 부트 애플리케이션의 도커 이미지 빌드, 생성

스프링 부트 애플리케이션의 코드 내용까지 적기에는 너무나 TMI였다. `http://localhost:18080/book/test` 라는 API 를 `GET` 요청에 대해 열어두고, 응답으로는 `안녕하세요` 라는 문자열을 보내주는 단순한 애플리케이션이기 때문이다. 그래서 프로젝트 내용은 그냥 생략했다.<BR>

>  아!! 물론 DB 초기화 스크립트 등에 대한 내용도 있는데, 그것과 관련해서는 이 문서의 최 하단부에 정리해둘 예정이다. SQL 스크립트는 어차피 예제 용도라서 운영업무로 사용할 만한 스크립트는 아니라고 생각했다. 운영 단계로 넘어가면, 전체 스크립트의 DDL을 사용하는 일은 드물다. 컬럼을 수정하는 경우 등에 한해서 FLYWAY 등을 사용하긴 한다. 더 이상은 글이 길어지기에 생략... 

<BR>

## Dockerfile 작성

먼저 Dockerfile 은 아래와 같다. java 기반의 프로젝트와 다른 점은 `build.gradle` 파일대신 `build.gradle.kts` 파일명을 사용하고, `settings.gradle.kts` 를 사용한다는 점 외에는 java 기반의 스프링 부트 이미지를 build 하는 것과 차이가 없다.<br>

설명을 작성할까 했는데, 역시나 요즘 시간이 그리 많지 않다. 그냥 코드만 남겨놓고 튀어야겠다.

```bash
FROM eclipse-temurin:19 AS prj-builder
COPY gradlew .
COPY gradle gradle
COPY build.gradle.kts .
COPY settings.gradle.kts .
COPY src src
RUN chmod +x ./gradlew
RUN ./gradlew bootJar

FROM eclipse-temurin:19
RUN mkdir /opt/app
COPY --from=prj-builder build/libs/*.jar /opt/app/kopring-app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/opt/app/kopring-app.jar"]
```

<br>

## spring boot 앱 도커이미지 빌드, 생성 (Dockerfile 빌드)

위에서 작성한 Dockerfile 이 위치한 디렉터리로 가서 이미지로 빌드하자. 이렇게 하면 Docker가 인식할 수 있는 이미지가 생성되고 생성이 완료되면 OS내에 설치되어 있는 도커 엔진내에 이미지가 등록된다.

 ```dockerfile
 docker build -t kopring-test/kopring-app-test:0.0.1 .
 ```

<br>

이렇게 하면 docker 이미지가 도커 엔진 내에 이미지로 등록되는데, 도커 이미지를 조회하는 명령은 아래와 같다.

```bash
> docker ps
CONTAINER ID   IMAGE                                 COMMAND                  CREATED          STATUS          PORTS                                NAMES
01e90fb88f02   kopring-test/kopring-app-test:0.0.1   "java -jar /opt/app/…"   34 minutes ago   Up 34 minutes   0.0.0.0:18080->8080/tcp              kopring-test
// ...
```

<br>

# docker container 로 스프링 부트 이미지 구동 

```bash
docker container run --rm -d -p 18080:8080 --name kopring-test --network network-test kopring-test/kopring-app-test:0.0.1
```

<br>

명령어가 다소 정리가 안되어 보기 불편하다. 들여쓰기를 한 명령어는 아래와 같다.

```bash
docker container run --rm -d -p 18080:8080 --name kopring-test \ 
		--network network-test \ 
		kopring-test/kopring-app-test:0.0.1
```

<br>

이미지가 구동되는 중인지 확인해보자.

docker conatiner ls 명령어를 사용하면 된다.

```bash
> docker container ls
CONTAINER ID   IMAGE                                 COMMAND                  CREATED          STATUS          PORTS                                NAMES
01e90fb88f02   kopring-test/kopring-app-test:0.0.1   "java -jar /opt/app/…"   38 minutes ago   Up 38 minutes   0.0.0.0:18080->8080/tcp              kopring-test
```



잘 구동되고 있다.<br>

<br>

# 테스트 (1)

이제 테스트를 한번 해보자.

네트워크 생성

```bash
docker network create network-test
```

<br>

mysql 도커 컨테이너 구동

```bash
docker container run --rm -d -p 13306:3306 --name mysql-test --network network-test -e MYSQL_USER=collector -e MYSQL_PASSWORD=1111 -e MYSQL_DATABASE=collector -e MYSQL_ROOT_PASSWORD=1111 -d mysql:5.7 --character-set-server=utf8mb4 --collation-server=utf8mb4_unicode_ci
```

<br>

spring boot 도커 컨테이너 구동

```bash
docker container run --rm -d -p 18080:8080 --name kopring-test --network network-test kopring-test/kopring-app-test:0.0.1
```

<br>

브라우저에서 결과 확인해보기

![1](./img/docker-container-deploy-01--2022-09/1.png)



<br>

# docker-compose 파일로 만들기

도커 네트워크를 생성해준다.

```bash
docker network create network-test
```

<br>

위의 도커 스크립트들을 모두 쉘스크립트로 만들어둔다고 하더라도, 아무래도 지금 상태로는 아직은 불편하다. 이번에는 `docker-compose.yml` 파일로 빌드할 수 있게 해보자.<br>

`docker-compose.yml`

```yaml
version: '3'
services:
  mysql-for-test:
    image: mysql:8
    restart: always
    #    command: --lower_case_table_names=1
    container_name: mysql-test
    networks:
      - default
      - network-test
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
  kopring-app-test:
    image: kopring-test/kopring-app-test:0.0.1
    restart: always
    container_name: kopring-test
    networks:
      - default
      - network-test
    ports:
      - "18080:8080"
networks:
  network-test:
    driver: bridge
```

<br>

설명은 나중에 정리해야 할것 같다. 공부해야할 게 너무 많아서다 ㅠㅠ<br>

<br>

# 테스트 (2)

![1](./img/docker-container-deploy-01--2022-09/1.png)





