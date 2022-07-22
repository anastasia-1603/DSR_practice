CREATE TABLE category
(
    id                 SERIAL PRIMARY KEY,
    name               VARCHAR(30) UNIQUE NOT NULL,
    description        VARCHAR(30)        NOT NULL,
    image              VARCHAR(30)        NOT NULL,
    parent_category_id INTEGER,
    FOREIGN KEY (parent_category_id) REFERENCES category (id)
);

CREATE TABLE item
(
    id          SERIAL PRIMARY KEY,
    name        VARCHAR(30) NOT NULL,
    description VARCHAR(30) NOT NULL,
    image       VARCHAR(30) NOT NULL,
    category_id INTEGER     NOT NULL,
    FOREIGN KEY (category_id) REFERENCES category (id)
);

CREATE TABLE users
(
    id         SERIAL PRIMARY KEY,
    surname    VARCHAR(30)        NOT NULL,
    name       VARCHAR(30)        NOT NULL,
    patronymic VARCHAR(30)        NOT NULL,
    email      VARCHAR(30) UNIQUE NOT NULL
);

CREATE TABLE item_user
(
    item_id INTEGER NOT NULL,
    user_id INTEGER NOT NULL,
    PRIMARY KEY (item_id, user_id),
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (item_id) REFERENCES item (id)
);

CREATE TABLE archive_item_user
(
    item_id INTEGER NOT NULL,
    user_id INTEGER NOT NULL,
    PRIMARY KEY (item_id, user_id),
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (item_id) REFERENCES item (id)
);

