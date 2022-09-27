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





