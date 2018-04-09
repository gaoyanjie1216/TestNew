CREATE TABLE `NewTable` (
`id`  int(20) NOT NULL ,
`login_name`  varchar(20) NOT NULL ,
`ip`  varchar(60) NULL ,
`conditions`  varchar(100) NOT NULL ,
`sys_created`  datetime NOT NULL ,
`sys_lastmodified`  timestamp NOT NULL ON UPDATE CURRENT_TIMESTAMP ,
PRIMARY KEY (`id`)
)
;
