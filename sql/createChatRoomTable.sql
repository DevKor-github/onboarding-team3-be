drop table if exists chat_room CASCADE;
CREATE TABLE chat_room (
    ID SERIAL,
    CREATED_AT TIMESTAMP,
    UPDATED_AT TIMESTAMP,
    PRIMARY KEY (ID)
);
