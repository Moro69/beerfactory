DROP TABLE IF EXISTS beer;
CREATE TABLE beer(
  beer_id INT NOT NULL AUTO_INCREMENT,
  beer_title VARCHAR(255) NOT NULL UNIQUE,
  beer_abv DOUBLE NOT NULL,
  description VARCHAR(512) NULL,
  beer_price INT NOT NULL,
  beer_image VARCHAR(255) NULL,
  PRIMARY KEY (beer_id)
);

DROP TABLE IF EXISTS review;
CREATE TABLE review(
  review_id INT NOT NULL AUTO_INCREMENT,
  beer_id INT NOT NULL,
  review_comment VARCHAR(255) NULL,
  rating DOUBLE NOT NULL,
  PRIMARY KEY (review_id)
);

DROP TABLE IF EXISTS beer_order;
CREATE TABLE beer_order(
  order_id INT NOT NULL AUTO_INCREMENT,
  order_date DATE NOT NULL,
  beer_id INT NOT NULL,
  beer_quantity INT NOT NULL,
  order_price INT NOT NULL,
  PRIMARY KEY (order_id)
);