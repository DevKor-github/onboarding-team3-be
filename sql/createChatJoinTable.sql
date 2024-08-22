drop table if exists chat_join CASCADE;
CREATE TABLE chat_join (
                           ID SERIAL,
                        USER_ID INT NOT NULL,
    CHAT_ROOM INT NOT NULL,
                           FOREIGN KEY (USER_ID) REFERENCES user_account(ID) ON DELETE CASCADE,
                           FOREIGN KEY (CHAT_ROOM) REFERENCES chat_room(ID) ON DELETE CASCADE,
                           CREATED_AT TIMESTAMP,
                           UPDATED_AT TIMESTAMP,
                           PRIMARY KEY (ID)
);
