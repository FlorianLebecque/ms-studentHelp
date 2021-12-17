-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Generation Time: Dec 17, 2021 at 08:49 AM
-- Server version: 8.0.21
-- PHP Version: 7.3.21

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `ms_`
--

-- --------------------------------------------------------

--
-- Table structure for table `mssh_category`
--

DROP TABLE IF EXISTS `mssh_category`;
CREATE TABLE IF NOT EXISTS `mssh_category` (
  `id` int NOT NULL,
  `title` varchar(60) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Table structure for table `mssh_elem`
--

DROP TABLE IF EXISTS `mssh_elem`;
CREATE TABLE IF NOT EXISTS `mssh_elem` (
  `id` varchar(36) NOT NULL,
  `authorId` varchar(60) NOT NULL,
  `date` datetime NOT NULL,
  `lastModif` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Table structure for table `mssh_forumthread`
--

DROP TABLE IF EXISTS `mssh_forumthread`;
CREATE TABLE IF NOT EXISTS `mssh_forumthread` (
  `id` varchar(36) NOT NULL,
  `title` varchar(120) NOT NULL,
  `category` int NOT NULL,
  `answered` int NOT NULL DEFAULT '0',
  `child` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `ft_cat` (`category`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Table structure for table `mssh_ft_tags`
--

DROP TABLE IF EXISTS `mssh_ft_tags`;
CREATE TABLE IF NOT EXISTS `mssh_ft_tags` (
  `id` varchar(36) NOT NULL,
  `tag` varchar(60) NOT NULL,
  PRIMARY KEY (`id`,`tag`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Table structure for table `mssh_post`
--

DROP TABLE IF EXISTS `mssh_post`;
CREATE TABLE IF NOT EXISTS `mssh_post` (
  `id` varchar(36) NOT NULL,
  `parent` varchar(36) DEFAULT NULL,
  `content` varchar(500) NOT NULL,
  KEY `elem_pt` (`id`),
  KEY `pt_pt` (`parent`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Table structure for table `mssh_reactions`
--

DROP TABLE IF EXISTS `mssh_reactions`;
CREATE TABLE IF NOT EXISTS `mssh_reactions` (
  `postId` varchar(36) NOT NULL,
  `authorId` varchar(60) NOT NULL,
  `value` int NOT NULL,
  UNIQUE KEY `pt_at_re` (`postId`,`authorId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `mssh_forumthread`
--
ALTER TABLE `mssh_forumthread`
  ADD CONSTRAINT `elem_ft` FOREIGN KEY (`id`) REFERENCES `mssh_elem` (`id`),
  ADD CONSTRAINT `ft_cat` FOREIGN KEY (`category`) REFERENCES `mssh_category` (`id`);

--
-- Constraints for table `mssh_ft_tags`
--
ALTER TABLE `mssh_ft_tags`
  ADD CONSTRAINT `ft_tag` FOREIGN KEY (`id`) REFERENCES `mssh_forumthread` (`id`);

--
-- Constraints for table `mssh_post`
--
ALTER TABLE `mssh_post`
  ADD CONSTRAINT `elem_pt` FOREIGN KEY (`id`) REFERENCES `mssh_elem` (`id`),
  ADD CONSTRAINT `pt_pt` FOREIGN KEY (`parent`) REFERENCES `mssh_post` (`id`);

--
-- Constraints for table `mssh_reactions`
--
ALTER TABLE `mssh_reactions`
  ADD CONSTRAINT `pt_re` FOREIGN KEY (`postId`) REFERENCES `mssh_post` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
