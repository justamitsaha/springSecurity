CREATE TABLE customer (
  customer_id INT NOT NULL AUTO_INCREMENT,
  name VARCHAR(100) NOT NULL,
  email VARCHAR(100) NOT NULL,
  mobile_number VARCHAR(20) NOT NULL,
  pwd VARCHAR(500) NOT NULL,
  role VARCHAR(100) NOT NULL,
  create_dt DATE DEFAULT NULL,
  PRIMARY KEY (customer_id)
);

--Authorities table
CREATE TABLE authorities (
  id INT NOT NULL AUTO_INCREMENT,
  customer_id INT NOT NULL,
  name VARCHAR(50) NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT fk_authorities_customer FOREIGN KEY (customer_id) REFERENCES customer (customer_id)
);


