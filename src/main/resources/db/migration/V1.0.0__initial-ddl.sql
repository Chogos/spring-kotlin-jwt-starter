DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS authorities;
DROP TABLE IF EXISTS user_authorities;

CREATE TABLE users(
    id                          SERIAL PRIMARY KEY,
    username                    VARCHAR(255) UNIQUE NOT NULL,
    password                    VARCHAR(255) NOT NULL,
    email                       VARCHAR(255) UNIQUE NOT NULL,
    enabled                     BOOLEAN NOT NULL,
    last_password_change_date   TIMESTAMP NOT NULL
);

CREATE TABLE authorities(
    id      SERIAL PRIMARY KEY,
    name    VARCHAR(255) UNIQUE NOT NULL
);

CREATE TABLE user_authorities(
    user_id         INTEGER NOT NULL,
    authority_id    INTEGER NOT NULL,
    PRIMARY KEY (user_id, authority_id),
    CONSTRAINT user_authorities_user_id_fkey FOREIGN KEY (user_id)
        REFERENCES users (id) MATCH SIMPLE
        ON UPDATE NO ACTION ON DELETE NO ACTION,
    CONSTRAINT user_authorities_authority_id_fkey FOREIGN KEY (authority_id)
        REFERENCES authorities (id) MATCH SIMPLE
        ON UPDATE NO ACTION ON DELETE NO ACTION
);
