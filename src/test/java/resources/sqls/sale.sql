INSERT INTO client (id_client, name_client, email_client, password_client)
VALUES (1, 'test 1', 'test1@gmail.com', '123');
INSERT INTO client (id_client, name_client, email_client, password_client)
VALUES (2, 'test 2', 'test2@gmail.com', '123');

INSERT INTO sale (id_sale, date_sale, client_id_client)
VALUES (1, '2023-06-21 10:00:00', 1);

INSERT INTO sale (id_sale, date_sale, client_id_client)
VALUES (2, '2023-06-20 15:30:00', 2);
