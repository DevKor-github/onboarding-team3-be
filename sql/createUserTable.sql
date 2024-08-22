drop table if exists user_account CASCADE;
CREATE TABLE user_account (
                              ID SERIAL,
                              USERNAME VARCHAR(20) UNIQUE NOT NULL,
                              PASSWORD VARCHAR(255) NOT NULL,
                              NICKNAME VARCHAR(255) NOT NULL,
                              PROFILE_URL VARCHAR(2083) NOT NULL DEFAULT 'https://cdn.pixabay.com/photo/2016/08/08/09/17/avatar-1577909_1280.png',
                              ROLE VARCHAR(20) NOT NULL CHECK (ROLE IN ('admin', 'general')),
                              CREATED_AT TIMESTAMP,
                              UPDATED_AT TIMESTAMP,
                              PRIMARY KEY (ID)
);
