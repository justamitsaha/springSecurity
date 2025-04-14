-- Insert a test user: username = amit, password = qwerty (BCrypt hashed)
INSERT INTO custom_user (username, password, age, roles) VALUES (
    'amit',
    '{bcrypt}$2a$12$G2mI8xcwWVLfpkW9r.sz.OaKPoSQWc4cpM5gfP7JVe3ZAY7h9k.q2',
    25,
    'USER'
);
