INSERT INTO genre (name) VALUES ('Fiction');
INSERT INTO genre (name) VALUES ('Non-Fiction');
INSERT INTO genre (name) VALUES ('Doc');
INSERT INTO genre (name) VALUES ('Science');

INSERT INTO author (name) VALUES ('George Orwell');
INSERT INTO author (name) VALUES ('J.K. Rowling');
INSERT INTO author (name) VALUES ('Ernest Hemingway');
INSERT INTO author (name) VALUES ('Pushkin');

INSERT INTO book (title) VALUES ('1984');
INSERT INTO book (title) VALUES ('Harry Potter and the Philosopher''s Stone');

INSERT INTO book_author (book_id, author_id) VALUES (1, 1);
INSERT INTO book_author (book_id, author_id) VALUES (2, 2);

INSERT INTO book_genre (book_id, genre_id) VALUES (1, 1);
INSERT INTO book_genre (book_id, genre_id) VALUES (1, 2);
