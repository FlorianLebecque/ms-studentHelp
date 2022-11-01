-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema ms_studenthelp
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema ms_studenthelp
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `ms_studenthelp` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `ms_studenthelp` ;

-- -----------------------------------------------------
-- Table `ms_studenthelp`.`authors`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ms_studenthelp`.`authors` (
  `id` VARCHAR(40) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `ms_studenthelp`.`categories`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ms_studenthelp`.`categories` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `title_UNIQUE` (`title` ASC) VISIBLE)
ENGINE = InnoDB
AUTO_INCREMENT = 5
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `ms_studenthelp`.`posts`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ms_studenthelp`.`posts` (
  `id` VARCHAR(40) NOT NULL,
  `content` VARCHAR(45) NOT NULL,
  `upvotes` INT NOT NULL DEFAULT '0',
  `downvotes` INT NOT NULL DEFAULT '0',
  `date_posted` DATETIME NOT NULL,
  `date_modified` DATETIME NOT NULL,
  `author_id` VARCHAR(40) NOT NULL,
  `parent` VARCHAR(40) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  INDEX `idAuthor_idx` (`author_id` ASC) VISIBLE,
  INDEX `parent_idx` (`parent` ASC) VISIBLE,
  CONSTRAINT `author_id`
    FOREIGN KEY (`author_id`)
    REFERENCES `ms_studenthelp`.`authors` (`id`),
  CONSTRAINT `parent`
    FOREIGN KEY (`parent`)
    REFERENCES `ms_studenthelp`.`posts` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `ms_studenthelp`.`reactions`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ms_studenthelp`.`reactions` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `value` INT NOT NULL,
  `author_id` VARCHAR(40) NOT NULL,
  `post_id` VARCHAR(40) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  INDEX `authorId_idx` (`author_id` ASC) VISIBLE,
  INDEX `postId_idx` (`post_id` ASC) VISIBLE,
  CONSTRAINT `reactions_author_id`
    FOREIGN KEY (`author_id`)
    REFERENCES `ms_studenthelp`.`authors` (`id`),
  CONSTRAINT `reactions_post_id`
    FOREIGN KEY (`post_id`)
    REFERENCES `ms_studenthelp`.`posts` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 9
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `ms_studenthelp`.`threads`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ms_studenthelp`.`threads` (
  `id` VARCHAR(36) NOT NULL,
  `title` VARCHAR(45) NOT NULL,
  `answered` TINYINT NOT NULL DEFAULT '0',
  `id_category` INT NOT NULL,
  `date_posted` DATETIME NOT NULL,
  `date_modified` DATETIME NOT NULL,
  `first_post` VARCHAR(40) NOT NULL,
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  INDEX `idCategory_idx` (`id_category` ASC) VISIBLE,
  INDEX `firstPost_idx` (`first_post` ASC) VISIBLE,
  CONSTRAINT `first_post`
    FOREIGN KEY (`first_post`)
    REFERENCES `ms_studenthelp`.`posts` (`id`),
  CONSTRAINT `id_category`
    FOREIGN KEY (`id_category`)
    REFERENCES `ms_studenthelp`.`categories` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `ms_studenthelp`.`tags`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ms_studenthelp`.`tags` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(45) NOT NULL,
  `thread_id` VARCHAR(40) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  INDEX `idThread_idx` (`thread_id` ASC) VISIBLE,
  CONSTRAINT `idThread`
    FOREIGN KEY (`thread_id`)
    REFERENCES `ms_studenthelp`.`threads` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 13
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
