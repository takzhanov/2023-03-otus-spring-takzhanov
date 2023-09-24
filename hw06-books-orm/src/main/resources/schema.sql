CREATE TABLE genre
(
    id   IDENTITY PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE author
(
    id   IDENTITY PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE book
(
    id    IDENTITY PRIMARY KEY,
    title VARCHAR(255) NOT NULL
);

CREATE TABLE book_author
(
    book_id   INT REFERENCES book (id) ON DELETE CASCADE,
    author_id INT REFERENCES author (id),
    PRIMARY KEY (book_id, author_id)
);

CREATE TABLE book_genre
(
    book_id  INT REFERENCES book (id) ON DELETE CASCADE,
    genre_id INT REFERENCES genre (id),
    PRIMARY KEY (book_id, genre_id)
);

CREATE TABLE comment
(
    id        IDENTITY PRIMARY KEY,
    text      VARCHAR(1024) NOT NULL,
    book_id   INT REFERENCES book (id) ON DELETE CASCADE,
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);