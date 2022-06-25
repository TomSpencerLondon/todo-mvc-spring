create table todo_item
(
    id    BIGINT NOT NULL PRIMARY KEY,
    title TEXT,
    completed BOOLEAN NOT NULL
);

create sequence hibernate_sequence;