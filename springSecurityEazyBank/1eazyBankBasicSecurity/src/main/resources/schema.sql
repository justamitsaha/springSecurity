CREATE TABLE custom_user (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    age INT NOT NULL,
    roles VARCHAR(255) NOT NULL
);

--Authorities table
CREATE TABLE authorities (
  id INT NOT NULL AUTO_INCREMENT,
  customer_id INT NOT NULL,
  name VARCHAR(50) NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT fk_authorities_customer FOREIGN KEY (customer_id) REFERENCES custom_user (id)
);