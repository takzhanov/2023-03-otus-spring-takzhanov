INSERT INTO genre (name)
VALUES ('Fiction'),
       ('Non-Fiction'),
       ('Doc'),
       ('Science');

INSERT INTO author (name)
VALUES ('George Orwell'),
       ('J.K. Rowling'),
       ('Ernest Hemingway'),
       ('Pushkin');

INSERT INTO book (title)
VALUES ('1984'),
       ('Harry Potter and the Philosopher''s Stone');

INSERT INTO book_author (book_id, author_id)
VALUES (1, 1),
       (2, 2);

INSERT INTO book_genre (book_id, genre_id)
VALUES (1, 1),
       (1, 2);
