create schema IF NOT EXISTS springdemo;
COMMIT;
create table IF NOT EXISTS springdemo.USERS
(
    ID              int           not null auto_increment,
    UUID            nvarchar(36)  not null,
    EMAIL           nvarchar(128) not null unique,
    FIRST_NAME      nvarchar(128) not null,
    LAST_NAME       nvarchar(128) not null,
    BIRTHDATE       date          not null,
    CREATED_TS      datetime2     not null,
    LAST_UPDATED_TS datetime2     not null,
    PRIMARY KEY (ID)
);
