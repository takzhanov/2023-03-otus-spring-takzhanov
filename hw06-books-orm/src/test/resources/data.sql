INSERT INTO genre (name)
VALUES ('Test Genre 1'),
       ('Test Genre 2'),
       ('Test Genre 3');

INSERT INTO author (name)
VALUES ('Test Author 1'),
       ('Test Author 2'),
       ('Test Author 3');

INSERT INTO book (title)
VALUES ('1984'),
       ('Harry Potter and the Philosopher''s Stone');

INSERT INTO book_author (book_id, author_id)
VALUES (1, 1),
       (2, 2);

INSERT INTO book_genre (book_id, genre_id)
VALUES (1, 1),
       (1, 2);

INSERT INTO comment (TEXT, BOOK_ID)
VALUES ('Comment 1', 1),
       ('Comment 2', 1);
