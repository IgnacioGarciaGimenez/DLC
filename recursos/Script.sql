-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema dlc
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `dlc` ;

-- -----------------------------------------------------
-- Schema dlc
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `dlc` DEFAULT CHARACTER SET utf8 ;
USE `dlc` ;

-- -----------------------------------------------------
-- Table `dlc`.`Terminos`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `dlc`.`Terminos` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `cantDocumentos` INT NULL,
  `maxFrecuencia` INT NULL,
  `termino` VARCHAR(100) NULL,
  `vocabulario_ID` INT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `dlc`.`Documentos`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `dlc`.`Documentos` (
  `documento_ID` INT NOT NULL AUTO_INCREMENT,
  `ruta` VARCHAR(150) NULL,
  `titulo` VARCHAR(45) NULL,
  PRIMARY KEY (`documento_ID`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `dlc`.`Posteos`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `dlc`.`Posteos` (
  `posteo_ID` INT NOT NULL AUTO_INCREMENT,
  `documento_ID` INT NULL,
  `frecuencia` INT NULL,
  `vocabulario_ID` INT NULL,
  `palabra` VARCHAR(100) NULL,
  PRIMARY KEY (`posteo_ID`),
  INDEX `fk_Posteo_Documento1_idx` (`documento_ID` ASC) VISIBLE,
  CONSTRAINT `fk_Posteo_Documento1`
    FOREIGN KEY (`documento_ID`)
    REFERENCES `dlc`.`Documentos` (`documento_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
