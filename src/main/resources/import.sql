INSERT INTO TEST VALUES(1, 'Hello');
INSERT INTO TEST VALUES(2, 'World');

INSERT INTO ROLE (`id`, `name`) VALUES (NULL, 'ADMIN');
INSERT INTO ROLE (`id`, `name`) VALUES (NULL, 'USER');

INSERT INTO ACCOMMODATION (`id`, `name`, `city`, `street`, `post_code`, `phone_number`, `animals`) VALUES (NULL, 'BAcowka', 'Zakopane', 'Krakowska', '12-345', 600100100, true );
INSERT INTO ACCOMMODATION (`id`, `name`, `city`, `street`, `post_code`, `phone_number`, `animals`) VALUES (NULL, 'Wawel', 'Krakow', 'Smocza', '32-501', 600200200, true );
INSERT INTO ACCOMMODATION (`id`, `name`, `city`, `street`, `post_code`, `phone_number`, `animals`) VALUES (NULL, 'Uroczysko', 'Kielce', 'Cedzyna', '54-174', 600300300, false );
INSERT INTO ACCOMMODATION (`id`, `name`, `city`, `street`, `post_code`, `phone_number`, `animals`) VALUES (NULL, 'Tower', 'London', 'Queens', '71-762', 600400400, false );

INSERT INTO ACCOMMODATION_ACCOMMODATION_ADD_INFO (`accommodation_id`, `accommodation_add_info`) VALUES (1, 'POOL');
INSERT INTO ACCOMMODATION_ACCOMMODATION_ADD_INFO (`accommodation_id`, `accommodation_add_info`) VALUES (1, 'RESTAURANT');
INSERT INTO ACCOMMODATION_ACCOMMODATION_ADD_INFO (`accommodation_id`, `accommodation_add_info`) VALUES (1, 'BAR');
INSERT INTO ACCOMMODATION_ACCOMMODATION_ADD_INFO (`accommodation_id`, `accommodation_add_info`) VALUES (2, 'PARKING');
INSERT INTO ACCOMMODATION_ACCOMMODATION_ADD_INFO (`accommodation_id`, `accommodation_add_info`) VALUES (2, 'GRILL');
INSERT INTO ACCOMMODATION_ACCOMMODATION_ADD_INFO (`accommodation_id`, `accommodation_add_info`) VALUES (3, 'POOL');
INSERT INTO ACCOMMODATION_ACCOMMODATION_ADD_INFO (`accommodation_id`, `accommodation_add_info`) VALUES (3, 'PLAYROOM');
INSERT INTO ACCOMMODATION_ACCOMMODATION_ADD_INFO (`accommodation_id`, `accommodation_add_info`) VALUES (3, 'PARKING');
INSERT INTO ACCOMMODATION_ACCOMMODATION_ADD_INFO (`accommodation_id`, `accommodation_add_info`) VALUES (3, 'BAR');
INSERT INTO ACCOMMODATION_ACCOMMODATION_ADD_INFO (`accommodation_id`, `accommodation_add_info`) VALUES (4, 'POOL');
INSERT INTO ACCOMMODATION_ACCOMMODATION_ADD_INFO (`accommodation_id`, `accommodation_add_info`) VALUES (4, 'PARKING');
INSERT INTO ACCOMMODATION_ACCOMMODATION_ADD_INFO (`accommodation_id`, `accommodation_add_info`) VALUES (4, 'RESTAURANT');

