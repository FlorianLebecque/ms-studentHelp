-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Dec 03, 2021 at 10:57 AM
-- Server version: 10.3.31-MariaDB-0ubuntu0.20.04.1
-- PHP Version: 7.4.3

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `ms_studenthelp`
--

-- --------------------------------------------------------

--
-- Table structure for table `mssh_category`
--

CREATE TABLE `mssh_category` (
  `id` int(11) NOT NULL,
  `title` varchar(60) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `mssh_elem`
--

CREATE TABLE `mssh_elem` (
  `id` varchar(36) NOT NULL,
  `authorId` varchar(60) NOT NULL,
  `date` datetime NOT NULL,
  `lastModif` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `mssh_ForumThread`
--

CREATE TABLE `mssh_ForumThread` (
  `id` varchar(36) NOT NULL,
  `title` varchar(120) NOT NULL,
  `category` int(11) NOT NULL,
  `answered` int(11) NOT NULL DEFAULT 0,
  `child` varchar(36) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `mssh_FT_tags`
--

CREATE TABLE `mssh_FT_tags` (
  `id` varchar(36) NOT NULL,
  `tag` varchar(60) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `mssh_Post`
--

CREATE TABLE `mssh_Post` (
  `id` varchar(36) NOT NULL,
  `forumThread` varchar(36) NOT NULL,
  `parent` varchar(36) NOT NULL,
  `content` varchar(500) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `mssh_reactions`
--

CREATE TABLE `mssh_reactions` (
  `postId` varchar(36) NOT NULL,
  `authorId` varchar(60) NOT NULL,
  `value` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `mssh_category`
--
ALTER TABLE `mssh_category`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `mssh_elem`
--
ALTER TABLE `mssh_elem`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `mssh_ForumThread`
--
ALTER TABLE `mssh_ForumThread`
  ADD PRIMARY KEY (`id`),
  ADD KEY `ft_cat` (`category`);

--
-- Indexes for table `mssh_FT_tags`
--
ALTER TABLE `mssh_FT_tags`
  ADD PRIMARY KEY (`id`,`tag`);

--
-- Indexes for table `mssh_Post`
--
ALTER TABLE `mssh_Post`
  ADD KEY `elem_pt` (`id`),
  ADD KEY `ft_pt` (`forumThread`),
  ADD KEY `pt_pt` (`parent`);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `mssh_ForumThread`
--
ALTER TABLE `mssh_ForumThread`
  ADD CONSTRAINT `elem_ft` FOREIGN KEY (`id`) REFERENCES `mssh_elem` (`id`),
  ADD CONSTRAINT `ft_cat` FOREIGN KEY (`category`) REFERENCES `mssh_category` (`id`);

--
-- Constraints for table `mssh_FT_tags`
--
ALTER TABLE `mssh_FT_tags`
  ADD CONSTRAINT `ft_tag` FOREIGN KEY (`id`) REFERENCES `mssh_ForumThread` (`id`);

--
-- Constraints for table `mssh_Post`
--
ALTER TABLE `mssh_Post`
  ADD CONSTRAINT `elem_pt` FOREIGN KEY (`id`) REFERENCES `mssh_elem` (`id`),
  ADD CONSTRAINT `ft_pt` FOREIGN KEY (`forumThread`) REFERENCES `mssh_ForumThread` (`id`),
  ADD CONSTRAINT `pt_pt` FOREIGN KEY (`parent`) REFERENCES `mssh_Post` (`id`);

--
-- Constraints for table `mssh_reactions`
--
ALTER TABLE `mssh_reactions`
  ADD CONSTRAINT `pt_re` FOREIGN KEY (`postId`) REFERENCES `mssh_Post` (`id`),
  ADD CONSTRAINT `pt_at_re` UNIQUE (`postId`, `authorId`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
