INSERT INTO genres (id_genres,tipe_genres)
VALUES (1,'Genres 1');
INSERT INTO genres (id_genres,tipe_genres)
VALUES (2,'Genres 2');

INSERT INTO book (id_book, title_book, price_book)
VALUES (1, 'Book 1', 20);
INSERT INTO book (id_book, title_book, price_book)
VALUES (2, 'Book 2', 40);

INSERT INTO book_genres(id_book_genres,genres_id_genres,book_id_book) 
VALUES (1,1,1);
INSERT INTO book_genres(id_book_genres,genres_id_genres,book_id_book) 
VALUES (2,2,2);