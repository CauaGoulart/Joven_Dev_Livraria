INSERT INTO book (id_book, title_book, price_book)
VALUES (1, 'Book 1', 20);
INSERT INTO book (id_book, title_book, price_book)
VALUES (2, 'Book 2', 40);

INSERT INTO client (id_client, name_client, email_client, password_client)
VALUES (1, 'test 1', 'test1@gmail.com', '123');
INSERT INTO client (id_client, name_client, email_client, password_client)
VALUES (2, 'test 2', 'test2@gmail.com', '123');

INSERT INTO sale (id_sale, date_sale, client_id_client)
VALUES (1, '2023-06-21 10:00:00', 1);
INSERT INTO sale (id_sale, date_sale, client_id_client)
VALUES (2, '2023-06-20 15:30:00', 2);

INSERT INTO sale_book (id_SB, sale_id_sale, book_id_book, quant_sell)
VALUES (1, 1, 1, 20);

INSERT INTO sale_book (id_SB, sale_id_sale, book_id_book, quant_sell)
VALUES (2, 2, 2, 10);
