CREATE SEQUENCE articles_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE comments_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE users (
    email      VARCHAR(255) PRIMARY KEY,
    first_name VARCHAR(255),
    last_name  VARCHAR(255),
    password   VARCHAR(255),
    role       VARCHAR(255)
);

CREATE TABLE articles (
    id             BIGINT PRIMARY KEY,
    title          VARCHAR(50)  NOT NULL,
    content        TEXT         NOT NULL,
    published_date DATE,
    created_at     TIMESTAMP(6),
    updated_at     TIMESTAMP(6)
);

CREATE TABLE comments (
    id         BIGINT PRIMARY KEY,
    text       TEXT NOT NULL,
    article_id BIGINT REFERENCES articles (id) ON DELETE CASCADE,
    user_email VARCHAR(255) REFERENCES users (email),
    created_at TIMESTAMP(6)
);

CREATE TABLE article_likes (
    article_id BIGINT       NOT NULL REFERENCES articles (id) ON DELETE CASCADE,
    user_email VARCHAR(255) NOT NULL REFERENCES users (email) ON DELETE CASCADE,
    PRIMARY KEY (article_id, user_email)
);

CREATE EXTENSION IF NOT EXISTS pgcrypto;

INSERT INTO users (email, first_name, last_name, password, role)
VALUES ('admin@blog.local',
        'Admin',
        'Admin',
        '{bcrypt}' || crypt('admin', gen_salt('bf', 10)),
        'ROLE_ADMIN')
ON CONFLICT (email) DO NOTHING;

INSERT INTO users (email, first_name, last_name, password, role)
VALUES ('user@blog.local',
        'Иван',
        'Петров',
        '{bcrypt}' || crypt('user', gen_salt('bf', 10)),
        'ROLE_USER')
ON CONFLICT (email) DO NOTHING;

INSERT INTO articles (id, title, content, published_date, created_at, updated_at)
VALUES (nextval('articles_seq'),
        'Жена со своим лучшим другом подарили мне стул',
        $$Недавно мне исполнилось 35. Обычно день рождения — это повод для радости, но в этом году всё пошло немного не по плану. За неделю до праздника жена сказала: «Мы с Димой (её лучшим другом) подготовили тебе подарок, но пока не скажем какой». Я, честно говоря, напрягся. Дима — это её друг детства, они вместе ещё с института, и я давно перестал ревновать, но всё равно, когда жена проводит с ним больше времени, чем со мной, становится немного не по себе. А тут ещё и подарок от них двоих.

В день рождения они торжественно вручили мне большую коробку. Я развернул — внутри оказался стул. Обычный офисный стул, но с эргономичной спинкой и массажными роликами. Я давно хотел такой, но всё как-то руки не доходили. Я обрадовался, но внутри свербило: почему именно стул? И почему они так загадочно переглядывались?

Вечером, когда гости разошлись, жена призналась: «Понимаешь, мы с Димой заметили, что ты последнее время много работаешь за старым стулом, и у тебя начала болеть спина. Мы решили, что это будет лучший подарок». Оказалось, они вместе выбирали его в интернете, читали отзывы, сравнивали модели. Дима даже съездил в магазин, чтобы проверить, удобный ли он.

Мне стало смешно и стыдно за свои подозрения. Я представил, как жена и её лучший друг сидят вдвоём, обсуждают мою спину и выбирают стул. Это было так трогательно и по-настоящему заботливо, что я обнял жену и извинился за свои мысли. А потом сел на новый стул и понял: спина действительно не болит, и сидеть на нём гораздо приятнее.

Теперь, когда я работаю, я часто вспоминаю этот случай. Иногда лучший друг жены — это не угроза, а просто человек, который знает тебя достаточно хорошо, чтобы помочь выбрать хороший стул. Главное — не накручивать себя раньше времени.$$,
        '2026-10-09',
        now(),
        now());

INSERT INTO comments (id, text, article_id, user_email, created_at)
VALUES (nextval('comments_seq'),
        'эщкере сикссевен',
        currval('articles_seq'),
        'user@blog.local',
        now());

