# Spring R2DBC 연결설정

https://stackoverflow.com/questions/65303082/how-to-enable-connection-pooling-with-spring-boot-starter-data-r2dbc

<br>

일단... 내가 사용한 spring boot 버전은 2.7.4 이다.<br>

알고보니 스프링 버전이 올라가면서 r2dbc를 공식지원하기 시작하면서, pool 관련설정들은 모두 직접 해주지 않아도 됐었다.<br>

<br>

# R2DBC 연결설정

인터넷자료를 보다가 산으로 갔었다...

spring-boot-starter-***-2.3.5 버전 이후로는 r2dbc-mysql, r2dbc-postgresql 설정을 모두 명시할 필요가 없나보다. <br>

pool 을 명시적으로 지정할 필요 역시 없는 듯. 스프링 data-r2dbc 팀에서 이것을 정리해둔건가 싶기도.<br>

요약해보면 이렇다.

- io.r2dbc:r2dbc 의존성 추가 (gradle)
- spring.r2dbc.* 아래의 모든 설정내용을 명시적으로 추가필요 없다.(application.properties, yml) - 추가하면 에러낸다.

r2dbc로 접속 URL을 설정하지 않았지만, spring datasource 내에 url을 설정해주면 된다. 만약 application properties 에 추가하는 것이 번거롭다면, DataSourceConfig 를 만들어서 ConnectionFactory 클래스를 이용해 직접 설정을 구현하면 될 듯하다.

<br>

**application.properties**

- mysql

```plain
spring.datasource.driver-class-name=com.mysql.jdbc.Driver
spring.datasource.url=r2dbc:mysql://localhost:13306
spring.datasource.username=collector
spring.datasource.password=1111
spring.jpa.hibernate.ddl-auto=create
spring.jpa.show-sql=true
spring.jpa.generate-ddl=true

spring.sql.init.schema-locations=classpath:mysql-schema.sql
#spring.sql.init.mode=always
#
logging.level.org.springframework.data.r2dbc=DEBUG
```



만약 h2 db를 임베디드로 돌릴 거면 아래의 설정을 추가해주면 된다.

**application.properties**

- h2

```plain
spring.h2.console.enabled=true
spring.h2.console.settings.web-allow-others=true
spring.h2.console.path=/h2-console


spring.datasource.driver-class-name=org.h2.Driver
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.username=sa
spring.datasource.password=
spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.show-sql=true
spring.jpa.generate-ddl=true
```

