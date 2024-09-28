# ���������� �������������� � ����������� � �������������� Spring Security � JWT



## [��������� ����������� �������](4.txt)

# ������ ����������
## ����������
������������� maven, docker, docker compose

## ������
1) �������� ������ �� ����� master
2) � ��������� ������ (cmd/bash) ��������� � ������� �������

windows: `cd C:\Users\user\Downloads\<��������������>`

linux: `cd ~/Downloads/<��������������>`

3) �������� ����� �����:

windows: 
```bash
./mvnw.cmd -B clean package dockerfile:build
```

linux: 
```bash
./mvnw -B clean package dockerfile:build
```

4) ������ �������:

`docker compose up`

## OpenAPI
[AOP](http://127.0.0.1:8080/swagger-ui/index.html)

##Credentials

������������ � ������� �������:

```
"username": "admin",
"password": "admin"
```

������������ � ������������� �������:

```
"username": "user",
"password": "user"
```