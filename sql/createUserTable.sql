drop table if exists user_account CASCADE;
CREATE TABLE user_account (
                              ID SERIAL,
                              USERNAME VARCHAR(20) UNIQUE NOT NULL,
                              PW VARCHAR(255) NOT NULL,
                              NICKNAME VARCHAR(20) NOT NULL,
                              CREATED_AT TIMESTAMP,
                              UPDATED_AT TIMESTAMP,
                              PRIMARY KEY (ID)
);
