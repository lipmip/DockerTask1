# Blog

Блог на Spring Boot: лента статей с сортировкой по дате и популярности, лайки и комментарии для зарегистрированных пользователей, панель администратора для создания, редактирования и удаления статей.

## Стек

- Java 21, Spring Boot 4
- Spring MVC + Thymeleaf
- Spring Security (вход по форме, роли `USER` и `ADMIN`)
- Spring Data JPA (Hibernate)
- PostgreSQL 17, миграции Flyway
- Docker, Docker Compose

## Аккаунты

| Email              | Пароль  | Роль  | Возможности                                   |
|--------------------|---------|-------|-----------------------------------------------|
| `admin@blog.local` | `admin` | ADMIN | всё, плюс панель `/dashboard`: добавление, редактирование и удаление статей |
| `user@blog.local`  | `user`  | USER  | лайки и комментарии                           |

Новых пользователей можно зарегистрировать на странице `/signup`, они получают роль `USER`.

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
