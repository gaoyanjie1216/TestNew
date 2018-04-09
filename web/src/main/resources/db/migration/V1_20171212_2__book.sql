ALTER TABLE `book`
ADD COLUMN `name`  varchar(255) NULL AFTER `id`,
ADD COLUMN `price`  decimal NULL AFTER `name`;