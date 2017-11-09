USE test;

DROP TABLE IF EXISTS meals;

CREATE TABLE `test`.`meals` (
  `id` INT(8) NOT NULL AUTO_INCREMENT,
  `dateTime` DATETIME NOT NULL,
  `description` VARCHAR(255) NULL,
  `calories` INT NOT NULL,
  PRIMARY KEY (`id`))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8;

insert into meals (dateTime, description, calories) values
  ('2015:05:30 10-00', 'Завтрак', 500),
  ('2015:05:30 01-00', 'Обед', 1000),
  ('2015:05:30 08-00', 'Ужин', 500),
  ('2015:05:31 10-00', 'Завтрак', 1000),
  ('2015:05:31 01-00', 'Обед', 500),
  ('2015:05:31 08-00', 'Ужин', 510);