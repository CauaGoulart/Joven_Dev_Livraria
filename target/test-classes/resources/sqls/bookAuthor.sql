INSERT INTO author (id_author,name_author, gender_author)
VALUES (1,'Author 1', 'Male');
INSERT INTO author (id_author,name_author, gender_author)
VALUES (2,'Author 2', 'Male');

INSERT INTO book (id_book, title_book, price_book)
VALUES (1, 'Book 1', 20);
INSERT INTO book (id_book, title_book, price_book)
VALUES (2, 'Book 2', 40);

INSERT INTO book_author (id_book_author,author_id_author,book_id_book) 
VALUES (1,1,1);

INSERT INTO book_author (id_book_author,author_id_author,book_id_book) 
VALUES (2,2,2);