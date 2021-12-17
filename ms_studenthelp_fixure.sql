-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1:3306
-- Généré le : ven. 17 déc. 2021 à 13:31
-- Version du serveur : 8.0.27
-- Version de PHP : 7.4.26

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `ms_studenthelp`
--

--
-- Déchargement des données de la table `mssh_category`
--

INSERT INTO `mssh_category` (`id`, `title`) VALUES
(0, 'Électronique analogique');

--
-- Déchargement des données de la table `mssh_elem`
--

INSERT INTO `mssh_elem` (`id`, `authorId`, `date`, `lastModif`) VALUES
('2e7e65f5-8374-4c1f-902f-66516f631f44', 'ee6f7132-bd0a-4fcd-83b3-a8022377067b', '2021-12-17 14:21:41', NULL),
('d66b3f8c-2271-4afb-a348-e370ef99e583', 'Someone', '2021-12-17 14:18:06', NULL),
('e53ca203-81dc-4aed-ad28-bc2dd1892e90', 'fb6f7132-bd0a-4fcd-83b3-a8022377076a', '2021-12-17 14:26:39', NULL),
('f20b507a-ace4-481b-8b81-c3ce0a7cfacf', 'fb6f7132-bd0a-4fcd-83b3-a8022377076a', '2021-12-17 14:26:22', '2021-12-17 14:26:22'),
('f42eae8a-bb39-4fac-abfb-ad2b5ebf73f6', 'Someone', '2021-12-17 14:18:06', NULL);

--
-- Déchargement des données de la table `mssh_forumthread`
--

INSERT INTO `mssh_forumthread` (`id`, `title`, `category`, `answered`, `child`) VALUES
('f42eae8a-bb39-4fac-abfb-ad2b5ebf73f6', 'Montage amplificateur inverseur', 0, 0, 'd66b3f8c-2271-4afb-a348-e370ef99e583');

--
-- Déchargement des données de la table `mssh_post`
--

INSERT INTO `mssh_post` (`id`, `parent`, `content`) VALUES
('d66b3f8c-2271-4afb-a348-e370ef99e583', NULL, 'Est-ce que quelqu un peut me rappeler la formule de calcul pour un montage AOP en amplifiacteur inverseur?'),
('2e7e65f5-8374-4c1f-902f-66516f631f44', 'd66b3f8c-2271-4afb-a348-e370ef99e583', 'Tu dois faire ça...'),
('f20b507a-ace4-481b-8b81-c3ce0a7cfacf', 'd66b3f8c-2271-4afb-a348-e370ef99e583', 'Deleted post'),
('e53ca203-81dc-4aed-ad28-bc2dd1892e90', 'd66b3f8c-2271-4afb-a348-e370ef99e583', 'Tu dois plutot faire ça...');
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
