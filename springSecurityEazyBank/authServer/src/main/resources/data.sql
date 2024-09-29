
-- Insert into customer table
INSERT INTO customer (name, email, mobile_number, pwd, role, create_dt)
VALUES ('Happy', 'happy@example.com', '5334122365', '{bcrypt}$2a$12$66470DSU6DCXXqMiEadhtO60F1FFU0svn96szTKa.0saPPIKLaHbO', 'admin', CURRENT_DATE);

INSERT INTO customer (name, email, mobile_number, pwd, role, create_dt)
VALUES ('Sad', 'sad@example.com', '5334122366', '{bcrypt}$2a$12$66470DSU6DCXXqMiEadhtO60F1FFU0svn96szTKa.0saPPIKLaHbO', 'admin', CURRENT_DATE);


--Insert into authorities
--INSERT INTO authorities (customer_id, name)
--VALUES (1, 'VIEWACCOUNT');
--
--INSERT INTO authorities (customer_id, name)
--VALUES (1, 'VIEWCARDS');
--
--INSERT INTO authorities (customer_id, name)
--VALUES (1, 'VIEWLOANS');
--
--INSERT INTO authorities (customer_id, name)
--VALUES (1, 'VIEWBALANCE');

--DELETE FROM authorities;
--
INSERT INTO authorities (customer_id, name)
VALUES (1, 'ROLE_USER');

INSERT INTO authorities (customer_id, name)
VALUES (1, 'ROLE_ADMIN');