INSERT INTO ROOM (`id`, `room_size`, `max_person`, `price`, `room_standard`, `description`, `accommodation_id`) VALUES (NULL, 15, 2, 150.00, 'STANDARD', 'short description room 1', 1);
INSERT INTO ROOM (`id`, `room_size`, `max_person`, `price`, `room_standard`, `description`, `accommodation_id`) VALUES (NULL, 25, 4, 300.00, 'FAMILY', 'short description room 2', 1);
INSERT INTO ROOM (`id`, `room_size`, `max_person`, `price`, `room_standard`, `description`, `accommodation_id`) VALUES (NULL, 30, 1, 200.00, 'SUPERIOR', 'short description room 3', 1);
INSERT INTO ROOM (`id`, `room_size`, `max_person`, `price`, `room_standard`, `description`, `accommodation_id`) VALUES (NULL, 25, 3, 250.00, 'PREMIUM', 'short description room 4', 1);
INSERT INTO ROOM (`id`, `room_size`, `max_person`, `price`, `room_standard`, `description`, `accommodation_id`) VALUES (NULL, 15, 2, 200.00, 'STANDARD', 'short description room 1', 2);
INSERT INTO ROOM (`id`, `room_size`, `max_person`, `price`, `room_standard`, `description`, `accommodation_id`) VALUES (NULL, 15, 2, 180.00, 'STANDARD', 'short description room 2', 2);
INSERT INTO ROOM (`id`, `room_size`, `max_person`, `price`, `room_standard`, `description`, `accommodation_id`) VALUES (NULL, 20, 4, 310.00, 'FAMILY', 'short description room 3', 2);
INSERT INTO ROOM (`id`, `room_size`, `max_person`, `price`, `room_standard`, `description`, `accommodation_id`) VALUES (NULL, 20, 2, 400.00, 'PREMIUM', 'short description room 1', 3);
INSERT INTO ROOM (`id`, `room_size`, `max_person`, `price`, `room_standard`, `description`, `accommodation_id`) VALUES (NULL, 20, 2, 400.00, 'PREMIUM', 'short description room 2', 3);
INSERT INTO ROOM (`id`, `room_size`, `max_person`, `price`, `room_standard`, `description`, `accommodation_id`) VALUES (NULL, 25, 2, 500.00, 'SUPERIOR', 'short description room 1', 4);
INSERT INTO ROOM (`id`, `room_size`, `max_person`, `price`, `room_standard`, `description`, `accommodation_id`) VALUES (NULL, 15, 1, 180.00, 'STANDARD', 'short description room 2', 4);
INSERT INTO ROOM (`id`, `room_size`, `max_person`, `price`, `room_standard`, `description`, `accommodation_id`) VALUES (NULL, 20, 3, 270.00, 'FAMILY', 'short description room 3', 4);
INSERT INTO ROOM (`id`, `room_size`, `max_person`, `price`, `room_standard`, `description`, `accommodation_id`) VALUES (NULL, 25, 4, 320.00, 'FAMILY', 'short description room 4', 4);

INSERT INTO USER (`id`, `username`, `email`, `password`, `idnumber`, `city`, `active`, `vip`) VALUES (NULL, 'Lucas', 'lucas@pl.pl', '12345', '500500500', 'Kielce', true, false);
INSERT INTO USER (`id`, `username`, `email`, `password`, `idnumber`, `city`, `active`, `vip`) VALUES (NULL, 'notLucas', 'notlucas@pl.pl', '12345', '400400400', 'Kielce', true, false);
INSERT INTO USER (`id`, `username`, `email`, `password`, `idnumber`, `city`, `active`, `vip`) VALUES (NULL, 'Martha', 'martha@pl.pl', '54321', '600600600', 'Warszawa', true, true);

INSERT INTO USER_ROLE (`user_id`, `role_id`) VALUES (1,2);
INSERT INTO USER_ROLE (`user_id`, `role_id`) VALUES (2,2);
INSERT INTO USER_ROLE (`user_id`, `role_id`) VALUES (3,1);

INSERT INTO RESERVATION (`id`, `reservation_number`, `user_id`, `accommodation_id`, `room_id`, `price`, `book_in`, `book_out`, `created`, `active`) VALUES (null, 20201127001, 1, 1, 1, 300.00, '2020-11-28', '2020-11-30', '2020-11-27', true);
INSERT INTO RESERVATION (`id`, `reservation_number`, `user_id`, `accommodation_id`, `room_id`, `price`, `book_in`, `book_out`, `created`, `active`) VALUES (null, 20201127001, 1, 1, 1, 220.00, '2020-11-21', '2020-11-25', '2020-11-20', false);
INSERT INTO RESERVATION (`id`, `reservation_number`, `user_id`, `accommodation_id`, `room_id`, `price`, `book_in`, `book_out`, `created`, `active`) VALUES (null, 20201127002, 2, 2, 7, 350.00, '2020-12-01', '2020-12-05', '2020-11-25', true);
INSERT INTO RESERVATION (`id`, `reservation_number`, `user_id`, `accommodation_id`, `room_id`, `price`, `book_in`, `book_out`, `created`, `active`) VALUES (null, 20201127003, 3, 3, 9, 150.00, '2020-12-24', '2020-12-27', '2020-11-25', true);