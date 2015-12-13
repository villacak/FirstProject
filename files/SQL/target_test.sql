--<ScriptOptions statementTerminator=";"/>

ALTER TABLE `target_test`.`price` DROP PRIMARY KEY;

ALTER TABLE `target_test`.`category` DROP PRIMARY KEY;

ALTER TABLE `target_test`.`product` DROP PRIMARY KEY;

ALTER TABLE `target_test`.`price` DROP INDEX `target_test`.`product_id`;

ALTER TABLE `target_test`.`product` DROP INDEX `target_test`.`price_fk_idx`;

ALTER TABLE `target_test`.`product` DROP INDEX `target_test`.`id`;

ALTER TABLE `target_test`.`product` DROP INDEX `target_test`.`category_fk_idx`;

DROP TABLE `target_test`.`price`;

DROP TABLE `target_test`.`category`;

DROP TABLE `target_test`.`product`;

CREATE TABLE `target_test`.`price` (
	`pk` INT NOT NULL,
	`product_id` INT,
	`price` DECIMAL(10 , 2),
	PRIMARY KEY (`pk`)
) ENGINE=InnoDB;

CREATE TABLE `target_test`.`category` (
	`pk` INT NOT NULL,
	`description` VARCHAR(45),
	PRIMARY KEY (`pk`)
) ENGINE=InnoDB;

CREATE TABLE `target_test`.`product` (
	`pk` INT NOT NULL,
	`id` INT,
	`name` VARCHAR(120),
	`category` INT,
	`price` INT,
	PRIMARY KEY (`pk`)
) ENGINE=InnoDB;

CREATE INDEX `product_id` ON `target_test`.`price` (`product_id` ASC);

CREATE INDEX `price_fk_idx` ON `target_test`.`product` (`price` ASC);

CREATE INDEX `id` ON `target_test`.`product` (`id` ASC);

CREATE INDEX `category_fk_idx` ON `target_test`.`product` (`category` ASC);

