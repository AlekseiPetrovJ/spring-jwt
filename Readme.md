# Реализация аутентификации и авторизации с использованием Spring Security и JWT



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

`docker compose up`

## OpenAPI
[AOP](http://127.0.0.1:8080/swagger-ui/index.html)

##Credentials

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