drop table if exists user_account CASCADE;
CREATE TABLE user_account (
                              ID SERIAL,
                              USERNAME VARCHAR(20) UNIQUE NOT NULL,
                              PASSWORD VARCHAR(255) NOT NULL,
                              ROLE VARCHAR(20) NOT NULL CHECK (ROLE IN ('admin', 'general')),
                              CREATED_AT TIMESTAMP,
                              UPDATED_AT TIMESTAMP,
                              PRIMARY KEY (ID)
);
