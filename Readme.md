[![spring-jwt with Maven](https://github.com/AlekseiPetrovJ/spring-jwt/actions/workflows/maven.yml/badge.svg)](https://github.com/AlekseiPetrovJ/spring-jwt/actions/workflows/maven.yml)

# Реализация аутентификации и авторизации с использованием Spring Security и JWT

Реализованы REST методы:
- POST добавления нового пользователя с правами USER (не требует предварительной авторизации)
- POST получение токена
- GET получение страницы любым авторизованным пользователем
- GET получение страницы с правами ADMIN

При добавлении нового пользователя данные валидируются. 
В случае невалидных данных возвращается сообщение с деталями ошибки.

В случае попытки внести дублирующегося имени или почты выбрасывается исключение UserAlreadyExistException, 
происходит запись в лог с уровнем ERROR. Ответ в данном случае не содержит деталей ошибки (только код ошибки).

Учетные записи хранятся в postgresql. Начальные учетные записи пользователя и администратора вносятся в БД посредством liquibase.

## [Подробное техническое задание](4.txt)

# Запуск приложения
## Требования
Установленный maven, docker, docker compose

## Запуск
1) Скачайте проект из ветки master
2) в командной строке (cmd/bash) перейдите в каталог проекта

windows: `cd C:\Users\user\Downloads\<КаталогПроекта>`

linux: `cd ~/Downloads/<КаталогПроекта>`

3) Соберите докер образ:

windows: 
```bash
./mvnw.cmd -B clean package dockerfile:build
```

linux: 
```bash
./mvnw -B clean package dockerfile:build
```

4) Запуск проекта:
```bash
docker compose up
```

## OpenAPI
[AOP](http://127.0.0.1:8080/swagger-ui/index.html)

## Credentials

Пользователь с полными правами:

```
"username": "admin",
"password": "admin"
```

Пользователь с ограниченными правами:

```
"username": "user",
"password": "user"
```