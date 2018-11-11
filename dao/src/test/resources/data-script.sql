INSERT INTO beer (beer_title, beer_abv, description, beer_price, beer_image)
VALUES ('Hamovniki', 5.6, 'Pretty good beer', 120, 'hamovniki');

INSERT INTO beer (beer_title, beer_abv, description, beer_price, beer_image)
VALUES ('Alivaria Gold', 4.9, 'Good beer', 115, 'alivaria-gold');

INSERT INTO beer (beer_title, beer_abv, description, beer_price, beer_image)
VALUES ('Kozel', 4.3, 'Nice beer', 135, 'kozel');

INSERT INTO beer (beer_title, beer_abv, description, beer_price, beer_image)
VALUES ('Corona Extra', 5.6, 'Very nice beer', 170, 'corona-extra');


INSERT INTO beer_order (order_date, beer_id, beer_quantity, order_price)
VALUES ('2017-06-23', 4, 25, 4250);

INSERT INTO beer_order (order_date, beer_id, beer_quantity, order_price)
VALUES ('2018-05-13', 1, 10, 1200);

INSERT INTO beer_order (order_date, beer_id, beer_quantity, order_price)
VALUES ('2015-11-01', 2, 15, 1725);

INSERT INTO beer_order (order_date, beer_id, beer_quantity, order_price)
VALUES ('2014-06-23', 4, 25, 4250);

INSERT INTO beer_order (order_date, beer_id, beer_quantity, order_price)
VALUES ('2019-03-21', 1, 10, 1200);

INSERT INTO beer_order (order_date, beer_id, beer_quantity, order_price)
VALUES ('2007-08-04', 2, 15, 1725);


INSERT INTO review (beer_id, review_comment, rating)
VALUES (1, 'I like it.' ,4.5);

INSERT INTO review (beer_id, review_comment, rating)
VALUES (1, 'I recommend it.', 5);

INSERT INTO review (beer_id, review_comment, rating)
VALUES (3, 'Not bad', 4);

INSERT INTO review (beer_id, review_comment, rating)
VALUES (3, 'Do not waste your money!', 2);

INSERT INTO review (beer_id, review_comment, rating)
VALUES (4, 'JUST 5 OF 5!!!!!!', 5);

