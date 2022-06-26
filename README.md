# Spring Boot and Spring Security with JWT App

## database migration

update db connection in `pom.xml` with yourself db info:

```shell
mvn flyway:migrate
```

## setup 

update db connection in `application.yml` with yourself db info:

```shell
mvn spring-boot:run
```

## test

### login

```shell
curl --location --request GET 'http://127.0.0.1:8080/login' \
--header 'username: admin' \
--header 'password: 12345'
```

### test api

```shell
curl --location --request GET 'http://127.0.0.1:8080/test/list' \
--header 'Authorization: Bearer ${token_from_last_step'
```

## reference

1. [Spring-security与JWT前后端分离](https://rstyro.github.io/blog/2021/07/23/Spring-security%E4%B8%8EJWT%E5%89%8D%E5%90%8E%E7%AB%AF%E5%88%86%E7%A6%BB/)