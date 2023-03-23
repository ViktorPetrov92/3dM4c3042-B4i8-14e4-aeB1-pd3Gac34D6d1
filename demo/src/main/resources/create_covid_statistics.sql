CREATE DATABASE IF NOT EXISTS `covid_statistics`;
USE `covid_statistics`;
CREATE TABLE IF NOT EXISTS `covid_statistics` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uuid` varchar(100) DEFAULT NULL,
  `country` varchar(100) DEFAULT NULL,
  `country_code` varchar(2) DEFAULT NULL,
  `slug` varchar(100) DEFAULT NULL,
  `new_confirmed` int(20) DEFAULT NULL,
  `total_confirmed` int(20) DEFAULT NULL,
  `new_deaths` int(20) DEFAULT NULL,
  `total_deaths` int(20) DEFAULT NULL,
  `new_recovered` int(20) DEFAULT NULL,
  `total_recovered` int(20) DEFAULT NULL,
  `date` varchar(50) DEFAULT NULL,
  `time_created` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1380 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;
