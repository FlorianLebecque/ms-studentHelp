-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Nov 26, 2021 at 02:24 PM
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
-- Table structure for table `mssh_object`
--

CREATE TABLE `mssh_object` (
  `id` varchar(36) NOT NULL,
  `type` int(11) NOT NULL,
  `datetime` datetime NOT NULL,
  `author` varchar(36) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `mssh_object`
--

INSERT INTO `mssh_object` (`id`, `type`, `datetime`, `author`) VALUES
('e085fb86-649c-4e5d-b37a-91bd6b871396', 1, '2021-11-26 14:17:12', '8e649c81-d9aa-4b76-b62a-425280531a40');

-- --------------------------------------------------------

--
-- Table structure for table `mssh_objectmeta`
--

CREATE TABLE `mssh_objectmeta` (
  `id` int(11) NOT NULL,
  `id_object` varchar(36) NOT NULL,
  `meta_key` varchar(30) NOT NULL,
  `type` varchar(30) NOT NULL,
  `meta_value` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `mssh_objectmeta`
--

INSERT INTO `mssh_objectmeta` (`id`, `id_object`, `meta_key`, `type`, `meta_value`) VALUES
(1, 'e085fb86-649c-4e5d-b37a-91bd6b871396', 'catergory', 'string', 'debug'),
(2, 'e085fb86-649c-4e5d-b37a-91bd6b871396', 'answered', 'string', 'false'),
(3, 'e085fb86-649c-4e5d-b37a-91bd6b871396', 'title', 'string', 'First Thread Ever!');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `mssh_object`
--
ALTER TABLE `mssh_object`
  ADD PRIMARY KEY (`id`),
  ADD KEY `author_index` (`author`);

--
-- Indexes for table `mssh_objectmeta`
--
ALTER TABLE `mssh_objectmeta`
  ADD PRIMARY KEY (`id`),
  ADD KEY `object_meta` (`id_object`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `mssh_objectmeta`
--
ALTER TABLE `mssh_objectmeta`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `mssh_objectmeta`
--
ALTER TABLE `mssh_objectmeta`
  ADD CONSTRAINT `object_meta` FOREIGN KEY (`id_object`) REFERENCES `mssh_object` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
