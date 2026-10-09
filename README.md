# Blog

Блог на Spring Boot: лента статей с сортировкой по дате и популярности, лайки и комментарии для зарегистрированных пользователей, панель администратора для создания, редактирования и удаления статей.

## Стек

- Java 21, Spring Boot 4
- Spring MVC + Thymeleaf
- Spring Security (вход по форме, роли `USER` и `ADMIN`)
- Spring Data JPA (Hibernate)
- PostgreSQL 17, миграции Flyway
- Docker, Docker Compose

## Запуск в Docker

Нужен только [Docker](https://www.docker.com/products/docker-desktop/).

```bash
docker compose up -d --build
```

Приложение откроется на http://localhost:8080. При первом запуске Flyway создаст схему базы и заполнит её начальными данными: пользователями, статьёй и комментарием.

Остановить:

```bash
docker compose down
```

Данные хранятся в Docker volume `pgdata` и переживают перезапуск. Чтобы удалить их и пересоздать базу с нуля:

```bash
docker compose down -v
docker compose up -d --build
```

## Аккаунты

| Email              | Пароль  | Роль  | Возможности                                   |
|--------------------|---------|-------|-----------------------------------------------|
| `admin@blog.local` | `admin` | ADMIN | всё, плюс панель `/dashboard`: добавление, редактирование и удаление статей |
| `user@blog.local`  | `user`  | USER  | лайки и комментарии                           |

Новых пользователей можно зарегистрировать на странице `/signup`, они получают роль `USER`.

## Локальная разработка

Чтобы запускать приложение из IDE или через Gradle, достаточно поднять в Docker только базу:

```bash
docker compose up -d postgres
./gradlew bootRun        # Windows: .\gradlew.bat bootRun
```

Приложение подключается к `localhost:5432` с параметрами из `src/main/resources/application.properties`. Если запущен контейнер `blog-app`, перед `bootRun` его нужно остановить, иначе порт 8080 будет занят:

```bash
docker compose stop app
```

Тест `BlogApplicationTests` поднимает весь контекст приложения, поэтому для `./gradlew test` и `./gradlew build` база тоже должна быть запущена.

## Миграции базы

Схема и начальные данные описаны в `src/main/resources/db/migration`. Hibernate работает в режиме `ddl-auto=validate`: он только проверяет схему, а создаёт и изменяет её Flyway.

Уже применённые миграции менять нельзя: Flyway сверяет их контрольные суммы и при расхождении не даст приложению запуститься. Новые изменения добавляйте отдельными файлами: `V2__описание.sql`, `V3__описание.sql` и так далее.

## Структура

```
src/main/java/fun/liljaksha/blog
├── config/        настройки Spring Security
├── controller/    контроллеры статей и аутентификации
├── model/         JPA-сущности: Article, Comment, User
├── repository/    репозитории Spring Data
└── services/      бизнес-логика
src/main/resources
├── db/migration/  миграции Flyway
├── templates/     шаблоны Thymeleaf
└── static/css/    стили
```
