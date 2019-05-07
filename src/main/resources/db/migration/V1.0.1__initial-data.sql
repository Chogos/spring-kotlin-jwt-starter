-- Password: 'admin'
INSERT INTO users (id, username, password, email, enabled, last_password_change_date)
    VALUES (1, 'admin', '$2a$10$eg135RbExrxvE7m2KruBQOkVM.tvbSP77NeKhbmCWj0OGTOj9qnF6',
            'admin@example.com', true, current_timestamp);

INSERT INTO authorities (id, name) VALUES (1, 'ROLE_ADMIN');
INSERT INTO authorities (id, name) VALUES (2, 'ROLE_USER');

INSERT INTO user_authorities (user_id, authority_id) VALUES (1, 1);
INSERT INTO user_authorities(user_id, authority_id) VALUES (1, 2);
