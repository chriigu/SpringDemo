create table USER(
ID int not null AUTO_INCREMENT,
UUID nvarchar(36) not null,
EMAIL nvarchar(128),
FIRST_NAME nvarchar(128),
LAST_NAME nvarchar(128)
)