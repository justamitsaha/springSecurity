-- Insert a test user: username = amit, password = qwerty (BCrypt hashed)
INSERT INTO custom_user (username, password, age, roles) VALUES (
    'amit',
    '{bcrypt}$2a$12$h0qbEfY3fK8Xz4CIpuDHM.MdrkSVeKx8AodPaX5McnAirmbevL/gi',
    25,
    'USER'
);


INSERT INTO custom_user (username, password, age, roles) VALUES (
    'shamit',
    '{bcrypt}$2a$12$h0qbEfY3fK8Xz4CIpuDHM.MdrkSVeKx8AodPaX5McnAirmbevL/gi',
    18,
    'USER'
);

INSERT INTO authorities (customer_id, name)
VALUES (1, 'ROLE_USER');

INSERT INTO authorities (customer_id, name)
VALUES (1, 'ROLE_ADMIN');

INSERT INTO authorities (customer_id, name)
VALUES (2, 'ROLE_USER');

